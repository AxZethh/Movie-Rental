package org.finalproject.movierenting.repository;

import org.finalproject.movierenting.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FilmRepository extends JpaRepository<Film, UUID> {
    List<Film> findByAvailable(boolean available);
}
