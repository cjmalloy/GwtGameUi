package com.cjmalloy.gameui.client.event;

import com.cjmalloy.gameui.client.event.EventBus.HandlerRegistration;

public interface HasDragEndHandlers
{

    HandlerRegistration addDragEndHandler(DragEndHandler handler);
}