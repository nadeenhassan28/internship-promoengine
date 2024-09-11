package com.example.promoengine.Data;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface segmentRepo extends CrudRepository<Segment, Integer> {

    @Query("SELECT min_seg FROM segment  WHERE bid = :previous_bid")
    Integer findMinSegByBid(@Param("previous_bid") int previous_bid);


}
