package com.cjmalloy.gameui.client.event;

import com.cjmalloy.gameui.client.component.UiElement;


public abstract class DragEvent extends MouseEvent
{
    public UiElement dragSource = null;
}
