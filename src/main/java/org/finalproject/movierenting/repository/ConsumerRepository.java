package org.finalproject.movierenting.repository;

import org.finalproject.movierenting.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConsumerRepository extends JpaRepository<Consumer, UUID> {
    Consumer findByEmail(String email);
}
