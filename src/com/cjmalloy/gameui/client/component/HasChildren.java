package com.cjmalloy.gameui.client.component;


public interface HasChildren extends UiProxy
{

    void add(UiProxy proxy);
    void add(UiElement child);
    void remove(UiProxy proxy);
    void remove(UiElement child);
}
