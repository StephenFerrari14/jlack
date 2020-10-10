package com.jlack;

import com.jlack.entities.SlackEntity;
import com.jlack.handlers.MessageEventHandler;
import com.jlack.handlers.PingEventHandler;
import com.jlack.listeners.EventListener;
import com.jlack.listeners.ListenerRunner;
import com.jlack.listeners.MessageListener;
import com.jlack.middleware.Challenge;
import com.jlack.middleware.Self;
import com.jlack.middleware.Signing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

public class SlackApp {
    private ArrayList<MessageListener> eventHandlerMap = new ArrayList<MessageListener>();

    private AppContext context;

    private int threadCount = 1;
    private boolean singleThreaded = false;
    private Map<String, String> headers;
    private String botId;

    public SlackApp() {
        this.context = new AppContext();
        MessageListener pingListener = new MessageListener("ping", new PingEventHandler());
        eventHandlerMap.add(pingListener);
    }

    public void on(String message, MessageEventHandler handler) {
        on(Pattern.compile(message), handler);
    }

    public void on(Pattern message, MessageEventHandler handler) {
        MessageListener listener = new MessageListener(message, handler);
        this.eventHandlerMap.add(listener);
    }

    // Handler for all events
    public void event(MessageEventHandler handler) {
        EventListener listener = new EventListener(handler);
        this.eventHandlerMap.add(listener);
    }

    public String run(SlackEntity entity) {
        if (Challenge.acceptChallenge(entity)) {
            return Challenge.getResponse(entity);
        }

        // TODO uncomment when function works
//        if (!Signing.isSigned(headers.get("x-slack-signature"))) {
//            return Signing.getResponse();
//        }

        System.out.println(Self.isSelf(entity));
        if (Self.isSelf(entity)) {
            return Self.getResponse();
        }

        if (singleThreaded) {
            ListenerRunner runner = new ListenerRunner(entity, context, eventHandlerMap);
            runner.run();
        } else {
            if (threadCount == 1) {
                Thread listenerThread = new Thread(new ListenerRunner(entity, context, eventHandlerMap));
                listenerThread.start();
            } else {
                // TODO needs to be tested then remove unsupported exception in setter
                // Split all the listeners into chunks then pass the to runner
                for (int x = 0; x < eventHandlerMap.size(); x += threadCount) {
                    MessageListener[] eventHandlerArray = (MessageListener[]) Arrays.copyOfRange(eventHandlerMap.toArray(), x, Math.min(eventHandlerMap.size(), x + threadCount));
                    ArrayList<MessageListener> chunkList = new ArrayList<MessageListener>(Arrays.asList(eventHandlerArray));
                    Thread listenerThread = new Thread(new ListenerRunner(entity, context, chunkList));
                    listenerThread.start();
                }
            }
        }
        return "ack";
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
        if (threadCount > 1) {
            throw new UnsupportedOperationException("More than 1 thread is not supported");
        }
    }

    public void setHeaders(Map<String, String> requestHeaders) {
        headers = requestHeaders;
    }
}
