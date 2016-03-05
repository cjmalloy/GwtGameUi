package com.cjmalloy.gameui.client.event;

public class DragEndEvent extends DragEvent {

  public static final EventType<DragEndEvent> TYPE = new EventType<DragEndEvent>() {

    @Override
    public DragEndEvent createEvent() {
      return new DragEndEvent();
    }

    @Override
    public boolean equals(Event e) {
      return e instanceof DragEndEvent;
    }
  };

  @Override
  public void callHandler(EventHandler handler) {
    if (!(handler instanceof DragEndHandler)) {
      throw new InvalidEventHandlerError();
    }
    if (!source.isMouseEnabled()) {
      return;
    }

    if (EventBus.get().capture.contains(source) || containsPoint()) {
      DragEndHandler h = (DragEndHandler) handler;
      h.onDragEnd(this);
    }
  }

  public EventType<? extends Event> getType() {
    return TYPE;
  }
}
