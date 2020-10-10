package com.jlack;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.jlack.entities.SlackEntity;

import java.io.IOException;

public class Event {

    private final MethodsClient slackClient;
    private SlackEntity entity;

    public Event(SlackEntity entity) {
        this.entity = entity;
        Slack slack = Slack.getInstance();
        String token = System.getenv("BOT_TOKEN");
        slackClient = slack.methods(token);
    }

    public SlackEntity getEntity() {
        return entity;
    }

    public void setEntity(SlackEntity entity) {
        this.entity = entity;
    }

    /**
     * Client to interact with slack api
     *
     * Ex. event.getSlackClient().chatPostMessage(req -> req.channel(entity.event.user).text("message"));
     *
     * @return MethodsClient
     */
    public MethodsClient getSlackClient() {
        return slackClient;
    }

    /**
     * Reply to event entity with the given string
     *
     * @param message String
     */
    public void reply(String message) {
        MethodsClient client = getSlackClient();
        try {
            if (entity.event.type.equals("app_mention")) {
                String userMessage = "<@" + entity.event.user + "> " + message;
                if (entity.event.event_ts.equals(entity.event.thread_ts)) {
                    client.chatPostMessage(req -> req.channel(entity.event.channel).text(userMessage));
                    } else {
                    client.chatPostMessage(req -> req.channel(entity.event.channel).threadTs(entity.event.thread_ts).text(userMessage));
                    }
            } else {
                if (entity.event.event_ts.equals(entity.event.thread_ts)) {
                    client.chatPostMessage(req -> req.channel(entity.event.channel).text(message));
                } else {
                    client.chatPostMessage(req -> req.channel(entity.event.channel).threadTs(entity.event.thread_ts).text(message));
                }
            }
        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
        }
    }
}
