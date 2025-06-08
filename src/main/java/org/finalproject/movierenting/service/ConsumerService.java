package org.finalproject.movierenting.service;

import org.finalproject.movierenting.dto.ConsumerSignUpDTO;
import org.finalproject.movierenting.entity.Consumer;
import org.finalproject.movierenting.enums.ConsumerPermission;
import org.finalproject.movierenting.repository.ConsumerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ConsumerService {

    @Autowired
     ConsumerRepository consumerRepository;

    @Autowired
     ModelMapper modelMapper;

    public List<Consumer> getUsers() {
       return consumerRepository.findAll();
    }

    public Consumer getUser(UUID id) {
        return consumerRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Consumer getUserByEmail(String email) {
        return consumerRepository.findByEmail(email);
    }

    public void saveUser(ConsumerSignUpDTO consumerSignUpDTO) {
        Consumer consumer = modelMapper.map(consumerSignUpDTO, Consumer.class);
        consumer.setPermission(ConsumerPermission.CUSTOMER);
        consumer.setBonusPoints(0);
        consumerRepository.save(consumer);
    }
}
