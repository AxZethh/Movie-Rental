package org.finalproject.movierenting.controller;

import org.finalproject.movierenting.dto.FilmDTO;
import org.finalproject.movierenting.entity.Film;
import org.finalproject.movierenting.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("localhost:8080/")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("getFilms")
    public ResponseEntity<List<Film>> getFilms() {
        List<Film> films = filmService.getFilms();

        if(films.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(films);
    }

    @GetMapping("getFilm/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable UUID id) {
        Film film = filmService.getFilm(id);
        if(film == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(film);
    }

    @PostMapping("addFilm")
    public ResponseEntity<String> addFilm(@RequestBody FilmDTO filmDTO) {
        try {
            filmService.saveFilm(filmDTO);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("Film Saved Successfully");
    }
}
