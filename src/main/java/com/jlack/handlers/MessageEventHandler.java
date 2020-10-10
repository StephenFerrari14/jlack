package com.jlack.handlers;

import com.jlack.AppContext;
import com.jlack.Event;

public abstract class MessageEventHandler implements EventHandlerInterface {
    public void handle(Event event, AppContext context) {}
}
