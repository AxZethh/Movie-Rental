package org.finalproject.movierenting.enums;

import lombok.Getter;
// If the order of enums changes, prices may get affected in the database, so new pricing should be added to the end, or prices should be saved in String format
@Getter
public enum Prices {
    BASIC_PRICE(3),
    PREMIUM_PRICE(4),
    BONUS_PRICE(25); // points

    private final int price;

    Prices(int price) {
        this.price = price;
    }


}
