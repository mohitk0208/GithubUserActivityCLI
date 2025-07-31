package org.example.models;

import lombok.Data;

@Data
public class Actor {
    private Long id;
    private String login;
    private String display_login;
    private String gravatar_id;
    private String url;
    private String avatar_url;
}
