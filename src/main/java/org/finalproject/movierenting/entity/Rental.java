package org.finalproject.movierenting.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.finalproject.movierenting.enums.PaymentType;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private Consumer consumer;
    private int totalPrice;
    @OneToMany
    private List<Film> films;
    private PaymentType paymentType;
    private Date rentalDate;
}
