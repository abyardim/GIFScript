
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* GIFScript demo, draws the "butterfly" curve using GIFScript tweens    */

setDim( 300, 300);
backgroundColor( Color.WHITE);

scale = 38;

lseq = lines( 150, 150);
lseq.setStrokeColor( Color.RED);
pen = dot( 0, 0);

t = interval( 0, 8 * Math.PI, 1.25);
xvals = t.map( function(t) Math.sin(t) * ( Math.exp(Math.cos(t)) - 2*Math.cos(4*t) - Math.pow( Math.sin( t/12),5)));
xvals = xvals.map( function(t) 150 + scale * t);

yvals = t.map( function(t) Math.cos(t) * ( Math.exp(Math.cos(t)) - 2*Math.cos(4*t) - Math.pow( Math.sin( t/12),5)));
yvals = yvals.map( function(t) 150 + scale * t);

apply( xvals, pen, "p.x");
apply( yvals, pen, "p.y");

for ( i = 0; i < 600; i++)
{
    frame( 20, false);
    lseq.add( pen.getTransformCenter());
    update( 20 );
    lseq.add( pen.getTransformCenter());
    update( 20 );
    lseq.add( pen.getTransformCenter());
    update( 20 );
    lseq.add( pen.getTransformCenter());
}