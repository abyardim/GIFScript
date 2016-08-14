package gifscriptui;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

public class GifEditorMain extends Application {

	private Button newButton, saveButton, saveAsButton, openButton;
	private Button playButton, pauseButton, exportButton;
	
	private CodeArea codeArea;
	private TextArea outputTextArea;
	private ImageView gifViewer;
	
	private Stage stage;
	
	// typical save mechanism variables
	private boolean fileChanged = true;
	private File saveFile = null;
	
	Image currentGIF;
	
    @Override
    public void start ( Stage primaryStage) {
        BorderPane root = new BorderPane();
        
    	///// left half: editor, output and tool bar
        
        BorderPane leftBorderPane = new BorderPane();
        
        ToolBar bar1 = new ToolBar();
        newButton = new Button( "New"); newButton.addEventHandler( ActionEvent.ACTION, (e) -> onClickNew(e));
        saveButton = new Button( "Save..."); saveButton.addEventHandler( ActionEvent.ACTION, (e) -> onClickSave(e));
        saveAsButton = new Button( "Save as..."); saveAsButton.addEventHandler( ActionEvent.ACTION, (e) -> onClickSaveAs(e));
        openButton = new Button( "Open..."); openButton.addEventHandler( ActionEvent.ACTION, (e) -> onClickOpen(e));
        bar1.getItems().addAll( newButton,
        						new Separator(),
        						saveButton,
        						saveAsButton,
        						openButton);        
        
        leftBorderPane.setTop( bar1);
        
        /// JavaScript Editor component
        
        codeArea = new CodeArea();
        
        String stylesheet = GifEditorMain.class.getResource("css/java-style.css").toExternalForm();
        IntFunction<String> format = (digits -> " %" + digits + "d ");

        codeArea.setParagraphGraphicFactory( LineNumberFactory.get(codeArea, format));
        codeArea.textProperty().addListener( ( obs, oldText, newText) -> {
            codeArea.setStyleSpans( 0, computeHighlighting(newText));
        });
        
        StackPane pane = new StackPane( codeArea);
        pane.getStylesheets().add(stylesheet);
        pane.setMinWidth( 300);
        
        leftBorderPane.setCenter( pane);
        
        /// text area for "compiler" output
        
        String stylesheetOutput = GifEditorMain.class.getResource("css/output-style.css").toExternalForm();
       
        outputTextArea = new TextArea();
        outputTextArea.getStylesheets().add( stylesheetOutput);
        outputTextArea.getStyleClass().add( "root");
        outputTextArea.setMaxHeight( 100);
        outputTextArea.setEditable( false);
        outputTextArea.setText( "Output:");
        leftBorderPane.setBottom( outputTextArea);
        
        root.setCenter( leftBorderPane);
        
        ///// right half of the scene: ImageViewer & toolbar
         
        ToolBar bar2 = new ToolBar();
        playButton = new Button("Play"); playButton.addEventHandler( ActionEvent.ACTION, (e) -> onClickPlay(e));
        pauseButton = new Button( "Pause"); pauseButton.addEventHandler( ActionEvent.ACTION, (e) -> onClickPause(e));
        exportButton = new Button( "Export..."); exportButton.addEventHandler( ActionEvent.ACTION, (e) -> onClickExport(e));
        
        bar2.getItems().addAll( playButton,
        						pauseButton,
        						exportButton);
        
        /// ImageViewer component
        
        gifViewer = new ImageView();
        currentGIF = new Image( getClass().getResource( "vis/empty-scene.gif").toExternalForm());
        gifViewer.setImage( currentGIF);
        gifViewer.setFitWidth( 380);
        gifViewer.setPreserveRatio( true);
        
        BorderPane rightBorderPane = new BorderPane();
        rightBorderPane.setBottom( bar2);
        rightBorderPane.setCenter( gifViewer);
        rightBorderPane.setMinWidth(400);
        
        rightBorderPane.getStylesheets().add( GifEditorMain.class.getResource("css/controls.css").toExternalForm());
        gifViewer.setId( "imageview");
        gifViewer.setOnMouseClicked( e -> onMouseClickScene(e));
        
        root.setRight( rightBorderPane);
        
        // set up the scene graph
        Scene scene = new Scene( root, 1200, 600);
        primaryStage.setScene( scene);
        primaryStage.setTitle( "GIFScript Editor");
        primaryStage.show();
        
        scene.getWindow().setOnCloseRequest( (e) -> onCloseWindow(e));
        
        this.stage = primaryStage;
    }
    
    ///// program entry point    
    public static void main(String[] args) {
        launch(args);
    }
    	
