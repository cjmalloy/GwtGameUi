package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.component.Button.ButtonState;
import com.cjmalloy.gameui.client.component.skin.ButtonSkin;
import com.google.gwt.canvas.dom.client.Context2d;

public class DragButton extends DragElement
{

    protected ButtonSkin skin;
    protected ButtonState state = ButtonState.UP;

    public DragButton()
    {
        super();

        anim = new DragButtonAnimation();
        setButtonSkin(Button.DEFAULT_BUTTON_SKIN);
    }

    @Override
    public void render(Context2d g, double timestamp)
    {
        if (!isVisible()) { return; }
        redrawNeeded = false;
        g.save();
        g.translate(x, y);
        skin.width = width;
        skin.height = height;
        skin.getRenderer(state).render(g, timestamp);
        g.restore();
    }

    public void setButtonSkin(ButtonSkin skin)
    {
        this.skin = skin;
        this.width = skin.width;
        this.height = skin.height;
        redrawNeeded = true;
        if (anim != null)
        {
            anim.width = skin.width;
            anim.height = skin.height;
            anim.redrawNeeded = true;
        }
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

        anim.setVisible(isDragging());
    }

    public class DragButtonAnimation extends DragAnimation
    {

        public DragButtonAnimation()
        {
            setVisible(false);
        }

        @Override
        public void render(Context2d g, double timestamp)
        {
            if (!isVisible()) { return; }
            redrawNeeded = false;
            g.save();
            g.translate(x, y);
            skin.width = width;
            skin.height = height;
            skin.getRenderer(state).render(g, timestamp);
            g.restore();
        }

    }
}
