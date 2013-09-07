package com.cjmalloy.gameui.client.component;

import java.util.HashMap;
import java.util.Map;

import com.cjmalloy.gameui.client.component.skin.DefaultButtonSkin;
import com.cjmalloy.gameui.client.core.IRender;
import com.cjmalloy.gameui.client.event.EventBus;
import com.cjmalloy.gameui.client.event.MouseClickEvent;
import com.cjmalloy.gameui.client.event.MouseClickHandler;
import com.cjmalloy.gameui.client.event.MouseDownEvent;
import com.cjmalloy.gameui.client.event.MouseDownHandler;
import com.cjmalloy.gameui.client.event.MouseMoveEvent;
import com.cjmalloy.gameui.client.event.MouseMoveHandler;
import com.cjmalloy.gameui.client.event.MouseUpEvent;
import com.cjmalloy.gameui.client.event.MouseUpHandler;
import com.google.gwt.canvas.dom.client.Context2d;

public class Button extends UiElement implements MouseDownHandler, MouseMoveHandler, MouseUpHandler, MouseClickHandler
{

    static ButtonSkin DEFAULT_BUTTON_SKIN = new DefaultButtonSkin();

    public boolean toggle = false;

    protected ButtonSkin skin;
    protected ButtonState state = ButtonState.UP;
    protected boolean down = false;

    private boolean pressed;
    private boolean hovering;
    private boolean disabled;

    private boolean allowClick = false;

    public Button()
    {
        super();

        setButtonSkin(DEFAULT_BUTTON_SKIN);

        addMouseClickHandler(this);
        addMouseUpHandler(this);
        addMouseDownHandler(this);
        addMouseMoveHandler(this);
    }

    @Override
    public void onMouseClick(MouseClickEvent event)
    {
        if (!allowClick)
        {
            event.stopPropogation();
            return;
        }
        if (toggle)
        {
            down = !down;
        }
        updateState();
    }

    @Override
    public void onMouseDown(MouseDownEvent event)
    {
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
        }
        else
        {
            hovering = false;
            pressed = false;
            event.releaseCapture(this);
        }
        updateState();
    }

    @Override
    public void onMouseUp(MouseUpEvent event)
    {
        if (pressed)
        {
            pressed = false;
            createClick(event.x, event.y);
        }

        updateState();
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
        skin.getFace(state).render(g, timestamp);
        g.restore();
    }

    public void setButtonSkin(ButtonSkin skin)
    {
        this.skin = skin;
        this.width = skin.width;
        this.height = skin.height;
        redrawNeeded = true;
    }

    public void setDown(boolean value)
    {
        if (toggle && down != value)
        {
            down = value;
            updateState();
        }
    }

    public void setText(String text)
    {
        skin.setText(text);
    }

    protected void updateState()
    {
        ButtonState s;
        if (down)
        {
            if (disabled)
            {
                s = ButtonState.DOWN_DISABLED;
            }
            else if (pressed)
            {
                s = ButtonState.DOWN_PRESSED;
            }
            else if (hovering)
            {
                s = ButtonState.DOWN_HOVERING;
            }
            else
            {
                s = ButtonState.DOWN;
            }
        }
        else
        {
            if (disabled)
            {
                s = ButtonState.UP_DISABLED;
            }
            else if (pressed)
            {
                s = ButtonState.UP_PRESSED;
            }
            else if (hovering)
            {
                s = ButtonState.UP_HOVERING;
            }
            else
            {
                s = ButtonState.UP;
            }
        }

        if (state != s)
        {
            state = s;
            redrawNeeded = true;
        }
    }

    private void createClick(int x, int y)
    {
        allowClick = true;
        MouseClickEvent click = new MouseClickEvent();
        click.x = x;
        click.y = y;
        EventBus.get().fireEvent(click);
        allowClick = false;
    }

    public static abstract class ButtonSkin
    {

        public Map<ButtonState, IRender> states = new HashMap<ButtonState, IRender>();
        public int width = 50, height = 50;

        public IRender getFace(ButtonState state)
        {
            if (states.containsKey(state)) { return states.get(state); }

            switch (state)
            {
            case UP:
                return null;
            case DOWN:
                return getFace(ButtonState.UP);
            case UP_HOVERING:
                return getFace(ButtonState.UP);
            case DOWN_HOVERING:
                return getFace(ButtonState.DOWN);
            case UP_PRESSED:
                return getFace(ButtonState.UP_HOVERING);
            case DOWN_PRESSED:
                return getFace(ButtonState.DOWN_HOVERING);
            case UP_DISABLED:
                return getFace(ButtonState.UP);
            case DOWN_DISABLED:
                return getFace(ButtonState.DOWN);
            default:
                throw new Error("Invalid button state");
            }
        }

        public abstract void setText(String text);
    }

    public enum ButtonState
    {
        UP, UP_PRESSED, UP_HOVERING, UP_DISABLED, DOWN, DOWN_PRESSED, DOWN_HOVERING, DOWN_DISABLED,
    }
}
