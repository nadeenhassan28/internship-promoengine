package com.example.promoengine.API;

import com.example.promoengine.Data.userHistory;
import com.example.promoengine.Service.userhistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

@RestController




public class Controller {


    private final userhistoryService userhistoryService;

    public Controller(com.example.promoengine.Service.userhistoryService userhistoryService) {
        this.userhistoryService = userhistoryService;
    }

    @GetMapping("/upgrade-bundle")
    public ResponseEntity<String> upgradeBundle(@RequestParam("msisdn") String msisdn, @RequestParam("pId")int previous_bid,
                                                @RequestParam("nId") int next_bid, SessionStatus sessionStatus) {
        userHistory uh = new userHistory(null,msisdn,previous_bid,next_bid,null,null,null);
        ResponseEntity<String> response = null;
        int result = userhistoryService.checkBundle(msisdn,previous_bid, next_bid);
        switch (result) {
            case 1:
               response= ResponseEntity.ok("Anghami Prize redeemed successfully upgraded from Basic to Silver");
               break;
            case 2:
                response= ResponseEntity.ok("Anghami & Shahid Prizes redeemed successfully upgraded from Basic to Gold");
                break;
            case 3:
                response=ResponseEntity.ok("Anghami & Shahid & Osn+ redeemed successfully upgraded from Basic to Platinum");
                break;
            case 4:
                response=ResponseEntity.ok("Shahid prize redeemed successfully upgraded from Silver to Gold");
                break;
            case 5:
                response=ResponseEntity.ok("Shahid & Osn+ prizes redeemed successfully upgraded from Silver to Platinum");
                break;
            case 6:
                response=ResponseEntity.ok("Osn+ prize redeemed successfully upgraded from Gold to Platinum");
                break;
            case 7:
                response=ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot Upgrade Bundle");
                break;
            case 8:
                response=ResponseEntity.status(HttpStatus.CONFLICT).body("Max Bundle Reached");
                break;
            case 9:
                response=ResponseEntity.status(HttpStatus.CONFLICT).body("Bundle Cancelled");
                break;
        }
        userhistoryService.checkAndUpdateIfPreviousGreaterThanNext(msisdn, previous_bid, next_bid);
        userhistoryService.saveUserHistory(uh);
          return response;

    }



}
