package org.finalproject.movierenting.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.finalproject.movierenting.enums.Prices;


import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private Prices priceType;
    private Date releaseDate;
    private Date added;

}
