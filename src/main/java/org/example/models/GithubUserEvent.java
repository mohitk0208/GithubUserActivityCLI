package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.example.enums.EventType;

@Data
public class GithubUserEvent {
    private Long id;
    private EventType type;
    private Actor actor;
    private Repo repo;
    private JsonNode payload;
    @JsonProperty("public")
    private boolean _public;
    private String created_at;
    private Org org;
}
