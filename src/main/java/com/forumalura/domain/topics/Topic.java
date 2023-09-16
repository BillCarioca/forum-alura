package com.forumalura.domain.topics;

import com.forumalura.domain.answers.Answer;
import com.forumalura.domain.courses.Course;
import com.forumalura.domain.users.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Topic {
    private Long id;
    private String title;
    private String message;
    private LocalDateTime creationDate = LocalDateTime.now();
    private TopicStatus status = TopicStatus.NAO_RESPONDIDO;
    private User author;
    private Course course;
    private List<Answer> answers = new ArrayList<>();
}
