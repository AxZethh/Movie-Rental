package org.finalproject.movierenting.util;


import org.finalproject.movierenting.entity.Consumer;
import org.finalproject.movierenting.entity.Film;
import org.finalproject.movierenting.entity.Rental;
import org.finalproject.movierenting.enums.FilmType;
import org.finalproject.movierenting.enums.PaymentType;
import org.finalproject.movierenting.enums.Prices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class RentalUtilTest {


    RentalUtil rentalUtil = new RentalUtil();

    List<Film> films = new ArrayList<>();
    Rental rental = new Rental();

    @BeforeEach
    public void setUp() {
        Consumer consumer = new Consumer();
        consumer.setId(UUID.randomUUID());
        consumer.setFirstName("John");
        consumer.setLastName("Doe");
        consumer.setBonusPoints(100);
        consumer.setEmail("Some@email.com");

        Film film1 = new Film();
        film1.setId(UUID.randomUUID());
        film1.setTitle("Something to do with something");
        film1.setFilmType(FilmType.NEW_RELEASE);
        film1.setAdded(Date.from(Instant.now()));
        film1.setRentalDuration(4);
        film1.setReleaseDate(Date.from(Instant.now().plusSeconds(1000000)));
        film1.setPriceType(Prices.PREMIUM_PRICE);
        film1.setAvailable(true);

        Film film2 = new Film();
        film2.setId(UUID.randomUUID());
        film2.setTitle("Something to do with something2");
        film2.setFilmType(FilmType.REGULAR_FILM);
        film2.setAdded(Date.from(Instant.now()));
        film2.setRentalDuration(4);
        film2.setReleaseDate(Date.from(Instant.now().plusSeconds(1000000)));
        film2.setPriceType(Prices.BASIC_PRICE);
        film2.setAvailable(true);

        films.add(film1);
        films.add(film2);

        rental.setId(UUID.randomUUID());
        rental.setFilms(films);
        rental.setConsumer(consumer);
        rental.setTotalPrice(rentalUtil.calculateTotalPrice(films));
        rental.setPaymentType(PaymentType.CARD);
    }

    @Test
    public void givenFilm_shouldReturnBonusCostPerFilm() {
        assertEquals(100, rentalUtil.getBonusCostPerFilm(films.getFirst().getRentalDuration()));
    }

    @Test
    public void givenListOfFilms_shouldReturnTotalPrice() {
        assertEquals(28, rentalUtil.calculateTotalPrice(films));
    }

    @Test
    public void givenRental_shouldReturnReceipt() {
        List<String> receipt = rentalUtil.getReceipt(rental);
        receipt.forEach(System.out::println);

    }
}
