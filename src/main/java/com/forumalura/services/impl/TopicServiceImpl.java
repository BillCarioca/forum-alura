package com.forumalura.services.impl;

import com.forumalura.domain.topics.Topic;
import com.forumalura.domain.users.User;
import com.forumalura.repositories.TopicRepository;
import com.forumalura.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicRepository topicRepository;

    @Transactional
    @Override
    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Optional<Topic> findById(Long id) {
        return topicRepository.findById(id);
    }

    @Override
    public Page<Topic> findAll(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    @Override
    public Page<Topic> findAllActive(Pageable pageable) {
        return topicRepository.findAllByActiveDateTrue(pageable);
    }

    @Override
    public Page<Topic> findAllByAuthor(User author, Pageable pageable) {
        return topicRepository.findAllByAuthor(author,pageable);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (topicRepository.existsById(id))topicRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Topic disable(Long id) {
        Optional<Topic> optional = topicRepository.findById(id);
        if (optional.isPresent()){
            optional.get().setActiveDate(false);
            return topicRepository.save(optional.get());
        }
        return null;
    }

    @Override
    public boolean existById(Long id) {
        return topicRepository.existsById(id);
    }
}
