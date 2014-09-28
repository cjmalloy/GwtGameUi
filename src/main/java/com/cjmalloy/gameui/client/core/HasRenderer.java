package com.cjmalloy.gameui.client.core;


public interface HasRenderer<T>
{

    Renderer getRenderer(T t);
}
