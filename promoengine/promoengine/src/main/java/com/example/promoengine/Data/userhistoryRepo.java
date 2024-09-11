package com.example.promoengine.Data;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface userhistoryRepo extends CrudRepository<userHistory, Integer> {

    @Query("SELECT b.name FROM bundle b WHERE b.bid = (SELECT uh.next_bid FROM userHistory uh WHERE uh.next_bid = :next_bid AND uh.status = 'SUCCESSFUL' LIMIT 1)")
    String findBundleNameIfSuccessful(@Param("next_bid") int next_bid);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM userHistory WHERE msisdn = :msisdn )")
    boolean existsByMsisdn(@Param("msisdn") String msisdn);

    @Query("SELECT EXISTS (SELECT 1 FROM userHistory WHERE msisdn = :msisdn AND status = 'SUCCESSFUL')")
    boolean existsByMsisdnAndStatusNot(@Param("msisdn") String msisdn);


    @Modifying
    @Query("UPDATE userHistory SET status = 'CANCELLED' WHERE msisdn = :msisdn")
    void updateBymsisdn(@Param("msisdn") String msisdn);

}
