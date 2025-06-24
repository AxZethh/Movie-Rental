package org.finalproject.movierenting.service;


import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

import org.finalproject.movierenting.dto.RentRequestDTO;
import org.finalproject.movierenting.dto.RentalDTO;
import org.finalproject.movierenting.entity.Film;
import org.finalproject.movierenting.entity.Rental;
import org.finalproject.movierenting.enums.PaymentType;
import org.finalproject.movierenting.repository.RentalRepository;
import org.finalproject.movierenting.util.RentalUtil;
import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RentalUtil rentalUtil;
    private final ModelMapper modelMapper;
    private final FilmService filmService;
    private final ConsumerService consumerService;

    public RentalService(RentalRepository rentalRepository, RentalUtil rentalUtil, ModelMapper modelMapper, FilmService filmService, ConsumerService consumerService) {
        this.rentalRepository = rentalRepository;
        this.rentalUtil = rentalUtil;
        this.modelMapper = modelMapper;
        this.filmService = filmService;
        this.consumerService = consumerService;
    }

    public Rental getRental(UUID id) {
       return rentalRepository.findById(id).orElseThrow();
    }

    public List<RentalDTO> getRentals() {
        return rentalRepository.findAll().stream()
                .map(rental -> modelMapper.map(rental, RentalDTO.class)).toList();
    }

    @Transactional
    public ResponseEntity<List<String>> saveRental(RentRequestDTO rentRequestDTO) {
        if(rentRequestDTO.getFilmIds().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Rental rental = new Rental();
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        rental.setConsumer(consumerService.getUserByEmail(email));
        rental.setRentalDate(Date.from(Instant.now()));
        rental.setPaymentType(rentRequestDTO.getPaymentType());

        rental.setFilms(
                rentRequestDTO.getFilmIds().stream()
                .map(filmService::getFilm)
                .toList());

        for (Film film : rental.getFilms()) {
            if (!film.isAvailable()) {
                throw new IllegalStateException("Film " + film.getTitle() + " is no longer available.");
            }
        }
        if(rental.getPaymentType() == PaymentType.BONUS) {
            rental.setTotalPrice(0);
            rental.getConsumer().setBonusPoints(rentalUtil.calcUsedBonus(rental.getFilms()));
            rental.getFilms().forEach(film -> film.setAvailable(false));
            return ResponseEntity.ok(rentalUtil.getReceipt(rental));
        }
        rental.setTotalPrice(rentalUtil.calculateTotalPrice(rental.getFilms()));
        rental.getFilms().forEach(film -> film.setAvailable(false));

        rental.getConsumer().setBonusPoints(
                rental.getConsumer()
                .getBonusPoints() + rentalUtil.calcGainedBonus(rental.getFilms()));
        try {
            rentalRepository.save(rental);
        } catch (OptimisticLockException e) {
            return ResponseEntity.ok(List.of("One or more Films are unavailable"));
        }
        return ResponseEntity.ok(rentalUtil.getReceipt(rental));
    }

    public String deleteRental(UUID id) {
        if(!rentalRepository.existsById(id)) {
            return null;
        }
        rentalRepository.deleteById(id);
        return "Rental deleted successfully!";
    }

}
