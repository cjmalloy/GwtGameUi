package com.cjmalloy.gameui.client.util;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

public class GraphicsUtil
{

    public static Canvas createCanvas(int width, int height)
    {
        Canvas c = Canvas.createIfSupported();
        resizeCanvas(c, width, height);
        return c;
    }

    public static void drawEllipse(Context2d g, double x, double y, double w, double h)
    {
        double kappa = .5522848;
        double xm = w / 2f; // x-middle
        double ym = h / 2f; // y-middle
        double ox = xm * kappa; // control point offset horizontal
        double oy = ym * kappa; // control point offset vertical

        g.save();
        g.translate(x, y);
        g.moveTo(0, ym);
        g.bezierCurveTo(0, ym - oy, xm - ox, 0, xm, 0);
        g.bezierCurveTo(xm + ox, 0, w, ym - oy, w, ym);
        g.bezierCurveTo(w, ym + oy, xm + ox, h, xm, h);
        g.bezierCurveTo(xm - ox, h, 0, ym + oy, 0, ym);
        g.restore();
    }

    public static void drawRoundedRect(Context2d g, double x, double y, double width, double height, double radius)
    {
        g.save();
        g.translate(x, y);
        g.moveTo(radius, 0);
        g.arcTo(width, 0, width, radius, radius);
        g.arcTo(width, height, width - radius, height, radius);
        g.arcTo(0, height, 0, height - radius, radius);
        g.arcTo(0, 0, radius, 0, radius);
        g.restore();
    }

    public static void drawRoundedRect(Context2d g,
            double x, double y, double width, double height,
            double topLeftRadiusX, double topLeftRadiusY,
            double topRightRadiusX, double topRightRadiusY,
            double bottomLeftRadiusX, double bottomLeftRadiusY,
            double bottomRightRadiusX, double bottomRightRadiusY)
    {
        g.save();
        g.translate(x, y);
        g.moveTo(topLeftRadiusX, 0);
        g.lineTo(width - topRightRadiusX, 0);
        x = width;
        y = 0;
        g.bezierCurveTo(x, y, x, y, width, topRightRadiusY);
        g.lineTo(width, height - bottomRightRadiusY);
        x = width;
        y = height;
        g.bezierCurveTo(x, y, x, y, width - bottomRightRadiusX, height);
        g.lineTo(bottomLeftRadiusX, height);
        x = 0;
        y = height;
        g.bezierCurveTo(x, y, x, y, 0, height - bottomLeftRadiusY);
        g.lineTo(0, topLeftRadiusY);
        x = 0;
        y = 0;
        g.bezierCurveTo(x, y, x, y, topLeftRadiusX, 0);
        g.restore();
    }

    public static void resizeCanvas(Canvas c, int width, int height)
    {
        c.setPixelSize(width, height);
        c.setCoordinateSpaceWidth(width);
        c.setCoordinateSpaceHeight(height);
    }
}
