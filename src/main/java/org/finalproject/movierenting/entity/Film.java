package org.finalproject.movierenting.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.finalproject.movierenting.enums.Prices;
import org.finalproject.movierenting.enums.FilmType;


import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private FilmType filmType;
    private Prices priceType;
    private int rentalDuration; // In days
    private Date releaseDate;
    private Date added;

}
