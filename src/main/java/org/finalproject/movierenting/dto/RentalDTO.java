package org.finalproject.movierenting.dto;

import lombok.Data;
import org.finalproject.movierenting.entity.Consumer;

import java.util.List;

@Data
public class RentalDTO {

    private Consumer consumer;
    private List<FilmDTO> films;
}
