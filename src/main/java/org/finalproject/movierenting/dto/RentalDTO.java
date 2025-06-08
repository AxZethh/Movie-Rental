package org.finalproject.movierenting.dto;

import lombok.Data;
import org.finalproject.movierenting.entity.Consumer;
import org.finalproject.movierenting.enums.PaymentType;

import java.util.*;

@Data
public class RentalDTO {
    private Consumer consumer;
    private int totalPrice;
    private List<FilmDTO> films;
    private PaymentType paymentType;
    private Date rentalDate;
}
