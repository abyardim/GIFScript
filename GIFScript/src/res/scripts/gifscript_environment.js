
// check if the Java interface object is loaded
if ( typeof _gifscript_env == 'undefined') {
    throw new Error( "_gifscript_env not loaded.");
}

//// GIF control

function frame ( delay, clear)
{
    if (typeof clear !== "undefined")
    {
        return _gifscript_env.newFrame( delay, clear);
    }
    else
    {
        return _gifscript_env.newFrame( delay, true);
    }
}

function setDim ( width, height) 
{
    _gifscript_env.setGIFDimensions( width, height);
}

function backgroundColor ( c)
{
    _gifscript_env.setBackgroundColor( c);
}


//// geometry

// circle, from center and radius
function circle ( centerx, centery, radius, strokeColor, fillColor, stroke)
{
    c = null;

    if ( typeof strokeColor == "undefined")
        c = _gifscript_env.drawCircle(centerx, centery, radius, false, true, Color.BLACK, null);
    else if ( typeof fillColor == "undefined")
        c = _gifscript_env.drawCircle(centerx, centery, radius, false, true, strokeColor, null);
    else if ( typeof stroke == "undefined")
        c = _gifscript_env.drawCircle(centerx, centery, radius, true, true, strokeColor, fillColor);
    else
        c = _gifscript_env.drawCircle(centerx, centery, radius, true, stroke, strokeColor, fillColor);

    c.setTransformCenter( centerx, centery);
    c.setStroke( STROKE_DEFAULT);

    return c;
}

function dot ( x, y, col)
{
    if ( typeof col == "undefined")
        col = Color.BLACK;

    return circle ( x, y, 3, col, col, false);
}

function line ( p1x, p1y, p2x, p2y, c) {
    if (typeof c !== "undefined") {
        return _gifscript_env.drawLine(p1x, p1y, p2x, p2y, c);
    }
    else {
        return _gifscript_env.drawLine(p1x, p1y, p2x, p2y, Color.BLACK);
    }
}

// oval, using the center, two radii
function oval ( centerx, centery, radius1, radius2, strokeColor, fillColor, stroke)
{
    if ( typeof strokeColor == "undefined")
       return _gifscript_env.drawOval( centerx, centery, radius1, radius2, false, true, Color.BLACK, null);
    else if ( typeof fillColor == "undefined")
        return _gifscript_env.drawOval( centerx, centery, radius1, radius2, false, true, strokeColor, null);
    else if ( typeof stroke == "undefined")
        return _gifscript_env.drawOval( centerx, centery, radius1, radius2, true, true, strokeColor, fillColor);
    
    return _gifscript_env.drawOval( centerx, centery, radius1, radius2, true, stroke, strokeColor, fillColor);
}

// arc, using the center, two radii
function arc ( centerx, centery, radius1, radius2, angle1, angle2, strokeColor, fillColor, stroke)
{
    if ( typeof strokeColor == "undefined")
       return _gifscript_env.drawArc( centerx, centery, radius1, radius2, angle1, angle2, false, true, Color.BLACK, null);
    else if ( typeof fillColor == "undefined")
        return _gifscript_env.drawArc( centerx, centery, radius1, radius2, angle1, angle2, false, true, strokeColor, null);
    else if ( typeof stroke == "undefined")
        return _gifscript_env.drawArc( centerx, centery, radius1, radius2, angle1, angle2, true, true, strokeColor, fillColor);
    
    return _gifscript_env.drawArc( centerx, centery, radius1, radius2, angle1, angle2, true, stroke, strokeColor, fillColor);
}

// polygon
function poly ( points, strokeColor, fillColor, stroke)
{
    pointArray = Java.to( points, "java.awt.geom.Point2D.Double[]");

    print( "test2");

    if ( typeof strokeColor == "undefined")
        return _gifscript_env.drawPolygon( pointArray, true, false, Color.BLACK, Color.WHITE);
    else if ( typeof fillColor == "undefined")
        return _gifscript_env.drawPolygon( pointArray, true, true, strokeColor, fillColor);
    else if ( typeof stroke == "undefined")
        return _gifscript_env.drawPolygon( pointArray, stroke, true, strokeColor, fillColor);
    else
        return _gifscript_env.drawPolygon( pointArray, stroke, true, strokeColor, fillColor);
}

// polygon from a list of points
function polyPoints ( )
{
    if ( arguments.length == 0)
        return null;

    return poly ( arguments);
}

