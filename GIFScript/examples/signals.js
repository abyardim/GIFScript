
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* A GIFScript module for visualizing signals (discrete and continuous) */

scale = [-10, 10, -10, 10]
viewport = [0, 0, 100, 100]

function setViewport (x, y, width, height)
{
	viewport = [x, y, width, height]
}

function setScale ( minx, maxx, miny, maxy)
{
	scale = [minx, maxx, miny, maxy]
}

function mapToScreen (x, y)
{
	nx = viewport[0] + viewport[2] * (x - scale[0]) / (scale[1] - scale[0])
	ny = viewport[1] + viewport[3] * (1- (y - scale[2]) / (scale[3] - scale[2]))

	return new Point2(nx, ny)
}

function drawAxes ( drawx, drawy)
{
	if ( typeof drawx == "undefined")
		drawx = true;
	if ( typeof drawy == "undefined")
		drawy = true;

	origin = mapToScreen(0, 0)

	if ( drawy && origin.x > viewport[0] && origin.x < viewport[0] + viewport[2])
		line( origin.x, viewport[1], origin.x, viewport[1] + viewport[3])

	if ( drawx && origin.y > viewport[1] && origin.y < viewport[1] + viewport[3])
		line( viewport[0], origin.y, viewport[0] + viewport[2], origin.y)
}

function plotDiscrete ( start, data, color, step)
{
	if( typeof step == "undefined")
	{
		step = 1
	}

	if( typeof color == "undefined")
	{
		color = Colors.Blue
	}

	zerolevel = mapToScreen(0, 0).y
	g = group()
	for ( i = 0; i < data.length; i++)
	{
		pos = mapToScreen(start + i * step, data[i])
		if ( pos.x >= viewport[0] && pos.x <= viewport[0] + viewport[2])
		{
			l = line(pos.x, pos.y, pos.x, zerolevel)
			l.setStrokeColor(color)
			c = circle(pos.x, pos.y, 2.5, null, color, false)

			g.add(l,c)
		} 
	}

	return g
}

function plotContinuous ( x, y, color)
{
	if( typeof color == "undefined")
	{
		color = Colors.Firebrick
	}

	pen = lines( mapToScreen(x[0], y[0]).x, mapToScreen(x[0], y[0]).y)
	for ( i = 1; i < x.length; i++)
	{
		pos = mapToScreen(x[i], y[i])
		
		pen.add(pos)
	}

	pen.setStrokeColor(color)

	return pen
}