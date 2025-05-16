package org.finalproject.movierenting.service;

import org.finalproject.movierenting.dto.FilmDTO;
import org.finalproject.movierenting.entity.Film;
import org.finalproject.movierenting.repository.FilmRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final ModelMapper modelMapper;

    public FilmService(FilmRepository filmRepository, ModelMapper modelMapper) {
        this.filmRepository = filmRepository;
        this.modelMapper = modelMapper;
    }

    public List<Film> getFilms() {
        List<Film> films;
        try {
            films = filmRepository.findAll();
        } catch (RuntimeException e) {
            return new ArrayList<>();
        }
        return films;
    }

    public Film getFilm(UUID id) {
        return filmRepository.findById(id).orElse(null);
    }

    public void saveFilm(FilmDTO filmDTO) {
        filmRepository.save(modelMapper.map(filmDTO, Film.class));
    }







}
