package com.cjmalloy.gameui.client.core;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

public abstract class EventDispatcher<T> implements HasValueChangeHandlers<T>
{

    private HandlerManager handlerManager;

    /**
     * Adds this handler.
     *
     * @param <H>
     *            the type of handler to add
     * @param type
     *            the event type
     * @param handler
     *            the handler
     * @return {@link HandlerRegistration} used to remove the handler
     */
    public final <H extends EventHandler> HandlerRegistration addHandler(final H handler, GwtEvent.Type<H> type)
    {
        return ensureHandlers().addHandler(type, handler);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler)
    {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    public void fireEvent(GwtEvent<?> event)
    {
        if (handlerManager != null)
        {
            handlerManager.fireEvent(event);
        }
    }

    public void update(T value)
    {
        ValueChangeEvent.fire(this, value);
    }

    /**
     * Ensures the existence of the handler manager.
     *
     * @return the handler manager
     * */
    HandlerManager ensureHandlers()
    {
        return handlerManager == null ? handlerManager = createHandlerManager() : handlerManager;
    }

    /**
     * Creates the {@link HandlerManager} used by this Widget. You can override
     * this method to create a custom {@link HandlerManager}.
     *
     * @return the {@link HandlerManager} you want to use
     */
    protected HandlerManager createHandlerManager()
    {
        return new HandlerManager(this);
    }

}
