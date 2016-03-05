package com.cjmalloy.gameui.client.core;


/**
 * A proxy interface to a UiElement.
 *
 * <p>The class implementing this interface should always return the same
 * UiElement for every call of getElement(). Failure to do so will result in
 * undefined behavior.</p>
 */
public interface UiProxy {
  UiElement getElement();
}
