package com.cjmalloy.gameui.client.event;

import com.cjmalloy.gameui.client.event.EventBus.HandlerRegistration;

public interface HasMouseWheelHandlers
{

    HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler);
}
