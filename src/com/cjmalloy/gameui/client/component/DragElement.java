package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.core.Point;
import com.cjmalloy.gameui.client.event.DragCancelEvent;
import com.cjmalloy.gameui.client.event.DragEndEvent;
import com.cjmalloy.gameui.client.event.DragMoveEvent;
import com.cjmalloy.gameui.client.event.DragStartEvent;
import com.cjmalloy.gameui.client.event.Event;
import com.cjmalloy.gameui.client.event.EventBus;
import com.cjmalloy.gameui.client.event.MouseDownEvent;
import com.cjmalloy.gameui.client.event.MouseDownHandler;
import com.cjmalloy.gameui.client.event.MouseEvent;
import com.cjmalloy.gameui.client.event.MouseMoveEvent;
import com.cjmalloy.gameui.client.event.MouseMoveHandler;
import com.cjmalloy.gameui.client.event.MouseUpEvent;
import com.cjmalloy.gameui.client.event.MouseUpHandler;

public abstract class DragElement extends UiElement implements MouseDownHandler, MouseMoveHandler, MouseUpHandler
{
    protected UiElement anim = null;
    protected Point startDragPoint;

    private boolean pressed;
    private boolean hovering;
    private boolean disabled;
    private boolean dragging;

    private boolean draggable = true;

    public DragElement()
    {
        super();

        addMouseUpHandler(this);
        addMouseDownHandler(this);
        addMouseMoveHandler(this);
    }

    public UiElement getAnimationLayer()
    {
        return anim;
    }

    public boolean isDisabled()
    {
        return disabled;
    }

    public boolean isDraggable()
    {
        return draggable;
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
                event.releaseCapture(this);
            }
        }
        else
        {
            if (dragging)
            {
                if (draggable)
                {
                    moveDrag(event);
                }
                else
                {
                    cancelDrag(event);
                    event.releaseCapture(this);
                }
            }
            else if (pressed && draggable)
            {
                startDrag(event);
            }
            else
            {
                event.releaseCapture(this);
            }
            hovering = false;
            pressed = false;
        }
        updateState();
    }

    @Override
    public void onMouseUp(MouseUpEvent event)
    {
        event.releaseCapture(this);
        pressed = false;
        if (dragging)
        {
            endDrag(event);
        }

        updateState();
    }

    public void setDraggable(boolean value)
    {
        draggable = value;
    }

    protected Event getDragCancelEvent(MouseEvent event)
    {
        DragCancelEvent e = new DragCancelEvent();
        e.x = event.x;
        e.y = event.y;
        e.dragSource = this;
        return e;
    }

    protected Event getDragEndEvent(MouseEvent event)
    {
        DragEndEvent e = new DragEndEvent();
        e.x = event.x;
        e.y = event.y;
        e.dragSource = this;
        return e;
    }

    protected Event getDragMoveEvent(MouseEvent event)
    {
        DragMoveEvent e = new DragMoveEvent();
        e.x = event.x;
        e.y = event.y;
        e.dragSource = this;
        return e;
    }

    protected Event getDragStartEvent(MouseEvent event)
    {
        DragStartEvent e = new DragStartEvent();
        e.x = event.x;
        e.y = event.y;
        e.dragSource = this;
        return e;
    }

    protected void updateAnimation(MouseMoveEvent event)
    {
        Point delta = event.getPoint().subtract(startDragPoint);
        anim.moveTo(localToGlobal(delta));
    }

    protected abstract void updateState();

    private void cancelDrag(MouseMoveEvent event)
    {
        dragging = false;
        EventBus.get().fireEvent(getDragCancelEvent(event));
    }

    private void endDrag(MouseUpEvent event)
    {
        dragging = false;
        EventBus.get().fireEvent(getDragEndEvent(event));
    }

    private void moveDrag(MouseMoveEvent event)
    {
        updateAnimation(event);
        EventBus.get().fireEvent(getDragMoveEvent(event));
    }

    private void startDrag(MouseMoveEvent event)
    {
        dragging = true;
        EventBus.get().fireEvent(getDragStartEvent(event));
    }

    public abstract class DragAnimation extends UiElement {}

}
