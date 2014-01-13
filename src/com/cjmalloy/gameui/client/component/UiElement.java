package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.core.IRender;
import com.cjmalloy.gameui.client.event.EventBus;
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
import com.cjmalloy.gameui.shared.core.Point;
import com.cjmalloy.gameui.shared.core.Rect;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * This is the base class for all widgets in this library.
 *
 * @author chris
 *
 */
public abstract class UiElement implements UiProxy, IRender, HasMouseHandlers
{

    public int x;
    public int y;
    public int width;
    public int height;
    public Panel parent;

    protected boolean redrawNeeded = true;

    private Rect rect;
    private boolean visible = true;

    public UiElement() {}

    public UiElement(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

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

    public UiElement getElement()
    {
        return this;
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

    public boolean isAllParentsVisible()
    {
        UiElement t = this;
        while (null != t)
        {
            if (!t.visible) return false;
            t = t.parent;
        }
        return true;
    }

    /**
     * Override to customise criteria for disabling mouse enabled.
     */
    public boolean isMouseEnabled()
    {
        return isAllParentsVisible();
    }

    public boolean isVisible()
    {
        return visible;
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

    public void moveTo(int x, int y)
    {
        this.x = x;
        this.y = y;
        if (parent != null)
        {
            parent.redrawNeeded();
        }
    }

    public void moveTo(Point p)
    {
        moveTo(p.x, p.y);
    }

    public void redrawIfNecessary(Context2d g, double timestamp)
    {
        if (isVisible() && redrawNeeded)
        {
            clearRect(g);
            render(g, timestamp);
        }
    }

    public void redrawNeeded()
    {
        redrawNeeded = true;
    }

    public void resize(int w, int h)
    {
        this.width = w;
        this.height = h;
        redrawNeeded = true;
        if (parent != null)
        {
            parent.redrawNeeded();
        }
    }

    public void setVisible(boolean value)
    {
        if (visible != value)
        {
            visible = value;
            if (parent != null)
            {
                parent.redrawNeeded();
            }
        }
    }

    protected void clearRect(Context2d g)
    {
        g.save();
        g.translate(x, y);
        g.clearRect(0, 0, width, height);
        g.restore();
    }

}
