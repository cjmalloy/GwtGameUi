package com.cjmalloy.gameui.client.component.skin;

import java.util.HashMap;
import java.util.Map;

import com.cjmalloy.gameui.client.component.DragButton.DragButtonState;
import com.cjmalloy.gameui.client.core.HasRenderer;
import com.cjmalloy.gameui.client.core.Renderer;


public abstract class DragButtonSkin implements HasRenderer<DragButtonState>
{

    public Map<DragButtonState, Renderer> states = new HashMap<DragButtonState, Renderer>();
    public int width = 50, height = 50;

    protected String text;

    public Renderer getRenderer(DragButtonState state)
    {
        if (states.containsKey(state)) { return states.get(state); }

        switch (state)
        {
        case UP:
            return null;
        case DOWN:
            return getRenderer(DragButtonState.UP);
        case UP_HOVERING:
            return getRenderer(DragButtonState.UP);
        case DOWN_HOVERING:
            return getRenderer(DragButtonState.DOWN);
        case UP_PRESSED:
            return getRenderer(DragButtonState.UP_HOVERING);
        case DOWN_PRESSED:
            return getRenderer(DragButtonState.DOWN_HOVERING);
        case UP_DISABLED:
            return getRenderer(DragButtonState.UP);
        case DOWN_DISABLED:
            return getRenderer(DragButtonState.DOWN);
        case DRAGGING:
            return getRenderer(DragButtonState.DOWN);
        default:
            throw new Error("Invalid button state");
        }
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
