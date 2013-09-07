package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.event.EventBus;
import com.cjmalloy.gameui.client.event.EventBus.HandlerRegistration;
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

// TODO: check if this needs caching for the content area
public class ScrollPanel extends Panel implements MouseUpHandler, MouseDownHandler, MouseMoveHandler, MouseWheelHandler
{

    public Point scrollOffset = new Point();
    protected Panel content = new Panel(0, 0, 0, 0);

    protected Point startDrag;
    protected Point startOffset;

    protected Rect contentRect;

    public ScrollPanel(int x, int y, int width, int height)
    {
        super(x, y, width, height);

        content.parent = this;

        addMouseUpHandler(this);
        addMouseDownHandler(this);
        addMouseMoveHandler(this);
        addMouseWheelHandler(this);
    }

    public void add(UiElement child)
    {
        if (children.indexOf(child) == -1)
        {
            if (null != child.parent)
            {
                child.parent.remove(child);
            }
            children.add(child);
            child.parent = content;
            redrawNeeded = true;
        }
    }

    @Override
    public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler)
    {
        return EventBus.get().addHandler(this, handler, MouseWheelEvent.TYPE, true);
    }

    public Rect getContentRect()
    {
        if (null == contentRect)
        {
            contentRect = new Rect();
        }

        contentRect.set(0, 0, width, height);
        for (UiElement c : children)
        {
            contentRect.boundingRect(c.getRect());
        }
        content.width = contentRect.width;
        content.height = contentRect.height;
        return contentRect;
    }

    @Override
    public void onMouseDown(MouseDownEvent event)
    {
        event.setCapture(this);
        startDrag = event.getPoint();
        startOffset = scrollOffset.copy();
    }

    @Override
    public void onMouseMove(MouseMoveEvent event)
    {
        if (null != startDrag)
        {
            setScrollOffset(event.getPoint().subtract(startDrag).add(startOffset));
        }
    }

    @Override
    public void onMouseUp(MouseUpEvent event)
    {
        event.releaseCapture(this);

        startDrag = null;
    }

    @Override
    public void onMouseWheel(MouseWheelEvent event)
    {
        setScrollOffset(scrollOffset.add(0, -event.deltaY * Math.abs(event.deltaY)));
    }

    @Override
    public void redrawIfNecessary(Context2d g, double timestamp)
    {
        if (!isVisible()) { return; }
        if (redrawNeeded)
        {
            clearRect(g);
            render(g, timestamp);
        }
        else
        {
            g.save();
            g.beginPath();
            g.rect(x, y, width, height);
            g.clip();
            g.translate(x + scrollOffset.x, y + scrollOffset.y);
            for (UiElement c : children)
            {
                c.redrawIfNecessary(g, timestamp);
            }
            g.restore();
        }
    }

    public void remove(UiElement child)
    {
        if (children.indexOf(child) != -1)
        {
            children.remove(child);
            child.parent = null;
            redrawNeeded = true;
        }
    }

    @Override
    public void render(Context2d g, double timestamp)
    {
        if (!isVisible()) { return; }

        Rect r = getContentRect();
        content.x = scrollOffset.x = Math.min(0, Math.max(width - r.width, scrollOffset.x));
        content.y = scrollOffset.y = Math.min(0, Math.max(height - r.height, scrollOffset.y));

        g.save();
        g.beginPath();
        g.translate(x, y);
        g.rect(0, 0, width, height);
        g.clip();
        g.translate(scrollOffset.x, scrollOffset.y);
        for (UiElement c : children)
        {
            c.render(g, timestamp);
        }
        g.restore();

        redrawNeeded = false;
    }

    public void setScrollOffset(Point off)
    {
        Rect r = getContentRect();
        content.x = scrollOffset.x = Math.min(0, Math.max(width - r.width, off.x));
        content.y = scrollOffset.y = Math.min(0, Math.max(height - r.height, off.y));
        redrawNeeded = true;
    }
}
