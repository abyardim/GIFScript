package gifscriptui;
import java.util.Collection;
import java.util.Collections;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

public class GifEditorMain extends Application {

	private Button saveButton, saveAsButton, openButton;
	private Button playButton, pauseButton, exportButton;
	
	CodeArea codeArea;
	TextArea outputTextArea;
	ImageView gifViewer;
	
	
	
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        
    	///// left half: editor, output and tool bar
        
        BorderPane leftBorderPane = new BorderPane();
        
        ToolBar bar1 = new ToolBar();
        saveButton = new Button( "Save...");
        saveAsButton = new Button( "Save as...");
        openButton = new Button( "Open...");
        bar1.getItems().addAll( saveButton,
        						saveAsButton,
        						openButton);        
        
        leftBorderPane.setTop( bar1);
        
        /// JavaScript Editor component
        
        codeArea = new CodeArea();
        
        String stylesheet = GifEditorMain.class.getResource("css/java-style.css").toExternalForm();
        IntFunction<String> format = (digits -> " %" + digits + "d ");

        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea, format));
        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            codeArea.setStyleSpans(0, computeHighlighting(newText));
        });
        
        StackPane pane = new StackPane( codeArea);
        pane.getStylesheets().add(stylesheet);
        pane.setMinWidth( 300);
        
        leftBorderPane.setCenter( pane);
        
        /// text area for "compiler" output
        
        outputTextArea = new TextArea();
        outputTextArea.setMaxHeight( 100);
        leftBorderPane.setBottom( outputTextArea);
        
        root.setCenter( leftBorderPane);
        
        ///// right half of the scene: ImageViewer & toolbar
         
        ToolBar bar2 = new ToolBar();
        playButton = new Button("Play");
        pauseButton = new Button( "Pause");
        exportButton = new Button( "Export...");
        
        bar2.getItems().addAll( playButton,
        						pauseButton,
        						exportButton);
        
        /// ImageViewer component
        
        gifViewer = new ImageView();
        
        BorderPane rightBorderPane = new BorderPane();
        rightBorderPane.setBottom( bar2);
        rightBorderPane.setCenter( gifViewer);
        rightBorderPane.setMinWidth(350);
        
        root.setRight( rightBorderPane);
        
        // set up the scene graph
        Scene scene = new Scene( root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("GIFScript Editor");
        primaryStage.show();
    }
    
    ///// program entry point    
    public static void main(String[] args) {
        launch(args);
    }
    	
    ///// internal helper for highlighting
    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
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
    
    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
            + "|(?<PAREN>" + PAREN_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
            + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
            + "|(?<STRING>" + STRING_PATTERN + ")"
    );
    
    
    
}