package com.cjmalloy.gameui.client.event;

import com.google.gwt.event.shared.HandlerRegistration;


public interface HasMouseMoveHandlers
{

    HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler);
}
