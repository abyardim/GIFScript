var Color = Java.type( "java.awt.Color");
var Point2 = Java.type("java.awt.geom.Point2D.Double");

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

function circle ( centerx, centery, radius, strokeColor, fillColor, stroke)
{
    if ( typeof strokeColor == "undefined")
       return _gifscript_env.drawCircle(centerx, centery, radius, false, true, Color.BLACK, null);
    else if ( typeof fillColor == "undefined")
        return _gifscript_env.drawCircle(centerx, centery, radius, false, true, strokeColor, null);
    else if ( typeof stroke == "undefined")
        return _gifscript_env.drawCircle(centerx, centery, radius, true, true, strokeColor, fillColor);
    
    return _gifscript_env.drawCircle(centerx, centery, radius, true, stroke, strokeColor, fillColor);
}

function line ( p1x, p1y, p2x, p2y, c) {
    if (typeof c !== "undefined") {
        return _gifscript_env.drawLine(p1x, p1y, p2x, p2y, c);
    }
    else {
        return _gifscript_env.drawLine(p1x, p1y, p2x, p2y, Color.BLACK);
    }
}

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

function poly ( points, strokeColor, fillColor, stroke)
{
    pointArray = Java.to( points, "java.awt.geom.Point2D.Double[]");

    print( "" + typeof pointArray);

    if ( typeof strokeColor == "undefined")
        return _gifscript_env.drawPolygon( pointArray, true, false, Color.BLACK, Color.WHITE);
    else if ( typeof fillColor == "undefined")
        return _gifscript_env.drawPolygon( pointArray, true, true, strokeColor, fillColor);
    else if ( typeof stroke == "undefined")
        return _gifscript_env.drawPolygon( pointArray, stroke, true, strokeColor, fillColor);
}

function polyPoints ( )
{
    if ( arguments.length == 0)
        return null;

    return poly ( arguments);
}

function rectangle ( p1x, p1y, p2x, p2y, strokeColor, fillColor)
{
    points = [ new Point2( p1x, p1y), new Point2( p1x, p2y), 
                new Point2( p2x, p2y), new Point2( p2x, p1y)];

    poly ( points, strokeColor, fillColor);
}

function regularPoly ( centerx, centery, edges, sidelen)
{
    points = new Array( edges);
    radius = sidelen / ( 2 * Math.sin( Math.PI / edges));

    for ( i = 0; i < edges; i++)
    {
        points[i] = new Point2( centerx + radius * Math.cos( i * Math.PI * 2 / edges),
                                centery + radius * Math.sin( i * Math.PI * 2 / edges));
    }

    print( "" + typeof points[0]);

    polyPoints( points);
}