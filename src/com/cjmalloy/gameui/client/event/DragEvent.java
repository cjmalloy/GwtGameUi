package com.cjmalloy.gameui.client.event;

import com.cjmalloy.gameui.client.component.DragElement;


public abstract class DragEvent extends MouseEvent
{
    public DragElement dragSource = null;
}
