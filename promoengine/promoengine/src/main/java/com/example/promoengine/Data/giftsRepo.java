package com.example.promoengine.Data;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface giftsRepo extends CrudRepository<Gifts, Integer> {
    @Query("SELECT name FROM gifts  WHERE gid = :gid")
    String findGiftNameById(@Param("gid") int gid);
}
