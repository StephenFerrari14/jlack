package com.jlack.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// TODO add remaining properties from event
@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackEventEntity {
    @JsonProperty
    public String user;
    @JsonProperty
    public String text;
    @JsonProperty
    public String type;
    @JsonProperty
    public String event_ts;
    @JsonProperty
    public String thread_ts;
    @JsonProperty
    public String ts;
    @JsonProperty
    public String reaction;
    @JsonProperty
    public String channel;
}