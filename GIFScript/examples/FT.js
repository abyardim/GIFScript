
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Script example, compares the Fourier series coefficients of a periodic*/
/* rectangular pulse with the FT of a nonperiodic pulse */

module("signals")

backgroundColor(Colors.White)
setViewport(0,0,256,256)
setScale(-50,50,-0.3,0.7)

tau = 1/2
A = 1

w = new Array(1000)
y = new Array(1000)
for ( i = 0; i < 1000; i++)
{
	w[i] = -50 + i * 100 / 999
	y[i] = A * Math.sin(w[i] * tau / 2) / (w[i] / 2) 
}

function plotSeriesCoefficients(T)
{
	omega = 2 * Math.PI / T
	ak = new Array(501)
	w2 = new Array(501)
	for ( i = 0; i < 501; i++)
	{
		w2[i] = (i-250) * omega
		if ( i-250 == 0)
			ak[i] = A * tau / T * T
		else
			ak[i] = 2 * A / Math.PI / (i-250) * Math.sin( (i-250) * Math.PI * tau / T) / 2 * T
	}
	scaleFactor = omega
	return plotDiscrete( -250 * scaleFactor, ak, Colors.Blue, scaleFactor)
}

var Ts = [0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 6, 7, 8, 9, 10, 15, 20]

for ( j = 0; j < Ts.length; j++)
{
	drawAxes()
	plotContinuous(w, y)

	plotSeriesCoefficients(Ts[j])

	write("T = " + Ts[j], 50, 50, FONT_SMALL)
	frame(500)
}
