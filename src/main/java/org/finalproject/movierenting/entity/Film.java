package org.finalproject.movierenting.entity;

import jakarta.persistence.*;
import jdk.jfr.ContentType;
import lombok.Data;
import org.finalproject.movierenting.enums.Prices;
import org.finalproject.movierenting.enums.FilmType;
import org.hibernate.annotations.ColumnDefault;


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
    private String genre;
    private Prices priceType;
    @Column(length = 1000)
    private String description;
    private boolean available;
    private int rentalDuration; // In days
    private Date releaseDate;
    private Date added;
    @Version
    private int ver;

}
