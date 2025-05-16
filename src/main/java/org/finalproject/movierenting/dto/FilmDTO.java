package org.finalproject.movierenting.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FilmDTO {
    private UUID id;
    private String title;
    private String type;
}
