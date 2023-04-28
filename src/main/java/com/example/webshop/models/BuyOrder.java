package com.example.webshop.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class BuyOrder {

    private long customerId;
    private ArrayList<Long> itemIds;

    public BuyOrder(long customerId, ArrayList<Long> itemIds) {
        this.customerId = customerId;
        this.itemIds = itemIds;
    }
}
