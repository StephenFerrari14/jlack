package com.jlack.middleware;

import com.jlack.entities.SlackEntity;

public class Challenge {
    public static boolean acceptChallenge(SlackEntity entity) {
        return entity.challenge != null;
    }
    public static String getResponse(SlackEntity entity) {
        return "{\"challenge\": \"" + entity.challenge + "\"}";
    }
}
