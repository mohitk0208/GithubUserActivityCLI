package org.example.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;


@Getter
public enum EventType {
    COMMIT_COMMENT_EVENT("CommitCommentEvent"),
    CREATE_EVENT("CreateEvent"),
    DELETE_EVENT("DeleteEvent"),
    FORK_EVENT("ForkEvent"),
    GOLLUM_EVENT("GollumEvent"),
    ISSUE_COMMENT_EVENT("IssueCommentEvent"),
    ISSUES_EVENT("IssuesEvent"),
    MEMBER_EVENT("MemberEvent"),
    PUBLIC_EVENT("PublicEvent"),
    PULL_REQUEST_EVENT("PullRequestEvent"),
    PULL_REQUEST_REVIEW_EVENT("PullRequestReviewEvent"),
    PULL_REQUEST_REVIEW_COMMENT_EVENT("PullRequestReviewCommentEvent"),
    PULL_REQUEST_REVIEW_THREAD_EVENT("PullRequestReviewThreadEvent"),
    PUSH_EVENT("PushEvent"),
    RELEASE_EVENT("ReleaseEvent"),
    SPONSORSHIP_EVENT("SponsorshipEvent"),
    WATCH_EVENT("WatchEvent"),
    UNKNOWN("Unknown");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static EventType fromValue(String text) {
        for (EventType type : EventType.values()) {
            if (String.valueOf(type.value).equalsIgnoreCase(text)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
