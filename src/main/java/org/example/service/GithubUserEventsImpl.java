package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.GithubUserEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public class GithubUserEventsImpl implements GithubUserEvents {
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public GithubUserEventsImpl() {
        this.httpClient = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    @Override
    public List<String> getUserActivity(String username) throws IllegalArgumentException {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Github username is required.");
        }

        List<GithubUserEvent> githubUserEvents = getUserEvents(username);

        return getActivityMessagesFromEvents(githubUserEvents);
    }

    private List<String> getActivityMessagesFromEvents(List<GithubUserEvent> githubUserEvents) {
        return githubUserEvents.stream()
                .map(githubUserEvent -> switch (githubUserEvent.getType()) {
                    case COMMIT_COMMENT_EVENT ->
                            String.format("Commented on a commit in %s", githubUserEvent.getRepo().getName());
                    case CREATE_EVENT -> {
                        if (githubUserEvent.getPayload().get("ref_type").asText().equalsIgnoreCase("repository")) {
                            yield String.format("Created a repository: %s", githubUserEvent.getRepo().getName());
                        } else {
                            yield String.format("Created a %s in %s", githubUserEvent.getPayload().get("ref_type").asText(), githubUserEvent.getRepo().getName());
                        }
                    }
                    case DELETE_EVENT ->
                            String.format("Deleted %s in %s", githubUserEvent.getPayload().get("ref_type").asText(), githubUserEvent.getRepo().getName());
                    case FORK_EVENT -> String.format("Forked repository %s", githubUserEvent.getRepo().getName());
                    case GOLLUM_EVENT, MEMBER_EVENT, PUBLIC_EVENT, PULL_REQUEST_EVENT, PULL_REQUEST_REVIEW_EVENT,
                         PULL_REQUEST_REVIEW_COMMENT_EVENT, PULL_REQUEST_REVIEW_THREAD_EVENT, RELEASE_EVENT,
                         WATCH_EVENT, SPONSORSHIP_EVENT -> null;
                    case ISSUE_COMMENT_EVENT ->
                            String.format("Commented on an issue in %s", githubUserEvent.getRepo().getName());
                    case ISSUES_EVENT ->
                            String.format("%s issue in %s", githubUserEvent.getPayload().get("action").asText(), githubUserEvent.getRepo().getName());
                    case PUSH_EVENT ->
                            String.format("Pushed %d commits to %s", githubUserEvent.getPayload().get("size").asInt(), githubUserEvent.getRepo().getName());
                    case UNKNOWN ->
                            String.format("Unknown event of type %s received in %s", githubUserEvent.getType(), githubUserEvent.getRepo().getName());
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private List<GithubUserEvent> getUserEvents(String username) {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/" + username + "/events"))
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> httpResponse = this.httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            return mapper.readValue(httpResponse.body(), new TypeReference<>() {
            });

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return List.of();
        }
    }
}
