var theta = 0.0;
var phi = 0.0;
var a = 100;

for (i = 0; i < 200; i++)
{
    line( 128, 128, 128 + 60 * Math.sin(phi) * Math.cos(theta), 128 + 60 * Math.sin(phi) * Math.sin(theta), Color.CYAN);
    line( 128, 128, 128 + 60 * Math.sin(theta), 128 - 60 * Math.cos(theta), Color.CYAN);
    line( 128, 128, 128 - 60 * Math.cos(phi) * Math.cos(theta), 128 - 60 * Math.cos(phi) * Math.sin(theta), Color.CYAN);

    line(128 + 60 * Math.sin(phi) * Math.cos(theta), 128 + 60 * Math.sin(phi) * Math.sin(theta),    128 + 60 * Math.sin(phi) * Math.cos(theta) + 60 * Math.sin(theta), 128 + 60 * Math.sin(phi) * Math.sin(theta) - 60 * Math.cos(theta), Color.CYAN);
    line(128 + 60 * Math.sin(theta), 128 - 60 * Math.cos(theta),                                    128 + 60 * Math.sin(phi) * Math.cos(theta) + 60 * Math.sin(theta), 128 + 60 * Math.sin(phi) * Math.sin(theta) - 60 * Math.cos(theta), Color.CYAN);

    line(128 + 60 * Math.sin(phi) * Math.cos(theta), 128 + 60 * Math.sin(phi) * Math.sin(theta),    128 + 60 * Math.sin(phi) * Math.cos(theta) - 60 * Math.cos(phi) * Math.cos(theta), 128 + 60 * Math.sin(phi) * Math.sin(theta) - 60 * Math.cos(phi) * Math.sin(theta), Color.CYAN);
    line(128 - 60 * Math.cos(phi) * Math.cos(theta), 128 - 60 * Math.cos(phi) * Math.sin(theta),    128 + 60 * Math.sin(phi) * Math.cos(theta) - 60 * Math.cos(phi) * Math.cos(theta), 128 + 60 * Math.sin(phi) * Math.sin(theta) - 60 * Math.cos(phi) * Math.sin(theta), Color.CYAN);

    line(128 + 60 * Math.sin(theta), 128 - 60 * Math.cos(theta),                                    128 + 60 * Math.sin(theta) - 60 * Math.cos(phi) * Math.cos(theta), 128 - 60 * Math.cos(theta) - 60 * Math.cos(phi) * Math.sin(theta), Color.CYAN);
    line(128 - 60 * Math.cos(phi) * Math.cos(theta), 128 - 60 * Math.cos(phi) * Math.sin(theta),    128 + 60 * Math.sin(theta) - 60 * Math.cos(phi) * Math.cos(theta), 128 - 60 * Math.cos(theta) - 60 * Math.cos(phi) * Math.sin(theta), Color.CYAN);

    line(128 + 60 * Math.sin(phi) * Math.cos(theta) + 60 * Math.sin(theta) - 60 * Math.cos(phi) * Math.cos(theta), 128 + 60 * Math.sin(phi) * Math.sin(theta) - 60 * Math.cos(theta) - 60 * Math.cos(phi) * Math.sin(theta),        128 + 60 * Math.sin(phi) * Math.cos(theta) + 60 * Math.sin(theta), 128 + 60 * Math.sin(phi) * Math.sin(theta) - 60 * Math.cos(theta), Color.CYAN);
    line(128 + 60 * Math.sin(phi) * Math.cos(theta) + 60 * Math.sin(theta) - 60 * Math.cos(phi) * Math.cos(theta), 128 + 60 * Math.sin(phi) * Math.sin(theta) - 60 * Math.cos(theta) - 60 * Math.cos(phi) * Math.sin(theta),        128 + 60 * Math.sin(phi) * Math.cos(theta) - 60 * Math.cos(phi) * Math.cos(theta), 128 + 60 * Math.sin(phi) * Math.sin(theta) - 60 * Math.cos(phi) * Math.sin(theta), Color.CYAN);
    line(128 + 60 * Math.sin(phi) * Math.cos(theta) + 60 * Math.sin(theta) - 60 * Math.cos(phi) * Math.cos(theta), 128 + 60 * Math.sin(phi) * Math.sin(theta) - 60 * Math.cos(theta) - 60 * Math.cos(phi) * Math.sin(theta), 128 + 60 * Math.sin(theta) - 60 * Math.cos(phi) * Math.cos(theta), 128 - 60 * Math.cos(theta) - 60 * Math.cos(phi) * Math.sin(theta), Color.CYAN);

    theta += 3.1415 * 2 / 200;
    phi += 3.1415 * 2 / 200;

    newFrame(20);
}