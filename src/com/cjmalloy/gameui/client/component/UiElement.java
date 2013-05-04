package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.core.IRender;
import com.cjmalloy.gameui.client.event.EventBus;
import com.cjmalloy.gameui.client.event.EventBus.HandlerRegistration;
import com.cjmalloy.gameui.client.event.HasMouseHandlers;
import com.cjmalloy.gameui.client.event.MouseClickEvent;
import com.cjmalloy.gameui.client.event.MouseClickHandler;
import com.cjmalloy.gameui.client.event.MouseDownEvent;
import com.cjmalloy.gameui.client.event.MouseDownHandler;
import com.cjmalloy.gameui.client.event.MouseMoveEvent;
import com.cjmalloy.gameui.client.event.MouseMoveHandler;
import com.cjmalloy.gameui.client.event.MouseUpEvent;
import com.cjmalloy.gameui.client.event.MouseUpHandler;
import com.cjmalloy.gameui.client.event.MouseWheelEvent;
import com.cjmalloy.gameui.client.event.MouseWheelHandler;
import com.cjmalloy.mathlib.shared.linear.Point;
import com.cjmalloy.mathlib.shared.linear.Rect;
import com.google.gwt.canvas.dom.client.Context2d;

public abstract class UiElement implements IRender, HasMouseHandlers
{

    public int x;
    public int y;
    public int width;
    public int height;
    public Panel parent;

    public boolean visible = true;

    protected boolean redrawNeeded = true;

    private Rect rect;

    @Override
    public HandlerRegistration addMouseClickHandler(MouseClickHandler handler)
    {
        return EventBus.get().addHandler(this, handler, MouseClickEvent.TYPE, false);
    }

    @Override
    public HandlerRegistration addMouseDownHandler(MouseDownHandler handler)
    {
        return EventBus.get().addHandler(this, handler, MouseDownEvent.TYPE, false);
    }

    @Override
    public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler)
    {
        return EventBus.get().addHandler(this, handler, MouseMoveEvent.TYPE, false);
    }

    @Override
    public HandlerRegistration addMouseUpHandler(MouseUpHandler handler)
    {
        return EventBus.get().addHandler(this, handler, MouseUpEvent.TYPE, false);
    }

    @Override
    public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler)
    {
        return EventBus.get().addHandler(this, handler, MouseWheelEvent.TYPE, false);
    }

    public Rect getRect()
    {
        if (null == rect)
        {
            rect = new Rect();
        }

        rect.x = x;
        rect.y = y;
        rect.width = width;
        rect.height = height;

        return rect;
    }

    public Point globalToLocal(Point p)
    {
        Point l = p.copy();
        UiElement t = this;
        while (null != t)
        {
            l.x -= t.x;
            l.y -= t.y;
            t = t.parent;
        }
        return l;
    }

    public Point localToGlobal(Point p)
    {
        Point l = p.copy();
        UiElement t = this;
        while (null != t)
        {
            l.x += t.x;
            l.y += t.y;
            t = t.parent;
        }
        return l;
    }

    public void redrawIfNecessary(Context2d g, double timestamp)
    {
        if (visible && redrawNeeded)
        {
            render(g, timestamp);
        }
    }

    public void redrawNeeded()
    {
        redrawNeeded = true;
    }

}
