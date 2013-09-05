package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.component.Button.ButtonSkin;
import com.cjmalloy.gameui.client.component.Button.ButtonState;
import com.cjmalloy.gameui.client.event.DragMoveEvent;
import com.cjmalloy.gameui.client.event.DragMoveHandler;
import com.cjmalloy.mathlib.shared.linear.Point;
import com.google.gwt.canvas.dom.client.Context2d;

public class DragButton extends DragElement implements DragMoveHandler
{

    protected ButtonSkin skin;
    protected ButtonState state = ButtonState.UP;

    public DragButton()
    {
        super();

        anim = new DragButtonAnimation();
        addDragMoveHandler(this);
    }

    @Override
    public void onDragMove(DragMoveEvent event)
    {
        Point delta = event.getPoint().subtract(startDragPoint);
        anim.moveTo(delta.add(x, y));
        anim.redrawNeeded = true;
    }

    @Override
    public void render(Context2d g, double timestamp)
    {
        if (!visible) { return; }
        redrawNeeded = false;
        g.save();
        g.translate(x, y);
        g.clearRect(0, 0, width, height);
        skin.width = width;
        skin.height = height;
        skin.getFace(state).render(g, timestamp);
        g.restore();
    }

    public void setButtonSin(ButtonSkin skin)
    {
        this.skin = skin;
        this.width = skin.width;
        this.height = skin.height;
        redrawNeeded = true;
    }

    public void setText(String text)
    {
        skin.setText(text);
    }

    protected void updateState()
    {
        ButtonState s;
        if (isDisabled())
        {
            s = ButtonState.UP_DISABLED;
        }
        else if (isPressed())
        {
            s = ButtonState.UP_PRESSED;
        }
        else if (isHovering())
        {
            s = ButtonState.UP_HOVERING;
        }
        else
        {
            s = ButtonState.UP;
        }

        if (state != s)
        {
            state = s;
            redrawNeeded = true;
        }

        anim.visible = isDragging();
    }

    public class DragButtonAnimation extends DragAnimation
    {

        @Override
        public void render(Context2d g, double timestamp)
        {
            if (!visible) { return; }
            redrawNeeded = false;
            g.save();
            g.translate(x, y);
            g.clearRect(0, 0, width, height);
            skin.width = width;
            skin.height = height;
            skin.getFace(state).render(g, timestamp);
            g.restore();
        }

    }
}
