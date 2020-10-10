package com.jlack.listeners;

import com.jlack.handlers.MessageEventHandler;

import java.util.regex.Pattern;

public class EventListener extends MessageListener {
    public EventListener(MessageEventHandler handler) {
        super(Pattern.compile(".*"), handler);
    }
}
