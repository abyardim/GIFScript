backgroundColor(Colors.White)
A = new Point2(128,60); C = new Point2(178,146); B = new Point2(78, 146);
P = new Point2((A.x + B.x) / 2, (A.y + B.y) / 2)
Q = new Point2((A.x + C.x) / 2, (A.y + C.y) / 2)
R = new Point2(P.x, B.y)
S = new Point2(Q.x, B.y)
r = 2.83473 / 6.61438
X = new Point2(R.x+(Q.x-R.x) * r, R.y+(Q.y-R.y) * r)
Y = new Point2(R.x+(Q.x-R.x) * (1-r), R.y+(Q.y-R.y) * (1-r))

// p = polyPoints( A, B, C)
p1 = polyPoints(P, X, Q, A); p1.setDrawFill(true); p1.setFillColor( Color.GREEN);
p2 = polyPoints(B, R, X, P); p2.setDrawFill(true); p2.setFillColor( Color.BLUE);
p3 = polyPoints(Y, S, R); p3.setDrawFill(true); p3.setFillColor( Color.RED);
p4 = polyPoints(Y, S, C, Q); p4.setDrawFill(true); p4.setFillColor( Colors.Aurometalsaurus);

g4 = group(p4);	g4.setTransformCenter(S.x, S.y);
g3 = group(p3, g4); g3.setTransformCenter(R.x, R.y);
g2 = group(p2, g3); g2.setTransformCenter(P.x, P.y);
g1 = group(p1, g2)

ANGLE = - Math.PI
for( i = 0; i < 250; i++)
{
	g4.rotate( ANGLE / 250)
	g3.rotate( ANGLE / 250)
	g2.rotate( ANGLE / 250)
	g1.translate(0, 0.15)
	frame(20, false)
}

for ( i = 0; i < 170; i++)
	frame( 20, false)
