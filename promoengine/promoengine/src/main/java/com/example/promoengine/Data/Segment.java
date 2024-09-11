package com.example.promoengine.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "segment")
public record Segment(
        @Id
        int bid,
        int seg,
        int min_seg
) {
}
