package org.finalproject.movierenting.enums;


import lombok.Getter;

@Getter
public enum FilmType {
    NEW_RELEASE("New Release"),
    REGULAR_FILM("Regular film"),
    OLD_FILM("Old Film");

    private final String type;

    FilmType(String type) {
        this.type = type;
    }


}
