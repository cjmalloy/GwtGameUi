package com.cjmalloy.gameui.client.component.skin;

import java.util.HashMap;
import java.util.Map;

import com.cjmalloy.gameui.client.component.Button.ButtonState;
import com.cjmalloy.gameui.client.core.HasRenderer;
import com.cjmalloy.gameui.client.core.Renderer;


public abstract class ButtonSkin implements HasRenderer<ButtonState> {

  public Map<ButtonState, Renderer> states = new HashMap<ButtonState, Renderer>();
  public int width = 50;
  public int height = 50;

  protected String text;

  /**
   * Get the renderer for the given button state.
   */
  public Renderer getRenderer(ButtonState state) {
    if (states.containsKey(state)) {
      return states.get(state);
    }

    switch (state) {
      case UP:
        return null;
      case DOWN:
        return getRenderer(ButtonState.UP);
      case UP_HOVERING:
        return getRenderer(ButtonState.UP);
      case DOWN_HOVERING:
        return getRenderer(ButtonState.DOWN);
      case UP_PRESSED:
        return getRenderer(ButtonState.UP_HOVERING);
      case DOWN_PRESSED:
        return getRenderer(ButtonState.DOWN_HOVERING);
      case UP_DISABLED:
        return getRenderer(ButtonState.UP);
      case DOWN_DISABLED:
        return getRenderer(ButtonState.DOWN);
      default:
        throw new Error("Invalid button state");
    }
  }

  public void setText(String text) {
    this.text = text;
  }
}
