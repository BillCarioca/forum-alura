package com.forumalura.repositories;

import com.forumalura.domain.topics.Topic;
import com.forumalura.domain.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findAllByActiveDateTrue(Pageable pageable);
    Page<Topic> findAllByAuthor(User author, Pageable pageable);
    long count();
}
