package com.cjmalloy.gameui.client.core;



public interface HasChildren extends UiProxy {

  void add(UiProxy proxy);

  void add(UiElement child);

  void remove(UiProxy proxy);

  void remove(UiElement child);
}
