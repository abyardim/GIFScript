setDim( 512, 512);
backgroundColor( Color.WHITE);

circle ( 256, 256, 100, Color.WHITE, Color.CYAN, true);
regularPoly ( 256, 256, 7, 80);

frame( 100, false);

for ( i = 0; i < 10; i++)
{
    arc( 256, 256, 110 + i * 10, 110 + i * 10, 0, Math.PI / 4, new Color( Math.random(), Math.random(), Math.random()));

    frame( 100, false);
}

