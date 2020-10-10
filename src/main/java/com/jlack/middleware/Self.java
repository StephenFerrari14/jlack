package com.jlack.middleware;

import com.jlack.entities.SlackEntity;

public class Self {
    private String _botId;
    public static boolean isSelf(SlackEntity entity) {
        String botId = System.getenv("BOT_ID"); // Works for now but can get botid by other means
        return entity.event.user.equals(botId);
    }

    public static String getResponse() { return "self"; }

    public String getBotId() {
        return _botId;
    }

    public void setBotId(String _botId) {
        this._botId = _botId;
    }
}
