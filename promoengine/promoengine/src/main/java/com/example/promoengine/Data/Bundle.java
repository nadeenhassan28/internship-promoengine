package com.example.promoengine.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "bundle")
public record Bundle(
        @Id
        int bId,
      String name
) {
}
