package org.finalproject.movierenting.service;

import org.finalproject.movierenting.dto.RentRequestDTO;
import org.finalproject.movierenting.entity.Consumer;
import org.finalproject.movierenting.entity.Film;
import org.finalproject.movierenting.entity.Rental;
import org.finalproject.movierenting.enums.ConsumerPermission;
import org.finalproject.movierenting.enums.FilmType;
import org.finalproject.movierenting.enums.PaymentType;
import org.finalproject.movierenting.enums.Prices;
import org.finalproject.movierenting.repository.RentalRepository;
import org.finalproject.movierenting.util.RentalUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RentalServiceTest {
    @Mock
    RentalRepository rentalRepo;
    @Mock
    ModelMapper modelMapper;
    @Mock
    RentalUtil rentalUtil;
    @Mock
    FilmService filmService;
    @Mock
    ConsumerService consumerService;
    @InjectMocks
    RentalService rentalService;

    private Consumer consumer;
    private Rental testRental;
    private Film film1, film2, film3;

    List<Film> films;

    @BeforeEach
    public void setUp() {
        consumer = new Consumer();
        testRental = new Rental();

        consumer.setId(UUID.randomUUID());
        consumer.setEmail("Some@gmail.com");
        consumer.setBonusPoints(50);
        consumer.setPermission(ConsumerPermission.CUSTOMER);
        consumer.setFirstName("Roach");
        consumer.setLastName("Sandals");

        testRental.setId(UUID.randomUUID());
        testRental.setPaymentType(PaymentType.CARD);

        films = new ArrayList<>();

        Authentication authentication = mock(Authentication.class);
        lenient().when(authentication.getPrincipal()).thenReturn(consumer.getEmail());

        SecurityContext securityContext = mock(SecurityContext.class);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void givenValidRentalId_shouldReturnRental() {
        when(rentalRepo.findById(testRental.getId())).thenReturn(Optional.of(testRental));

        Rental result = rentalService.getRental(testRental.getId());

        assertNotNull(result);
        assertEquals(testRental, result);
        verify(rentalRepo).findById(testRental.getId());
    }

    @Test
    void givenValidRentRequestDTO_shouldSaveRentalAndReturnReceipt() {

        film1 = buildFilm("Something", FilmType.REGULAR_FILM,Prices.BASIC_PRICE,4);
        film2 = buildFilm("Something II", FilmType.NEW_RELEASE,Prices.PREMIUM_PRICE,2);
        film3 = buildFilm("Something III", FilmType.REGULAR_FILM,Prices.BASIC_PRICE,6);
        films = List.of(film1,film2,film3);

        RentRequestDTO requestDTO = new RentRequestDTO();
        requestDTO.setFilmIds(films.stream().map(Film::getId).collect(Collectors.toList()));
        requestDTO.setPaymentType(PaymentType.CARD);

        when(consumerService.getUserByEmail(consumer.getEmail())).thenReturn(consumer);
        when(filmService.getFilm(film1.getId())).thenReturn(film1);
        when(filmService.getFilm(film2.getId())).thenReturn(film2);
        when(filmService.getFilm(film3.getId())).thenReturn(film3);
        when(rentalRepo.save(any(Rental.class))).thenReturn(testRental);

        List<String> result = rentalService.saveRental(requestDTO).getBody();

        assertNotNull(result);
        assertInstanceOf(List.class, result);
    }

//    @Test
//    void givenListOfFilms_shouldReturnBonusPoints() {
//
//    }
//
//    @Test
//    void givenListOfFilms_shouldReturnRentalBonus() {
//
//    }


    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    private Film buildFilm(String title, FilmType type, Prices price, int duration) {
        Film film = new Film();
        film.setId(UUID.randomUUID());
        film.setTitle(title);
        film.setFilmType(type);
        film.setPriceType(price);
        film.setRentalDuration(duration);
        film.setAvailable(true);
        return film;
    }
}