package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.component.TextRenderer.TextClip;
import com.cjmalloy.gameui.client.core.Buffer;
import com.cjmalloy.gameui.client.core.UiElement;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;


public class Label extends UiElement {
  private String backgroundColor = null;

  private TextRenderer renderer = new TextRenderer();
  private boolean redrawBufferNeeded = true;
  private Buffer buffer = new Buffer(renderer, width, height);

  public Label() {
    renderer.textClip = TextClip.FIT;
  }

  public Label(String text) {
    this();
    setText(text);
  }

  public Label(String text, String font) {
    this(text);
    setFont(font);
  }

  public String getFont() {
    return renderer.font;
  }

  public String getText() {
    return renderer.text;
  }

  public TextAlign getTextAlign() {
    return renderer.textAlign;
  }

  public TextBaseline getTextBaseline() {
    return renderer.textBaseline;
  }

  public TextClip getTextClip() {
    return renderer.textClip;
  }

  public String getTextColor() {
    return renderer.color;
  }

  @Override
  public void render(Context2d g, double timestamp) {
    if (!isVisible()) {
      return;
    }
    redrawNeeded = false;
    g.save();
    g.translate(x, y);

    if (redrawBufferNeeded) {
      buffer.render();
      redrawBufferNeeded = false;
    }
    if (null != backgroundColor) {
      g.beginPath();
      g.rect(0, 0, width, height);
      g.setFillStyle(backgroundColor);
      g.fill();
    }
    buffer.flip(g);
    g.restore();
  }

  @Override
  public void resize(int w, int h) {
    super.resize(w, h);
    renderer.width = w;
    renderer.height = h;
    buffer.resize(w, h);
  }

  /**
   * Set to <code>null</code> for transparent.
   */
  public void setBackgroundColor(String backgroundColor) {
    if (this.backgroundColor == backgroundColor) {
      return;
    }
    this.backgroundColor = backgroundColor;
    redrawNeeded = true;
  }

  /**
   * Label font.
   */
  public void setFont(String font) {
    if (renderer.font == font) {
      return;
    }
    renderer.font = font;
    redrawBufferNeeded = true;
    redrawNeeded = true;
  }

  /**
   * Label text.
   */
  public void setText(String text) {
    if (renderer.text == text) {
      return;
    }
    renderer.text = text;
    redrawBufferNeeded = true;
    redrawNeeded = true;
  }

  /**
   * Text align.
   */
  public void setTextAlign(TextAlign textAlign) {
    if (renderer.textAlign == textAlign) {
      return;
    }
    renderer.textAlign = textAlign;
    redrawBufferNeeded = true;
    redrawNeeded = true;
  }

  /**
   * Text baseline.
   */
  public void setTextBaseline(TextBaseline textBaseline) {
    if (renderer.textBaseline == textBaseline) {
      return;
    }
    renderer.textBaseline = textBaseline;
    redrawBufferNeeded = true;
    redrawNeeded = true;
  }

  /**
   * Text clip.
   */
  public void setTextClip(TextClip textClip) {
    if (renderer.textClip == textClip) {
      return;
    }
    renderer.textClip = textClip;
    redrawBufferNeeded = true;
    redrawNeeded = true;
  }

  /**
   * Text color.
   */
  public void setTextColor(String textColor) {
    if (renderer.color == textColor) {
      return;
    }
    renderer.color = textColor;
    redrawBufferNeeded = true;
    redrawNeeded = true;
  }

}
