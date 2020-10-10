package com.jlack.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// TODO Add missing properties from payload
// https://api.slack.com/events-api
@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackEntity {
    @JsonProperty
    public String challenge;
    @JsonProperty
    public String token; // Deprecated value, use signing secret instead
    @JsonProperty
    public String type;
    @JsonProperty
    public String team_id;
    @JsonProperty
    public String api_app_id;
    @JsonProperty
    public String event_id;
    @JsonProperty
    public int event_time;
    @JsonProperty
    public SlackEventEntity event;
    @JsonProperty
    public int minute_rate_limited;
}