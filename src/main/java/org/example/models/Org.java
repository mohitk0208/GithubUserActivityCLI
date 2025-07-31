package org.example.models;

import lombok.Data;

@Data
public class Org {
    private Long id;
    private String login;
    private String gravatar_id;
    private String url;
    private String avatar_url;
}