// rectangle
function rectangle ( p1x, p1y, p2x, p2y, strokeColor, fillColor, stroke)
{
    points = [ new Point2( p1x, p1y), new Point2( p1x, p2y), 
                new Point2( p2x, p2y), new Point2( p2x, p1y)];

    polygon1 = poly ( points, strokeColor, fillColor, stroke);
    polygon1.setStroke( STROKE_DEFAULT);
    polygon1.setTransformCenter( ( p1x + p2x) / 2, ( p1y + p2y) / 2);

    return polygon1;
}

// regular polygon
function regularPoly ( centerx, centery, edges, sidelen)
{
    points = new Array( edges);
    radius = sidelen / ( 2 * Math.sin( Math.PI / edges));

    for ( i = 0; i < edges; i++)
    {
        points[i] = new Point2( centerx + radius * Math.cos( i * Math.PI * 2 / edges),
                                centery + radius * Math.sin( i * Math.PI * 2 / edges));
    }

    polygon1 = poly( points, Color.BLACK, Color.WHITE, true);
    polygon1.setTransformCenter( centerx, centery);

    return polygon1;
}

// quadratic bezier curve
function bezier2 ( p1x, p1y, p2x, p2y, p3x, p3y, color, stroke)
{
    bezier = null;

    if ( typeof color == "undefined")
    {
        bezier = _gifscript_env.drawBezier2( new Point2( p1x, p1y), new Point2( p2x, p2y), new Point2( p3x, p3y), STROKE_DEFAULT);
    }
    else if ( typeof stroke == "undefined")
    {
        bezier = _gifscript_env.drawBezier2( new Point2( p1x, p1y), new Point2( p2x, p2y), new Point2( p3x, p3y), STROKE_DEFAULT);
        bezier.setStrokeColor( color);
    }
    else
    {
        bezier = _gifscript_env.drawBezier2( new Point2( p1x, p1y), new Point2( p2x, p2y), new Point2( p3x, p3y), stroke);
        bezier.setStrokeColor( color);
    }

    return bezier;
}

// cubic bezier curve
function bezier3 ( p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y )
{
    return _gifscript_env.drawBezier3( new Point2( p1x, p1y), new Point2( p2x, p2y),
                                        new Point2( p3x, p3y), new Point2( p4x, p4y),
                                        STROKE_DEFAULT);
}

// sequence of line segments end to end
function lines ( psx, psy)                      // TODO variable args
{
    l = _gifscript_env.drawLineStrip( STROKE_DEFAULT, new Point2( psx, psy));

    return l;
}

// curved path
function compoundCurve ( psx, psy)                      // TODO variable args
{
    l = _gifscript_env.drawCurvedPath( new Point2( psx, psy), STROKE_DEFAULT);

    return l;
}


////// higher order methods

// combine shapes
function combine ( )
{
    var args = (arguments.length === 1 ? [arguments[0]] : Array.apply(null, arguments));  // TODO: clean array allocation code
    return _gifscript_env.combinePaths( Java.to( args, "SGeometricPrimitive[]"));
}

// combine shapes
function group ( )
{
    var args = (arguments.length === 1 ? [arguments[0]] : Array.apply(null, arguments));  // TODO: clean array allocation code
    return _gifscript_env.groupObjects( Java.to( args, "SceneObject[]"));
}

function write ( string, x, y, font)
{
    text = null;

    if ( typeof font == "undefined")
    {
       text = _gifscript_env.drawText( string, x, y, FONT_MEDIUM);
    }
    else
    {
        text = _gifscript_env.drawText( string, x, y, font);
    }

    return text;
}

function load( file, key)
{
    return _gifscript_env.loadImage( key, file);
}

function drawImage( key, x, y, scale)
{
    if ( typeof scale == "undefined")
        return _gifscript_env.drawImage( key, x, y, 1 );

    return _gifscript_env.drawImage( key, x, y, scale );
}

function interval ( start, end, speed)
{
    if ( end == Infinity)
        return _gifscript_env.generateInfiniteInterval( start, true,  speed);
    else if ( end == -Infinity)
        return _gifscript_env.generateInfiniteInterval( start, false,  speed);
    
    return _gifscript_env.generateInfiniteInterval( start, end,  speed);
}

function apply ( gen, target, property)
{
    _gifscript_env.applyTween( gen, target, property);
}

function clearAnimations ( target)
{
    _gifscript_env.clearTweens( target);
}

function clearAnimation ( target, property)
{
    _gifscript_env.clearTweens( target, property);
}

function update ( dt)
{
    _gifscript_env.updateScene( dt);
}