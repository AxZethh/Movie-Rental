package org.finalproject.movierenting.service;

import org.finalproject.movierenting.entity.Film;
import org.finalproject.movierenting.entity.Rental;
import org.finalproject.movierenting.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Rental getRental(UUID id) {
       return rentalRepository.findById(id).orElseThrow();
    }

    public Rental saveRental(Rental rental) {
        rental.setTotalPrice(calculatePrice(rental.getFilms(), rental.getDaysRented()));
        return rentalRepository.save(rental);
    }

    public int calculatePrice(List<Film> films, int timeInDays) {
        int price = 0;
        for(Film film : films) {
            price += film.getPriceType().getPrice();
        }
        return price * timeInDays;
    }
}
