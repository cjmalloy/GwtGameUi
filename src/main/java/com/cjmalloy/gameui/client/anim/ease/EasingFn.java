package com.cjmalloy.gameui.client.anim.ease;

public class EasingFn {

  public static final Easing easeInQuad = new Easing() {

    @Override
    public double ease(double p) {
      return p * p;
    }
  };
  public static final Easing easeOutQuad = new Easing() {

    @Override
    public double ease(double p) {
      return -p * (p - 2);
    }
  };
  public static final Easing easeInOutQuad = new Easing() {

    @Override
    public double ease(double p) {
      if ((p *= 2) < 1) {
        return 0.5 * p * p;
      }
      return -0.5 * ((--p) * (p - 2) - 1);
    }
  };
  public static final Easing easeInCubic = new Easing() {

    @Override
    public double ease(double p) {
      return p * p * p;
    }
  };
  public static final Easing easeOutCubic = new Easing() {

    @Override
    public double ease(double p) {
      return ((--p) * p * p + 1);
    }
  };
  public static final Easing easeInOutCubic = new Easing() {

    @Override
    public double ease(double p) {
      if ((p *= 2) < 1) {
        return 0.5 * p * p * p;
      }
      return 0.5 * ((p -= 2) * p * p + 2);
    }
  };
  public static final Easing easeInQuart = new Easing() {

    @Override
    public double ease(double p) {
      return p * p * p * p;
    }
  };
  public static final Easing easeOutQuart = new Easing() {

    @Override
    public double ease(double p) {
      return -((--p) * p * p * p - 1);
    }
  };
  public static final Easing easeInOutQuart = new Easing() {

    @Override
    public double ease(double p) {
      if ((p *= 2) < 1) {
        return 0.5 * p * p * p * p;
      }
      return -0.5 * ((p -= 2) * p * p * p - 2);
    }
  };
  public static final Easing easeInQuint = new Easing() {

    @Override
    public double ease(double p) {
      return p * p * p * p * p;
    }
  };
  public static final Easing easeOutQuint = new Easing() {

    @Override
    public double ease(double p) {
      return ((--p) * p * p * p * p + 1);
    }
  };
  public static final Easing easeInOutQuint = new Easing() {

    @Override
    public double ease(double p) {
      if ((p *= 2) < 1) {
        return 0.5 * p * p * p * p * p;
      }
      return 0.5 * ((p -= 2) * p * p * p * p + 2);
    }
  };
  public static final Easing easeInSine = new Easing() {

    @Override
    public double ease(double p) {
      return -Math.cos(p * (Math.PI / 2)) + 1;
    }
  };
  public static final Easing easeOutSine = new Easing() {

    @Override
    public double ease(double p) {
      return Math.sin(p * (Math.PI / 2));
    }
  };
  public static final Easing easeInOutSine = new Easing() {

    @Override
    public double ease(double p) {
      return -0.5 * (Math.cos(Math.PI * p) - 1);
    }
  };
  public static final Easing easeInExpo = new Easing() {

    @Override
    public double ease(double p) {
      return (p == 0) ? 0 : Math.pow(2, 10 * (p - 1));
    }
  };
  public static final Easing easeOutExpo = new Easing() {

    @Override
    public double ease(double p) {
      return (p == 1) ? 1 : (-Math.pow(2, -10 * p) + 1);
    }
  };
  public static final Easing easeInOutExpo = new Easing() {

    @Override
    public double ease(double p) {
      if (p == 0) {
        return 0;
      }
      if (p == 1) {
        return 1;
      }
      if ((p *= 2) < 1) {
        return 0.5 * Math.pow(2, 10 * (p - 1));
      }
      return 0.5 * (-Math.pow(2, -10 * --p) + 2);
    }
  };
  public static final Easing easeInCirc = new Easing() {

    @Override
    public double ease(double p) {
      return -(Math.sqrt(1 - p * p) - 1);
    }
  };
  public static final Easing easeOutCirc = new Easing() {

    @Override
    public double ease(double p) {
      return Math.sqrt(1 - (--p) * p);
    }
  };
  public static final Easing easeInOutCirc = new Easing() {

    @Override
    public double ease(double p) {
      if ((p *= 2) < 1) {
        return -0.5 * (Math.sqrt(1 - p * p) - 1);
      }
      return 0.5 * (Math.sqrt(1 - (p -= 2) * p) + 1);
    }
  };
  public static final Easing easeInBack = new Easing() {

    @Override
    public double ease(double p) {
      double s = 1.70158;
      return p * p * ((s + 1) * p - s);
    }
  };
  public static final Easing easeOutBack = new Easing() {

    @Override
    public double ease(double p) {
      double s = 1.70158;
      return ((--p) * p * ((s + 1) * p + s) + 1);
    }
  };
  public static final Easing easeInOutBack = new Easing() {

    @Override
    public double ease(double p) {
      double s = 1.70158;
      if ((p *= 2) < 1) {
        return 0.5 * (p * p * (((s *= (1.525)) + 1) * p - s));
      }
      return 0.5 * ((p -= 2) * p * (((s *= (1.525)) + 1) * p + s) + 2);
    }
  };
  public static final Easing easeInBounce = new Easing() {

    @Override
    public double ease(double p) {
      return 1 - easeOutBounce.ease(1 - p);
    }
  };
  public static final Easing easeOutBounce = new Easing() {

    @Override
    public double ease(double p) {
      if (p < (1 / 2.75)) {
        return (7.5625 * p * p);
      } else if (p < (2 / 2.75)) {
        return (7.5625 * (p -= (1.5 / 2.75)) * p + .75);
      } else if (p < (2.5 / 2.75)) {
        return (7.5625 * (p -= (2.25 / 2.75)) * p + .9375);
      } else {
        return (7.5625 * (p -= (2.625 / 2.75)) * p + .984375);
      }
    }
  };
  public static final Easing easeInOutBounce = new Easing() {

    @Override
    public double ease(double p) {
      if (p < 0.5) {
        return easeInBounce.ease(2 * p) / 2;
      }
      return easeOutBounce.ease(p * 2 - 1) / 2 + .5;
    }
  };
}
