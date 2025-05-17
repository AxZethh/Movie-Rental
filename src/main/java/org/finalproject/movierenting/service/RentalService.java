package org.finalproject.movierenting.service;


import org.finalproject.movierenting.entity.Rental;
import org.finalproject.movierenting.enums.PaymentType;
import org.finalproject.movierenting.repository.RentalRepository;
import org.finalproject.movierenting.util.RentalUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RentalUtil rentalUtil;

    public RentalService(RentalRepository rentalRepository, RentalUtil rentalUtil) {
        this.rentalRepository = rentalRepository;
        this.rentalUtil = rentalUtil;
    }

    public Rental getRental(UUID id) {
       return rentalRepository.findById(id).orElseThrow();
    }

    public List<String> saveRental(Rental rental) {
        PaymentType paymentType = rental.getPaymentType();
        if(rental.getFilms().isEmpty()) {
            return null;
        }
        if(paymentType == PaymentType.BONUS) {
            rental.setTotalPrice(0);
            rental.getConsumer().setBonusPoints(rentalUtil.calcUsedBonus(rental.getFilms()));
            return rentalUtil.getReceipt(rental);
        }
        rental.setTotalPrice(rentalUtil.calculateTotalPrice(rental.getFilms()));
        // Slightly weird execution,however I'm doing it this way to avoid overwriting the consumer connected to the Rental
        rental.getConsumer().setBonusPoints(
                rental.getConsumer()
                .getBonusPoints() + rentalUtil.calcGainedBonus(rental.getFilms()));

        rentalRepository.save(rental);
        return rentalUtil.getReceipt(rental);
    }






}
