package com.example.promoengine.Service;
import com.example.promoengine.Data.userHistory;
import com.example.promoengine.Data.userhistoryRepo;
import com.example.promoengine.Data.giftsRepo;
import com.example.promoengine.Data.segmentRepo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class userhistoryService {

    private final giftsRepo giftsRepo;
    private final segmentRepo segmentRepo;
    private final userhistoryRepo userhistoryRepo;
    @Autowired
    public userhistoryService(com.example.promoengine.Data.segmentRepo segmentRepo, userhistoryRepo userhistoryRepo,com.example.promoengine.Data.giftsRepo giftsRepo) {
        this.segmentRepo = segmentRepo;
        this.userhistoryRepo = userhistoryRepo;
        this.giftsRepo= giftsRepo;
    }



       public int checkBundle(String msisdn,int previous_bid, int next_bid) {
           // Retrieve the next_seg where nextBid matches bid in segments
           Integer minSeg = segmentRepo.findMinSegByBid(previous_bid);

           if (minSeg == null) {
               throw new IllegalArgumentException("Next segment not found");
           }
           //law mawgood mayenfa3sh diff upgrade
           if(previous_bid<next_bid) {
               boolean exist= userhistoryRepo.existsByMsisdnAndStatusNot(msisdn);
               if(exist)
                   return 7;
               else{
               if (previous_bid == 1) {
                   //1 to 2
                   if (next_bid == minSeg)
                       return 1;
                   //1 to 3
                   if (next_bid == minSeg + 1)
                       return 2;
                   //1 to 4
                   if (next_bid == minSeg + 2)
                       return 3;
                   //3 to 4
               } else if (previous_bid == 2) {
                   //from 2 to 3
                   if (next_bid == minSeg)
                       return 4;
                   //from 2 to 4
                   if (next_bid == minSeg + 1)
                       return 5;

               } else if (previous_bid == 3) {
                   //3 to 4
                   if (next_bid == minSeg)
                       return 6;
                   //limit bundle reached
               }
             }
           }//cancel bundle
           if (previous_bid > next_bid) {
               checkAndUpdateIfPreviousGreaterThanNext(msisdn, previous_bid, next_bid);
               return 9;}
           // Compare next_seg with nextBid using nextSeg and return appropriate value
            else if (next_bid>4) {
               return 8;

           }
           return 7; //default
       }


    public void checkAndUpdateIfPreviousGreaterThanNext(String msisdn, int previousBid, int nextBid) {
        boolean exists = userhistoryRepo.existsByMsisdn(msisdn);
            if(exists) {
                userhistoryRepo.updateBymsisdn(msisdn);
            }



    }



   public void saveUserHistory(userHistory userHistory) {
       int previousBid = userHistory.previousBid();
       int nextBid = userHistory.nextBid();
       String msisdn=userHistory.msisdn();
       int difference = nextBid - previousBid;
       String giftName = null;


       StringBuilder giftNamesBuilder = new StringBuilder();
       String allGiftNames = giftNamesBuilder.toString();

       if (difference > 1) {
           // Loop through the range and get gift names
           for (int i = 0; i < difference; i++) {
               int currentId = previousBid + i;
               giftName = giftsRepo.findGiftNameById(currentId);

               if (giftName != null) {
                   if (!giftNamesBuilder.isEmpty()) {
                       giftNamesBuilder.append(", ");
                   }
                   giftNamesBuilder.append(giftName);
               } else {
                   if (!giftNamesBuilder.isEmpty()) {
                       giftNamesBuilder.append(", "); // Separate with comma if there are existing names
                   }
                   giftNamesBuilder.append("Unknown Gift"); // Or any default value
               }

           }
           allGiftNames = giftNamesBuilder.toString();
       }
       if(difference ==1 ) {
           allGiftNames = giftsRepo.findGiftNameById(previousBid);
       }





       // Calculate the result using checkBundle
       int result = checkBundle(msisdn,previousBid, nextBid);

       // Determine the status based on the result
       String status;
       String newBundle = null;
       if (result == 7 || result == 8) {
           status = "FAILED";
           newBundle="Same as Previous";
       }
       else {
           status = "SUCCESSFUL";
       }


       newBundle=userhistoryRepo.findBundleNameIfSuccessful(nextBid);

          userhistoryRepo.save(new userHistory(null, userHistory.msisdn(), userHistory.previousBid(), userHistory.nextBid(),status,allGiftNames,newBundle));



   }



}
