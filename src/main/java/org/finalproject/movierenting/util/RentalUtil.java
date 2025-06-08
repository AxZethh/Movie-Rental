package org.finalproject.movierenting.util;

import org.finalproject.movierenting.entity.Film;
import org.finalproject.movierenting.entity.Rental;
import org.finalproject.movierenting.enums.PaymentType;
import org.finalproject.movierenting.enums.Prices;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentalUtil {

    private final int priceInBonus = 25;

    public List<String> getReceipt(Rental rental) {
        List<String> receipt = new ArrayList<>();
        PaymentType paymentType = rental.getPaymentType();
        if(paymentType == PaymentType.CARD || paymentType == PaymentType.CASH) {
            for (Film film : rental.getFilms()) {
                receipt.add(String.format("%s ( %s ) %d days %d EUR",
                        film.getTitle(),
                        film.getFilmType(),
                        film.getRentalDuration(),
                        film.getPriceType().getPrice() * film.getRentalDuration()));
            }
        } else if (paymentType == PaymentType.BONUS) {
            for (Film film : rental.getFilms()) {
                receipt.add(String.format("%s ( %s ) %d days (Paid with %d Bonus points)",
                        film.getTitle(),
                        film.getFilmType(),
                        film.getRentalDuration(),
                        getBonusCostPerFilm(film.getRentalDuration())));
            }
            receipt.add("Remaining Bonus Points: " + rental.getConsumer().getBonusPoints());
        }
        receipt.add("Total Price: " + rental.getTotalPrice() + " EUR");
        return receipt;
    }

    public int calcGainedBonus(List<Film> films) {
        int bonus = 0;
        if(films.isEmpty()) {
            return bonus;
        }
        for(Film film : films) {
            if(film.getPriceType() == Prices.PREMIUM_PRICE) {
                bonus += 2;
                continue;
            }
            bonus++;
        }
        return bonus;
    }

    public int calculateTotalPrice(List<Film> films) {
        int price = 0;
        for(Film film : films) {
            price += film.getPriceType().getPrice() * film.getRentalDuration();
        }

        return price;
    }

    public int calcUsedBonus(List<Film> films) {
        int bonusCostTotal = 0;
        for(Film film : films) {
            bonusCostTotal += getBonusCostPerFilm(film.getRentalDuration());
        }
        return bonusCostTotal;
    }

    public int getBonusCostPerFilm(int days) {
        return days * priceInBonus;
    }
}
