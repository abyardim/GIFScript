setDim( 512, 512);
backgroundColor( Color.BLACK);

bezier2( 250, 250, 510, 258, 250, 500).setStrokeColor( Color.CYAN);
bezier3( 250, 250, 270, 230, 480, 230, 500, 250).setStrokeColor( Color.BLUE);

r = rectangle( 200, 200, 400, 400);
r.setStrokeColor( Colors.Almond);
r.setFillColor( Colors.DarkElectricBlue);
r.setStroke(STROKE_DASHED);

r2 = rectangle( 160, 360, 240, 440);
r2.setStrokeColor( Color.GREEN);

g2 = group( r, r2);
g2.setTransformCenter( r.getTransformCenter().x, r.getTransformCenter().y);

c1 = circle( 100, 100, 50);
c2 = circle( 100, 200, 50);
c1.setStrokeColor( Colors.DarkKhaki);
c2.setStrokeColor( Color.BLUE);

g = group( c1, c2);

g.setTransformCenter( 100, 150);

lseq = lines( r2.points[0].x, r2.points[0].y);
lseq.setStrokeColor( Color.WHITE);

s1 = rectangle( 300, 300, 500, 500);
scomb = combine( s1);
s2 = rectangle( 350, 350, 450, 450);
scomb.subtract( s2);
s2.setVisible( false);
scomb.setDrawFill( true);
scomb.setFillColor( Color.BLUE);

lseq.setStroke( STROKE_DASHED);

writing = write( "Hello!!!", 50, 50);
writing.setFillColor( Color.WHITE);

frame( 20, false);

for ( i = 0; i < 100; i++)
{
    r2.rotate( Math.PI * 4 / 100);
    g2.rotate( Math.PI * 2 / 100);
    g.rotate( Math.PI * 2 / 100);
    s2.rotate( Math.PI * 2 / 100);
    // writing.rotate( Math.PI * 2 / 100);
    // writing.scale( 0.98);
    writing.translate( 2, 2);
    lseq.add( r2.points[0]);

    frame( 20, false);
}