package org.finalproject.movierenting.repository;

import org.finalproject.movierenting.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {

}
