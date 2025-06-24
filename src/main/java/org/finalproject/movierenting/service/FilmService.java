package org.finalproject.movierenting.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.finalproject.movierenting.dto.FilmDTO;
import org.finalproject.movierenting.entity.Film;
import org.finalproject.movierenting.repository.FilmRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final ModelMapper modelMapper;
    public FilmService(FilmRepository filmRepository, ModelMapper modelMapper) {
        this.filmRepository = filmRepository;
        this.modelMapper = modelMapper;
    }

    public List<Film> getFilms() {
        return filmRepository.findAll();
    }

    public List<Film> getAvailableFilms() {
        return filmRepository.findByAvailable(true);
    }

    public Film getFilm(UUID id) {
        return filmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Film does not Exist"));
    }

    public void saveFilm(FilmDTO filmDTO) {
        filmRepository.save(modelMapper.map(filmDTO, Film.class));
    }

    public String deleteFilm(UUID id) {
        if(!filmRepository.existsById(id)) {
            log.error("Film with id: {} does not exist!", id);
            throw new NoSuchElementException("Film with id: " + id + " does not exist!");
        }
        filmRepository.deleteById(id);
        return "Film Deleted Successfully!";
    }

}
