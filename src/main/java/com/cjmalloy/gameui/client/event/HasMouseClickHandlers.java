package com.cjmalloy.gameui.client.event;

import com.google.gwt.event.shared.HandlerRegistration;


public interface HasMouseClickHandlers {

  HandlerRegistration addMouseClickHandler(MouseClickHandler handler);
}
