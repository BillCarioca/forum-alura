package com.forumalura.services;

import com.forumalura.domain.topics.Topic;
import com.forumalura.domain.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TopicService {
    Topic save(Topic topic);
    Optional<Topic> findById(Long id);
    Page<Topic> findAll(Pageable pageable);
    Page<Topic> findAllActive(Pageable pageable);
    Page<Topic> findAllByAuthor(User author, Pageable pageable);
    void delete(Long id);
    Topic disable(Long id);
    boolean existById(Long id);
}
