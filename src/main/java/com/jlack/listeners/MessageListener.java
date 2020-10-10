package com.jlack.listeners;

import com.jlack.AppContext;
import com.jlack.Event;
import com.jlack.entities.SlackEntity;
import com.jlack.handlers.MessageEventHandler;

import java.util.regex.Pattern;

public class MessageListener {

    private String pattern;
    private Pattern regexPattern;
    private MessageEventHandler handler;

    public MessageListener(String pattern, MessageEventHandler handler) {
        this.pattern = pattern;
        this.handler = handler;
    }

    public MessageListener(Pattern pattern, MessageEventHandler handler){
        this.regexPattern = pattern;
        this.handler = handler;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public MessageEventHandler getHandler() {
        return handler;
    }

    public void setHandler(MessageEventHandler handler) {
        this.handler = handler;
    }

    public Pattern getRegexPattern() {
        return regexPattern;
    }

    public void setRegexPattern(Pattern regexPattern) {
        this.regexPattern = regexPattern;
    }

    public void runHandler(SlackEntity entity, AppContext context) {
        Event event = new Event(entity);
        this.handler.handle(event, context);
    }
}
