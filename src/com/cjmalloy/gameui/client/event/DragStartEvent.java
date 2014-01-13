package com.cjmalloy.gameui.client.event;

public class DragStartEvent extends DragEvent
{
    public static final EventType<DragStartEvent> TYPE = new EventType<DragStartEvent>()
    {

        @Override
        public DragStartEvent createEvent()
        {
            return new DragStartEvent();
        }

        @Override
        public boolean equals(Event e)
        {
            return e instanceof DragStartEvent;
        }
    };

    @Override
    public void callHandler(EventHandler handler)
    {
        if (!(handler instanceof DragStartHandler)) { throw new InvalidEventHandlerError(); }
        if (!source.isMouseEnabled()) return;

        if (EventBus.get().capture == source || containsPoint())
        {
            DragStartHandler h = (DragStartHandler) handler;
            h.onDragStart(this);
        }
    }

    public EventType<? extends Event> getType()
    {
        return TYPE;
    }
}
