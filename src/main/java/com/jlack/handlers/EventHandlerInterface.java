package com.jlack.handlers;

import com.jlack.AppContext;
import com.jlack.Event;

interface EventHandlerInterface {
    void handle(Event event, AppContext context);
}