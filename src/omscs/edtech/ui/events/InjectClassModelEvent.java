package omscs.edtech.ui.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import omscs.edtech.ui.models.ClassModel;

public class InjectClassModelEvent extends Event {

    public static final EventType<InjectClassModelEvent> INJECT_CLASS_MODEL =
            new EventType<>(Event.ANY, "INJECT_CLASS_MODEL");

    private final ClassModel classModel;

    public InjectClassModelEvent(ClassModel o, EventTarget eventTarget) {
        super(o, eventTarget, INJECT_CLASS_MODEL);
        classModel = o;
    }

    public ClassModel getClassModel(){
        return classModel;
    }

    @Override
    public InjectClassModelEvent copyFor(Object o, EventTarget eventTarget) {
        return (InjectClassModelEvent) super.copyFor(o, eventTarget);
    }

    @Override
    public EventType<? extends InjectClassModelEvent> getEventType() {
        return (EventType<? extends InjectClassModelEvent>) super.getEventType();
    }
}
