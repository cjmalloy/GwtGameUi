package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.event.DragCancelEvent;
import com.cjmalloy.gameui.client.event.DragCancelHandler;
import com.cjmalloy.gameui.client.event.DragEndEvent;
import com.cjmalloy.gameui.client.event.DragEndHandler;
import com.cjmalloy.gameui.client.event.DragMoveEvent;
import com.cjmalloy.gameui.client.event.DragMoveHandler;
import com.cjmalloy.gameui.client.event.DragStartEvent;
import com.cjmalloy.gameui.client.event.DragStartHandler;
import com.cjmalloy.gameui.client.event.EventBus;
import com.cjmalloy.gameui.client.event.EventBus.HandlerRegistration;
import com.cjmalloy.gameui.client.event.HasDragHandlers;
import com.cjmalloy.gameui.client.event.MouseDownEvent;
import com.cjmalloy.gameui.client.event.MouseDownHandler;
import com.cjmalloy.gameui.client.event.MouseMoveEvent;
import com.cjmalloy.gameui.client.event.MouseMoveHandler;
import com.cjmalloy.gameui.client.event.MouseUpEvent;
import com.cjmalloy.gameui.client.event.MouseUpHandler;
import com.cjmalloy.mathlib.shared.linear.Point;

public abstract class DragElement extends UiElement implements MouseDownHandler, MouseMoveHandler, MouseUpHandler,
                                                               HasDragHandlers
{

    protected DragAnimation anim = null;
    protected Point startDragPoint;

    private boolean pressed;
    private boolean hovering;
    private boolean disabled;
    private boolean dragging;

    private EventBus dragBus = new EventBus();

    protected abstract void updateState();

    public DragElement()
    {
        super();

        addMouseUpHandler(this);
        addMouseDownHandler(this);
        addMouseMoveHandler(this);
    }

    @Override
    public HandlerRegistration addDragCancelHandler(DragCancelHandler handler)
    {
        return dragBus.addHandler(this, handler, DragCancelEvent.TYPE, false);
    }

    @Override
    public HandlerRegistration addDragEndHandler(DragEndHandler handler)
    {
        return dragBus.addHandler(this, handler, DragEndEvent.TYPE, false);
    }

    @Override
    public HandlerRegistration addDragMoveHandler(DragMoveHandler handler)
    {
        return dragBus.addHandler(this, handler, DragMoveEvent.TYPE, false);
    }

    @Override
    public HandlerRegistration addDragStartHandler(DragStartHandler handler)
    {
        return dragBus.addHandler(this, handler, DragStartEvent.TYPE, false);
    }

    public DragAnimation getAnimationLayer()
    {
        return anim;
    }

    public boolean isDisabled()
    {
        return disabled;
    }

    public boolean isDragging()
    {
        return dragging;
    }

    public boolean isHovering()
    {
        return hovering;
    }

    public boolean isPressed()
    {
        return pressed;
    }

    @Override
    public void onMouseDown(MouseDownEvent event)
    {
        startDragPoint = event.getPoint();
        pressed = true;
        updateState();
    }

    @Override
    public void onMouseMove(MouseMoveEvent event)
    {
        event.setCapture(this);
        if (event.containsPoint())
        {
            hovering = true;
            if (dragging)
            {
                cancelDrag(event);
            }
            event.releaseCapture(this);
        }
        else
        {
            if (pressed)
            {
                startDrag(event);
            }
            else
            {
                moveDrag(event);
            }
            hovering = false;
            pressed = false;
        }
        updateState();
    }

    @Override
    public void onMouseUp(MouseUpEvent event)
    {
        if (pressed)
        {
            pressed = false;
        }
        if (dragging)
        {
            endDrag(event);
        }

        updateState();
    }

    private void cancelDrag(MouseMoveEvent event)
    {
        DragCancelEvent e = new DragCancelEvent();
        e.x = event.x;
        e.y = event.y;
        dragBus.fireEvent(e);
    }

    private void endDrag(MouseUpEvent event)
    {
        DragEndEvent e = new DragEndEvent();
        e.x = event.x;
        e.y = event.y;
        dragBus.fireEvent(e);
    }

    private void moveDrag(MouseMoveEvent event)
    {
        DragMoveEvent e = new DragMoveEvent();
        e.x = event.x;
        e.y = event.y;
        dragBus.fireEvent(e);
    }

    private void startDrag(MouseMoveEvent event)
    {
        DragStartEvent e = new DragStartEvent();
        e.x = event.x;
        e.y = event.y;
        dragBus.fireEvent(e);
    }

    public abstract class DragAnimation extends UiElement {}

}
