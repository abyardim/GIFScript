backgroundColor( Color.WHITE);

t1 = write( "hello", 0, 0);
t1.translate( 50, 50);
t1.scale( 2);

r1 = rectangle( 0, 0, 200, 200);

scomb = combine( r1);

scomb.subtract( t1);

scomb.setDrawFill( true);
scomb.setDrawStroke( true);
scomb.setStroke( STROKE_DEFAULT);
scomb.setStrokeColor( Color.BLACK);
scomb.setFillColor( Color.BLUE);

frame( 15, false);

for ( i = 0; i < 50; i++)
{
    t1.rotate( - Math.PI * 2 / 50);

    frame( 15, false);
}