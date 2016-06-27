
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* GIFScript demo, manually renders a 3D spinning cube animation         */

var theta = 0.0;
var phi = 0.0;

for (i = 0; i < 200; i++)
{
    var p1x = 60 * Math.sin(phi) * Math.cos(theta);
    var p1y = 60 * Math.sin(phi) * Math.sin(theta);

    var p2x = 26.83 * Math.sin(theta) - 53.67 * Math.cos(phi) * Math.cos(theta);
    var p2y = -26.83 * Math.cos(theta) - 53.67 * Math.cos(phi) * Math.sin(theta);

    var p3x = 53.67 * Math.sin(theta) + 26.83 * Math.cos(phi) * Math.cos(theta);
    var p3y = -53.67 * Math.cos(theta) + 26.83 * Math.cos(phi) * Math.sin(theta);

    line(128, 128, 128 + p1x, 128 + p1y, Color.CYAN);
    line(128, 128, 128 + p2x, 128 + p2y, Color.CYAN);
    line(128, 128, 128 + p3x, 128 + p3y, Color.CYAN);

    line(128 + p1x, 128 + p1y, 128 + p1x + p2x, 128 + p1y + p2y, Color.CYAN);
    line(128 + p2x, 128 + p2y, 128 + p1x + p2x, 128 + p1y + p2y, Color.CYAN);

    line(128 + p1x, 128 + p1y, 128 + p1x + p3x, 128 + p1y + p3y, Color.CYAN);
    line(128 + p3x, 128 + p3y, 128 + p1x + p3x, 128 + p1y + p3y, Color.CYAN);

    line(128 + p3x, 128 + p3y, 128 + p3x + p2x, 128 + p2y + p3y, Color.CYAN);
    line(128 + p2x, 128 + p2y, 128 + p3x + p2x, 128 + p2y + p3y, Color.CYAN);

    line(128 + p1x + p2x, 128 + p1y + p2y, 128 + p1x + p2x + p3x, 128 + p2y + p1y + p3y, Color.CYAN);
    line(128 + p1x + p3x, 128 + p1y + p3y, 128 + p1x + p2x + p3x, 128 + p2y + p1y + p3y, Color.CYAN);
    line(128 + p2x + p3x, 128 + p2y + p3y, 128 + p1x + p2x + p3x, 128 + p2y + p1y + p3y, Color.CYAN);

    theta += 3.1415 * 2 / 200;
    phi += 3.1415 * 2 / 200;

    frame(20);
}