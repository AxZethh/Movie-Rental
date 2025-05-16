package org.finalproject.movierenting.service;


import org.finalproject.movierenting.entity.Film;
import org.finalproject.movierenting.entity.Rental;
import org.finalproject.movierenting.enums.Prices;
import org.finalproject.movierenting.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<String> saveRental(Rental rental) {
        if(rental.getFilms().isEmpty()) {
            return null;
        }

        rental.setTotalPrice(calculatePrice(rental.getFilms(), rental.getDaysRented()));
        rental.getConsumer().setBonusPoints(rental.getConsumer()
                .getBonusPoints() + rentalBonus(rental.getFilms()));

        rentalRepository.save(rental);
        return getReceipt(rental);
    }

    public int calculatePrice(List<Film> films, int timeInDays) {
        int price = 0;
        for(Film film : films) {
            price += film.getPriceType().getPrice();
        }
        return price * timeInDays;
    }

    public int rentalBonus(List<Film> films) {
        int bonus = 0;
        if(films.isEmpty()) {
            return bonus;
        }
        for(Film film : films) {
            if(film.getPriceType() == Prices.PREMIUM_PRICE) {
                bonus += 2;
            }
            bonus++;
        }
        return bonus;
    }

    public List<String> getReceipt(Rental rental) {
        int size = rental.getFilms().size();
        List<String> receipt = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Film film = rental.getFilms().get(i);
            receipt.add(String.format("%s ( %s ) %d days %d EUR",
                    film.getTitle(),
                    film.getFilmType(),
                    rental.getDaysRented(),
                    film.getPriceType().getPrice() * rental.getDaysRented()));
        }
        receipt.add("Total Price: " + rental.getTotalPrice() + " EUR");
        return receipt;
    }




}
