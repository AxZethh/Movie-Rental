package org.finalproject.movierenting.dto;

import lombok.Data;
import org.finalproject.movierenting.enums.FilmType;
import org.finalproject.movierenting.enums.Prices;

import java.util.*;

@Data
public class FilmDTO {
    private UUID id;
    private String title;
    private String genre;
    private FilmType filmType;
    private String description;
    private Prices priceType;
    private int rentalDuration;
    private boolean available;
    private Date releaseDate;
}
