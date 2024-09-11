package com.example.promoengine.Data;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "userhistory")
public record userHistory(

        @Id Integer id,
        String msisdn ,
        int previousBid,
        int nextBid,
        String status ,
        String gift ,
        String newBundle
) {
}
