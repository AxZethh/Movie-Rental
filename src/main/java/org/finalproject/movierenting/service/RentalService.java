package org.finalproject.movierenting.service;


import org.finalproject.movierenting.dto.RentalDTO;
import org.finalproject.movierenting.entity.Rental;
import org.finalproject.movierenting.enums.PaymentType;
import org.finalproject.movierenting.repository.RentalRepository;
import org.finalproject.movierenting.util.RentalUtil;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RentalUtil rentalUtil;
    private final ModelMapper modelMapper;

    public RentalService(RentalRepository rentalRepository, RentalUtil rentalUtil, ModelMapper modelMapper) {
        this.rentalRepository = rentalRepository;
        this.rentalUtil = rentalUtil;
        this.modelMapper = modelMapper;
    }

    public Rental getRental(UUID id) {
       return rentalRepository.findById(id).orElseThrow();
    }

    public List<RentalDTO> getRentals() {
        return rentalRepository.findAll().stream()
                .map(rental -> modelMapper.map(rental, RentalDTO.class)).toList();
    }

    public List<String> saveRental(RentalDTO rentalDTO) {
        Rental rental = modelMapper.map(rentalDTO, Rental.class);
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

    public String deleteRental(UUID id) {
        if(!rentalRepository.existsById(id)) {
            return null;
        }
        rentalRepository.deleteById(id);
        return "Rental deleted successfully!";
    }



}
