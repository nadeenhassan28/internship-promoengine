package com.example.promoengine.Data;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "gifts")
public record Gifts(
        @Id
        int gId,
        String name
) {
}
