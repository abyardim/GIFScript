setDim( 512, 512);
backgroundColor( Color.BLACK);

bezier2( 250, 250, 510, 258, 250, 500).setStrokeColor( Color.CYAN);
bezier3( 250, 250, 270, 230, 480, 230, 500, 250).setStrokeColor( Color.BLUE);

r = rectangle( 200, 200, 400, 400);
r.setStrokeColor( Color.CYAN);
r.setFillColor( Color.BLUE);

r2 = rectangle( 160, 360, 240, 440);
r2.setStrokeColor( Color.GREEN);

g2 = group( r, r2);
g2.setTransformCenter( r.getTransformCenter().x, r.getTransformCenter().y);

c1 = circle( 100, 100, 50);
c2 = circle( 100, 200, 50);
c1.setStrokeColor( Color.RED);
c2.setStrokeColor( Color.BLUE);

g = group( c1, c2);

g.setTransformCenter( 100, 150);

lseq = lines( r2.points[0].x, r2.points[0].y);
lseq.setStrokeColor( Color.WHITE);

frame( 20, false);

for ( i = 0; i < 100; i++)
{
    r2.rotate( Math.PI * 4 / 100);
    g2.rotate( Math.PI * 2 / 100);
    g.rotate( Math.PI * 2 / 100);

    lseq.add( r2.points[0]);

    frame( 20, false);
}