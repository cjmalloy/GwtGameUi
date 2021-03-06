package com.cjmalloy.gameui.client.component.skin;

import com.cjmalloy.gameui.client.component.Button.ButtonState;
import com.cjmalloy.gameui.client.core.Renderer;
import com.cjmalloy.gameui.client.util.GraphicsUtil;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;

public class DefaultButtonSkin extends ButtonSkin {

  private Renderer up = new Renderer() {

    @Override
    public void render(Context2d g, double timestamp) {
      g.save();
      g.setFillStyle("#999");
      g.setStrokeStyle("#333");
      g.beginPath();
      GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 1, height - 1, 8);
      g.closePath();
      g.fill();
      g.stroke();
      renderText(g);
      g.restore();
    }
  };
  private Renderer down = new Renderer() {

    @Override
    public void render(Context2d g, double timestamp) {
      g.save();
      g.setFillStyle("#333");
      g.setStrokeStyle("#999");
      g.beginPath();
      GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 1, height - 1, 8);
      g.closePath();
      g.fill();
      g.stroke();
      renderText(g);
      g.restore();
    }
  };
  private Renderer upPressed = new Renderer() {

    @Override
    public void render(Context2d g, double timestamp) {
      g.save();
      g.setFillStyle("#99F");
      g.setStrokeStyle("#333");
      g.beginPath();
      GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 1, height - 1, 8);
      g.closePath();
      g.fill();
      g.stroke();
      renderText(g);
      g.restore();
    }
  };
  private Renderer downPressed = new Renderer() {

    @Override
    public void render(Context2d g, double timestamp) {
      g.save();
      g.setFillStyle("#33F");
      g.setStrokeStyle("#999");
      g.beginPath();
      GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 1, height - 1, 8);
      g.closePath();
      g.fill();
      g.stroke();
      renderText(g);
      g.restore();
    }
  };
  private Renderer upHovering = new Renderer() {

    @Override
    public void render(Context2d g, double timestamp) {
      g.save();
      g.setFillStyle("#F99");
      g.setStrokeStyle("#333");
      g.beginPath();
      GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 1, height - 1, 8);
      g.closePath();
      g.fill();
      g.stroke();
      renderText(g);
      g.restore();
    }
  };
  private Renderer downHovering = new Renderer() {

    @Override
    public void render(Context2d g, double timestamp) {
      g.save();
      g.setFillStyle("#F33");
      g.setStrokeStyle("#999");
      g.beginPath();
      GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 1, height - 1, 8);
      g.closePath();
      g.fill();
      g.stroke();
      renderText(g);
      g.restore();
    }
  };
  private Renderer upDisabled = new Renderer() {

    @Override
    public void render(Context2d g, double timestamp) {
      g.save();
      g.setGlobalAlpha(0.4);
      g.setFillStyle("#999");
      g.setStrokeStyle("#333");
      g.beginPath();
      GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 1, height - 1, 8);
      g.closePath();
      g.fill();
      g.stroke();
      renderText(g);
      g.restore();
    }
  };
  private Renderer downDisabled = new Renderer() {

    @Override
    public void render(Context2d g, double timestamp) {
      g.save();
      g.setGlobalAlpha(0.4);
      g.setFillStyle("#333");
      g.setStrokeStyle("#999");
      g.beginPath();
      GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 1, height - 1, 8);
      g.closePath();
      g.fill();
      g.stroke();
      renderText(g);
      g.restore();
    }
  };

  /**
   * Constructor.
   */
  public DefaultButtonSkin() {
    states.put(ButtonState.UP, up);
    states.put(ButtonState.DOWN, down);
    states.put(ButtonState.UP_PRESSED, upPressed);
    states.put(ButtonState.DOWN_PRESSED, downPressed);
    states.put(ButtonState.UP_HOVERING, upHovering);
    states.put(ButtonState.DOWN_HOVERING, downHovering);
    states.put(ButtonState.UP_DISABLED, upDisabled);
    states.put(ButtonState.DOWN_DISABLED, downDisabled);
  }

  private void renderText(Context2d g) {
    if (null != text) {
      g.setTextAlign(TextAlign.LEFT);
      g.setTextBaseline(TextBaseline.MIDDLE);
      g.setFont("bold 18px sans-serif");
      g.setFillStyle("#000");
      g.fillText(text, 10, height / 2);
    }
  }

}
