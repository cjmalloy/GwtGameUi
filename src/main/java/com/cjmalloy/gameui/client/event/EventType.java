package com.cjmalloy.gameui.client.event;

public abstract class EventType<T extends Event> {

  public abstract T createEvent();

  public abstract boolean equals(Event e);
}
