package com.cjmalloy.gameui.client.event;

import com.google.gwt.event.shared.HandlerRegistration;


public interface HasMouseUpHandlers {

  HandlerRegistration addMouseUpHandler(MouseUpHandler handler);
}
