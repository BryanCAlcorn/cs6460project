package omscs.edtech.ui.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class InjectModelEvent<T> extends Event {

    public static final EventType<InjectModelEvent> INJECT_MODEL =
            new EventType<>(Event.ANY, "INJECT_MODEL");

    private final T modelToInject;

    public InjectModelEvent(T o, EventTarget eventTarget) {
        super(o, eventTarget, INJECT_MODEL);
        modelToInject = o;
    }

    public T getModelToInject(){
        return modelToInject;
    }

    @Override
    public InjectModelEvent<T> copyFor(Object o, EventTarget eventTarget) {
        return (InjectModelEvent<T>) super.copyFor(o, eventTarget);
    }

    @Override
    public EventType<? extends InjectModelEvent<T>> getEventType() {
        return (EventType<? extends InjectModelEvent<T>>) super.getEventType();
    }
}
