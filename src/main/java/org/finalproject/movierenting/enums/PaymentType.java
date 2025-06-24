package org.finalproject.movierenting.enums;

import lombok.Getter;

@Getter
public enum PaymentType {
    CARD("Card"),

    BONUS("Bonus");

    private final String type;

    PaymentType(String paymentType) {
        this.type = paymentType;
    }
}
