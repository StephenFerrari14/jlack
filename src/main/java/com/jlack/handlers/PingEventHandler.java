package com.jlack.handlers;

import com.slack.api.methods.SlackApiException;
import com.jlack.AppContext;
import com.jlack.Event;

import java.io.IOException;

// Ping is included for basic functionality of a slack bot
public class PingEventHandler extends MessageEventHandler {
    @Override
    public void handle(Event event, AppContext context) {
        try {
            context.getSlackClient().chatPostMessage(req -> req.channel(event.getEntity().event.user).text("pong"));
        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
        }
    }
}
