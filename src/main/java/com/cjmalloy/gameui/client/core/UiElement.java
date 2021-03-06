package com.cjmalloy.gameui.client.core;

import com.cjmalloy.gameui.client.component.Panel;
import com.cjmalloy.gameui.client.event.EventBus;
import com.cjmalloy.gameui.client.event.EventHandler;
import com.cjmalloy.gameui.client.event.EventType;
import com.cjmalloy.gameui.client.event.HasMouseHandlers;
import com.cjmalloy.gameui.client.event.MouseClickEvent;
import com.cjmalloy.gameui.client.event.MouseClickHandler;
import com.cjmalloy.gameui.client.event.MouseDownEvent;
import com.cjmalloy.gameui.client.event.MouseDownHandler;
import com.cjmalloy.gameui.client.event.MouseMoveEvent;
import com.cjmalloy.gameui.client.event.MouseMoveHandler;
import com.cjmalloy.gameui.client.event.MouseUpEvent;
import com.cjmalloy.gameui.client.event.MouseUpHandler;
import com.cjmalloy.gameui.client.event.MouseWheelEvent;
import com.cjmalloy.gameui.client.event.MouseWheelHandler;
import com.cjmalloy.math.shared.screen.Point;
import com.cjmalloy.math.shared.screen.Rect;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * This is the base class for all widgets in this library.
 *
 * @author chris
 *
 */
public abstract class UiElement implements UiProxy, Renderer, HasMouseHandlers {

  public int x;
  public int y;
  public int width;
  public int height;
  public Panel parent;

  protected boolean redrawNeeded = true;

  private Rect rect;
  private boolean visible = true;

  public UiElement() {}

  /**
   * Constructor.
   */
  public UiElement(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public HandlerRegistration addHandler(EventHandler handler, EventType<?> type) {
    return addHandler(handler, type, false);
  }

  public HandlerRegistration addHandler(EventHandler handler, EventType<?> type,
      boolean ignoresCapture) {
    return EventBus.get().addHandler(this, handler, type, ignoresCapture);
  }

  @Override
  public HandlerRegistration addMouseClickHandler(MouseClickHandler handler) {
    return addHandler(handler, MouseClickEvent.TYPE);
  }

  @Override
  public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
    return addHandler(handler, MouseDownEvent.TYPE);
  }

  @Override
  public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
    return addHandler(handler, MouseMoveEvent.TYPE);
  }

  @Override
  public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
    return addHandler(handler, MouseUpEvent.TYPE);
  }

  @Override
  public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
    return addHandler(handler, MouseWheelEvent.TYPE);
  }

  public UiElement getElement() {
    return this;
  }

  /**
   * Get this object's rect.
   */
  public Rect getRect() {
    if (null == rect) {
      rect = new Rect();
    }

    rect.x = x;
    rect.y = y;
    rect.width = width;
    rect.height = height;

    return rect;
  }

  /**
   * Convert a point in global coordinates to coordinates relative to this
   * object's position.
   */
  public Point globalToLocal(Point p) {
    Point l = p.copy();
    UiElement t = this;
    do {
      l.x -= t.x;
      l.y -= t.y;
      t = t.parent;
    } while (null != t && t.parent != t);
    return l;
  }

  /**
   * Check if this and all parents are visible.
   */
  public boolean isAllParentsVisible() {
    UiElement t = this;
    do {
      if (!t.visible) {
        return false;
      }
      t = t.parent;
    } while (null != t && t.parent != t);
    return true;
  }

  /**
   * Check if this object's top level parent is not attached to a layer.
   */
  public boolean isDetached() {
    UiElement t = this;
    while (t != null) {
      if (t.parent == t) {
        return false;
      }
      t = t.parent;
    }
    return true;
  }

  /**
   * Override to customise criteria for disabling mouse enabled.
   */
  public boolean isMouseEnabled() {
    return isAllParentsVisible();
  }

  public boolean isVisible() {
    return visible;
  }

  /**
   * Convert a point in coordinates relative to this object's position to
   * global coordinates.
   */
  public Point localToGlobal(Point p) {
    Point l = p.copy();
    UiElement t = this;
    do {
      l.x += t.x;
      l.y += t.y;
      t = t.parent;
    } while (null != t && t.parent != t);
    return l;
  }

  /**
   * Set a new position for this object.
   * 
   * <p>You must use this function to set the position if you want to ensure
   * it is also redrawn.</p>
   */
  public void moveTo(int x, int y) {
    this.x = x;
    this.y = y;
    if (parent != null) {
      parent.redrawNeeded();
    }
  }

  public void moveTo(Point p) {
    moveTo(p.x, p.y);
  }

  /**
   * Redraw this widget if it is visible and is marked for redraw meeded.
   */
  public void redrawIfNecessary(Context2d g, double timestamp) {
    if (isVisible() && redrawNeeded) {
      clearRect(g);
      render(g, timestamp);
    }
  }

  public void redrawNeeded() {
    redrawNeeded = true;
  }

  /**
   * Resize this object.
   * 
   * <p>You must use this function to set the size if you want to ensure it is
   * also redrawn.</p>
   */
  public void resize(int w, int h) {
    this.width = w;
    this.height = h;
    redrawNeeded = true;
    if (parent != null) {
      parent.redrawNeeded();
    }
  }

  /**
   * Set this object's visibility.
   */
  public void setVisible(boolean value) {
    if (visible != value) {
      visible = value;
      if (parent != null) {
        parent.redrawNeeded();
      }
      EventBus.get().validateCapture();
    }
  }

  protected void clearRect(Context2d g) {
    g.save();
    g.translate(x, y);
    g.clearRect(0, 0, width, height);
    g.restore();
  }

}
