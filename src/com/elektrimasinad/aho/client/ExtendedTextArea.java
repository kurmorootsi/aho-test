package com.elektrimasinad.aho.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Extended text area class for listening to paste events.
 * Used to make text area fire ValueChangeEvent on itself whenever "paste" is used.
 * @author rando
 *
 */
public class ExtendedTextArea extends TextArea {

    public ExtendedTextArea() {
        super();
        sinkEvents(Event.ONPASTE);
    }

    @Override
    public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);
        switch (DOM.eventGetType(event)) {
            case Event.ONPASTE:
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {

                    @Override
                    public void execute() {
                        ValueChangeEvent.fire(ExtendedTextArea.this, getText());
                    }

                });
                break;
        }
    }
}