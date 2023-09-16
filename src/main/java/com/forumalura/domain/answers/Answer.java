package com.forumalura.domain.answers;

import com.forumalura.domain.topics.Topic;
import com.forumalura.domain.users.User;

import java.time.LocalDateTime;

public class Answer {
    private Long id;
    private String message;
    private Topic topic;
    private LocalDateTime creationDate = LocalDateTime.now();
    private User author;
    private Boolean solution = false;
}
