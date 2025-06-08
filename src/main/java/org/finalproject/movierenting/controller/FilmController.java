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

    @GetMapping("getAvailableFilms")
    public ResponseEntity<List<Film>> getAvailableFilms() {
        List<Film> films = filmService.getAvailableFilms();
        if(films.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(films);
    }

    @GetMapping("getFilm/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable UUID id) {
        return filmService.getFilm(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