    ///// internal helper for highlighting
    private StyleSpans<Collection<String>> computeHighlighting(String text) {
    	fileChanged = true;
    	
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        
        while(matcher.find()) {
        	
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                    matcher.group("PAREN") != null ? "paren" :
                    matcher.group("BRACE") != null ? "brace" :
                    matcher.group("BRACKET") != null ? "bracket" :
                    matcher.group("SEMICOLON") != null ? "semicolon" :
                    matcher.group("STRING") != null ? "string" :
                    matcher.group("COMMENT") != null ? "comment" :
                    null; /* never happens */ assert styleClass != null;
                    
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
    
    ///// JavaScript highlighting:
    
    private static final String[] KEYWORDS = new String[] {
        "abstract", "arguments", "boolean", "break", "byte", "case", "catch", 
        "char", "class", "const", "continue", "default", "debugger", "delete",
        "do", "double", "else", "enum", "eval", "export", "false", "extends", 
        "final", "finally", "float", "for", "function", "goto", "if", "implements",
        "import", "in", "instanceof", "int", "interface", "let", "long", "native",
        "new", "null", "package", "private", "protected", "public", "return", "short",
        "static", "super", "switch", "synchronized", "this", "throw", "throws", "transient",
        "true", "typeof", "var", "try", "void", "volatile", "while", "with", "yield"
    };
    
    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
    
    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
            + "|(?<PAREN>" + PAREN_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
            + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
            + "|(?<STRING>" + STRING_PATTERN + ")"
            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    // executed on new button clicked
    public void onClickNew ( ActionEvent event)
    {
    	if ( checkDiscardChanges() < 0)
    		return;
    	
		saveFile = null;
		fileChanged = true;
		
		codeArea.replaceText( "");
    }
    
    // executed on save button clicked
    public void onClickSave ( ActionEvent event)
    {
    	File scriptFile;
    	
    	if ( saveFile == null)
    	{
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.setTitle( "Save GIFScript file");
	    	fileChooser.getExtensionFilters().addAll(
	    	         new FileChooser.ExtensionFilter("JavaScript files", "*.js"),
	    	         new FileChooser.ExtensionFilter("All Files", "*.*"));
	    	scriptFile = fileChooser.showSaveDialog( stage);
	    	
	    	if( scriptFile == null)
	    		return;
	    	else
	    	{
	    		saveFile = scriptFile;
	    	}
    	}
    	else
    		scriptFile = saveFile;
    	
    	String script = codeArea.getText();
    	
    	try {
    		Files.write( scriptFile.toPath(), script.getBytes( StandardCharsets.UTF_8));
    		
    		fileChanged = false;
    	}
    	catch (IOException e)
    	{

    		Alert alert = new Alert( AlertType.ERROR);
    		alert.setTitle( "Error");
    		alert.setHeaderText( "Error saving " + scriptFile.getName());
    		alert.setContentText( "Could not save GIFScript file");

    		String exceptionText = e.toString();

    		// display error details
    		Label label = new Label("IOException stack trace:");

    		TextArea textArea = new TextArea(exceptionText);
    		textArea.setEditable( false);
    		textArea.setWrapText( true);

    		textArea.setMaxWidth( Double.MAX_VALUE);
    		textArea.setMaxHeight( Double.MAX_VALUE);
    		GridPane.setVgrow( textArea, Priority.ALWAYS);
    		GridPane.setHgrow( textArea, Priority.ALWAYS);

    		GridPane expContent = new GridPane();
    		expContent.setMaxWidth( Double.MAX_VALUE);
    		expContent.add( label, 0, 0);
    		expContent.add( textArea, 0, 1);

    		alert.getDialogPane().setExpandableContent(expContent);

    		alert.showAndWait();
    	}
    }


	// executed on save as button clicked
	public void onClickSaveAs ( ActionEvent event)
    {
    	File scriptFile;

	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle( "Save GIFScript file");
	    fileChooser.getExtensionFilters().addAll(
   	         new FileChooser.ExtensionFilter("JavaScript files", "*.js"),
   	         new FileChooser.ExtensionFilter("All Files", "*.*"));
	    scriptFile = fileChooser.showSaveDialog( stage);
	    	
	    if( scriptFile == null)
	    	return;
	    else
	    {
	    	saveFile = scriptFile;
	    }
    	
    	String script = codeArea.getText();
    	
    	try {
    		Files.write( scriptFile.toPath(), script.getBytes( StandardCharsets.UTF_8));
    		
    		fileChanged = false;
    	} catch ( IOException e) {

    		Alert alert = new Alert( AlertType.ERROR);
    		alert.setTitle( "Error");
    		alert.setHeaderText( "Error saving " + scriptFile.getName());
    		alert.setContentText( "Could not save GIFScript file");

    		String exceptionText = e.toString();

    		// display error details
    		Label label = new Label("IOException stack trace:");

    		TextArea textArea = new TextArea(exceptionText);
    		textArea.setEditable( false);
    		textArea.setWrapText( true);

    		textArea.setMaxWidth( Double.MAX_VALUE);
    		textArea.setMaxHeight( Double.MAX_VALUE);
    		GridPane.setVgrow( textArea, Priority.ALWAYS);
    		GridPane.setHgrow( textArea, Priority.ALWAYS);

    		GridPane expContent = new GridPane();
    		expContent.setMaxWidth( Double.MAX_VALUE);
    		expContent.add( label, 0, 0);
    		expContent.add( textArea, 0, 1);

    		alert.getDialogPane().setExpandableContent(expContent);

    		alert.showAndWait();
    	}
    }

    // executed on open button clicked
    public void onClickOpen ( ActionEvent event)
    {
    	if ( checkDiscardChanges() < 0)
    		return;
    	
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle( "Open GIFScript file");
    	fileChooser.getExtensionFilters().addAll(
    	         new FileChooser.ExtensionFilter("JavaScript files", "*.js"),
    	         new FileChooser.ExtensionFilter("All Files", "*.*"));
    	File scriptFile = fileChooser.showOpenDialog( stage);
    	
    	if ( scriptFile != null)
    	{
    		try{
    			String script = readFile( scriptFile.toPath(), StandardCharsets.UTF_8);
    			
    			codeArea.replaceText( script);
    			saveFile = scriptFile;
    			fileChanged = false;
    		}
    		catch ( IOException e)
    		{
    	    	Alert alert = new Alert( AlertType.ERROR);
    			alert.setTitle( "Error");
    			alert.setHeaderText( "Error opening " + scriptFile.getName());
    			alert.setContentText( "Could not open GIFScript file");
    			
    			String exceptionText = e.toString();

    			// display error details
    			Label label = new Label("IOException stack trace:");

    			TextArea textArea = new TextArea(exceptionText);
    			textArea.setEditable( false);
    			textArea.setWrapText( true);

    			textArea.setMaxWidth( Double.MAX_VALUE);
    			textArea.setMaxHeight( Double.MAX_VALUE);
    			GridPane.setVgrow( textArea, Priority.ALWAYS);
    			GridPane.setHgrow( textArea, Priority.ALWAYS);

    			GridPane expContent = new GridPane();
    			expContent.setMaxWidth( Double.MAX_VALUE);
    			expContent.add( label, 0, 0);
    			expContent.add( textArea, 0, 1);

    			alert.getDialogPane().setExpandableContent(expContent);

    			alert.showAndWait();
    		}
    	}
    }
    
    // executed on play button clicked
    public void onClickPlay ( ActionEvent event)
    {
    	
    }

    // executed on pause button clicked
    public void onClickPause ( ActionEvent event)
    {
    	
    }

    // executed on export button clicked
    public void onClickExport ( ActionEvent event)
    {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle( "Export GIF");
    	File target = fileChooser.showSaveDialog( stage);
    	
    	if ( target == null)
    		return;
    	else
    	{
    		exportGIF( target);
    	}
    }

    // executed when GIF viewer clicked
    public void onMouseClickScene ( MouseEvent event)
    {
    	if  ( event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getClickCount() == 2)
    	{
    		int index = codeArea.getCaretPosition();
    		int px, py;
    		
    		px = (int) event.getX();
    		py = (int) event.getY();
    		
    		px = (int) ( px * currentGIF.getWidth() / gifViewer.getLayoutBounds().getWidth());
    		py = (int) ( py * currentGIF.getHeight() / gifViewer.getLayoutBounds().getHeight());
    		
    		codeArea.insertText( index, "new Point2(" + px + ", " + py + ")");
    	}
    }
    
    // window close attempt
    public void onCloseWindow ( WindowEvent e)
    {
    	if ( checkDiscardChanges() < 0)
    		e.consume();
    }
    
    // private helpers
    
    private static String readFile ( Path path, Charset encoding) 
    		  throws IOException 
    {
    	  byte[] encoded = Files.readAllBytes( path);
    	  return new String(encoded, encoding);
    }
    
    private int checkDiscardChanges ( )
    {
    	if ( !fileChanged)
    		return 1;
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle( "Save changes");
    	alert.setHeaderText( "You made changes to the file.");
    	alert.setContentText( "Save changes made to " + ( saveFile == null ? "unnamed" : saveFile.getName()) + "?");

    	ButtonType buttonTypeSave = new ButtonType( "Save...");
    	ButtonType buttonTypeDiscard = new ButtonType( "Discard");
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

    	alert.getButtonTypes().setAll( buttonTypeSave, buttonTypeDiscard, buttonTypeCancel);

    	Optional<ButtonType> result = alert.showAndWait();
    	
    	if (result.get() == buttonTypeSave)
    	{
    	    onClickSave( null);    		
    		return 1;
    	}
    	else if (result.get() == buttonTypeDiscard)
    	{
    	    return 1;
    	}
    	else 
    	{
    		return -1;
    	}
    }    
    
    private void exportGIF ( File f)
    {
    	// TODO
    }
    
}