package com.cjmalloy.gameui.client.event;

import com.cjmalloy.math.shared.screen.Point;
import com.cjmalloy.math.shared.screen.Rect;

public abstract class MouseEvent extends Event {

  public int x;
  public int y;

  public boolean containsPoint() {
    Rect r = new Rect(0, 0, source.width, source.height);
    return r.contains(getTargetPoint());
  }

  public Point getPoint() {
    Point p = new Point(x, y);
    return p;
  }

  public Point getTargetPoint() {
    return source.globalToLocal(getPoint());
  }
}
