var Color = Java.type( "java.awt.Color");
var Point = Java.type("java.awt.geom.Point2D.Double");

if ( typeof _gifscript_env == 'undefined') {
    throw new Error( "_gifscript_env not loaded.");
}

function line ( p1x, p1y, p2x, p2y, c)
{
    if (typeof c !== "undefined")
    {
        return _gifscript_env.line( p1x, p1y, p2x, p2y, c);
    }
    else
    {
        return _gifscript_env.line( p1x, p1y, p2x, p2y);
    }
}

function newFrame ( delay, clear)
{
    if (typeof clear !== "undefined")
    {
        return _gifscript_env.frame( delay, clear);
    }
    else
    {
        return _gifscript_env.frame( delay);
    }
}