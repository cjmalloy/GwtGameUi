package com.cjmalloy.gameui.client.event;

import com.cjmalloy.gameui.shared.core.Point;
import com.cjmalloy.gameui.shared.core.Rect;

public abstract class MouseEvent extends Event
{

    public int x, y;

    public boolean containsPoint()
    {
        Rect r = new Rect(0, 0, source.width, source.height);
        return r.contains(getTargetPoint());
    }

    public Point getPoint()
    {
        Point p = new Point(x, y);
        return p;
    }

    public Point getTargetPoint()
    {
        return source.globalToLocal(getPoint());
    }
}