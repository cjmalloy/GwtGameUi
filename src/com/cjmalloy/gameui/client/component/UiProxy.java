package com.cjmalloy.gameui.client.component;

/**
 * A proxy interface to a UiElement.
 *
 * The class implementing this interface should always return the same
 * UiElement for every call of getElement(0). Failure to do so will result
 * in undefined behaviour.
 *
 * @author chris
 *
 */
public interface UiProxy
{
    UiElement getElement();
}
