package com.jlack;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;

import java.util.regex.Pattern;

public class AppContext {

    private MethodsClient slackClient;

    private Pattern messagePattern;

    AppContext() {
        Slack slack = Slack.getInstance();
        String token = System.getenv("BOT_TOKEN");
        slackClient = slack.methods(token);
    }

    public Pattern getMessagePattern() { return messagePattern; }

    public void setMessagePattern(Pattern pattern) {
        messagePattern = pattern;
    }

    public MethodsClient getSlackClient() {
        return slackClient;
    }
}
