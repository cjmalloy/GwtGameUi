package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.component.skin.ButtonSkin;
import com.cjmalloy.gameui.client.component.skin.DefaultButtonSkin;
import com.cjmalloy.gameui.client.core.CacheMap;
import com.cjmalloy.gameui.client.core.UiElement;
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
    public enum ButtonState
    {
        UP,
        UP_PRESSED,
        UP_HOVERING,
        UP_DISABLED,
        DOWN,
        DOWN_PRESSED,
        DOWN_HOVERING,
        DOWN_DISABLED,
    }

    public static ButtonSkin DEFAULT_BUTTON_SKIN = new DefaultButtonSkin();

    public boolean toggle = false;
    public boolean cache = false;

    protected ButtonSkin skin;
    protected ButtonState state = ButtonState.UP;
    protected boolean down = false;

    private boolean pressed;
    private boolean hovering;
    private boolean disabled;

    private boolean allowClick = false;
    private CacheMap<ButtonState> cacheMap = null;

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
        if (cache)
        {
            if (cacheMap == null)
            {
                cacheMap = new CacheMap<ButtonState>(skin, width, height);
            }
            cacheMap.render(g, state);
        }
        else
        {
            skin.getRenderer(state).render(g, timestamp);
        }
        g.restore();
    }

    @Override
    public void resize(int w, int h)
    {
        super.resize(w, h);
        skin.width = width;
        skin.height = height;
        if (cacheMap != null)
        {
            cacheMap.resize(w, h);
        }
    }

    public void setButtonSkin(ButtonSkin skin)
    {
        this.skin = skin;
        this.width = skin.width;
        this.height = skin.height;
        redrawNeeded = true;
        cacheMap = null;
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
        if (cacheMap != null)
        {
            cacheMap.clear();
        }
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
}
