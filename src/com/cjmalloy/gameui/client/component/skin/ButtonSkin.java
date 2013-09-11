package com.cjmalloy.gameui.client.component.skin;

import java.util.HashMap;
import java.util.Map;

import com.cjmalloy.gameui.client.component.Button.ButtonState;
import com.cjmalloy.gameui.client.core.IRender;


public abstract class ButtonSkin
{

    public Map<ButtonState, IRender> states = new HashMap<ButtonState, IRender>();
    public int width = 50, height = 50;

    protected String text;

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

    public void setText(String text)
    {
        this.text = text;
    }
}
