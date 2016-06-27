
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* GIFScript demo, generates a parametrized curves using transformations */

setDim( 400, 400);
backgroundColor( Color.WHITE);

c1 = circle( 200, 200, 120, new Color( "#003300"));
circle( 200, 200, 120, new Color( "#003300")).setStroke( STROKE_THIN);
cpeg = circle( 320, 200, 3, new Color( "#003300"), new Color( "#003300"));
c2 = circle( 320, 200, 45, new Color( "#003300"));
c2.setStroke( STROKE_THIN);
pen = circle( 365, 200, 5, new Color( "#990000"), new Color( "#990000"));
c1.setVisible( false);

g1 = group( c2, pen);
g1.setTransformCenter( 320, 200);

g2 = group( c1, cpeg, g1);
g2.setTransformCenter( 200, 200);

lseq = lines( 365, 200);
lseq.setStrokeColor( Color.RED);

frame( 20, false);

for ( i = 0; i < 350; i++)
{
    g2.rotate( Math.PI * 2 / 350);
    g1.rotate( Math.PI * 16 / 350);

    lseq.add( pen.getTransformCenter());

    frame( 20, false);
}