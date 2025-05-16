package org.finalproject.movierenting.service;

import org.finalproject.movierenting.entity.Consumer;
import org.finalproject.movierenting.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ConsumerService {

    @Autowired
    private ConsumerRepository consumerRepository;

    public List<Consumer> getUsers() {
       return consumerRepository.findAll();
    }

    public Consumer getUser(UUID id) {
        return consumerRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
