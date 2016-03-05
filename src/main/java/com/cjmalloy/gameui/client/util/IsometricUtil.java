package com.cjmalloy.gameui.client.util;

import com.google.gwt.canvas.dom.client.Context2d;


public class IsometricUtil {
  public static double SQRT0_5 = Math.sqrt(0.5);
  public static double SQRT1_5 = Math.sqrt(1.5);
  public static double SQRT2 = Math.sqrt(2);
  public static double SQRT3 = Math.sqrt(3);

  /**
   * Draw a cone. Does not call beginPath().
   */
  public static void drawCone(Context2d g, double x, double y, double radius, double height) {
    double kappa = .5522848;
    double w = (2 * radius) * SQRT1_5;
    double h = (2 * radius) * SQRT0_5;
    double xm = w / 2f; // x-middle
    double ym = h / 2f; // y-middle
    double ox = xm * kappa; // control point offset horizontal
    double oy = ym * kappa; // control point offset vertical

    g.translate(x, y);
    g.moveTo(w, ym);
    g.bezierCurveTo(w, ym + oy, xm + ox, h, xm, h);
    g.bezierCurveTo(xm - ox, h, 0, ym + oy, 0, ym);
    g.lineTo(xm, ym - height);
    g.closePath();
    g.translate(-x, -y);
  }
}
