package com.jlack.listeners;

import com.jlack.AppContext;
import com.jlack.entities.SlackEntity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenerRunner implements Runnable {

    private SlackEntity entity;
    private AppContext context;
    private ArrayList<MessageListener> eventHandlerMap;

    public ListenerRunner(SlackEntity entity, AppContext context, ArrayList<MessageListener> eventHandlerMap) {
        this.entity = entity;
        this.context = context;
        this.eventHandlerMap = eventHandlerMap;
    }

    @Override
    public void run() {
        for (MessageListener ml : eventHandlerMap) {
            if (ml.getRegexPattern() != null) {
                Matcher m = ml.getRegexPattern().matcher(entity.event.text);
                boolean matched = m.matches();
                context.setMessagePattern(ml.getRegexPattern());
                if (matched) {
                    System.out.println("Regex matched");
                    ml.runHandler(entity, context);
                }
            } else {
                if (entity.event.text.equals(ml.getPattern())) {
                    Pattern contextPattern = Pattern.compile(entity.event.text);
                    context.setMessagePattern(contextPattern);
                    System.out.println("Pattern matched " + ml.getPattern());
                    ml.runHandler(entity, context);
                }
            }
        }
    }
}
