package com.bill.bill_payment_service.commons.enumeration;

import lombok.Getter;

@Getter
public enum ProductType {
    TV("Cable TV", true, 0),

    ELECTRICITY("Electricity", true, 1),

    INTERNET("Internet", false, 2),

    EDUCATION("Education", true, 3),

    INSURANCE("Insurance", true, 4),

    BET("Sport Betting", true, 5),

    AIRTIME("Airtime", true, 6),

    DATA("Data", true, 7),

    EVENT("EVENT", false, 8);

    private final String name;


    private final boolean display;

    private final int order;


    ProductType(String name, boolean display, int order) {
        this.name = name;
        this.display = display;
        this.order = order;
    }
}
