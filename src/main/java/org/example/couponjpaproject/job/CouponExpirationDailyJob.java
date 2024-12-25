package org.example.couponjpaproject.job;


import org.example.couponjpaproject.beans.Coupon;
import org.example.couponjpaproject.repositories.CouponRepository;
import org.springframework.stereotype.Component;


import java.sql.Date;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

@Component
public class CouponExpirationDailyJob implements Runnable {

    private CouponRepository cupRep;
    private boolean quit;

    public CouponExpirationDailyJob(CouponRepository cupRep) {
        this.cupRep = cupRep;
        this.quit = false;
    }

    @Override
    public void run() {
        try {
            while (!quit) {
                ArrayList<Coupon> allCoupons = (ArrayList<Coupon>) cupRep.findAll();
                Date cur = new Date(System.currentTimeMillis());
                for (int i = allCoupons.size() - 1; i >= 0; i--) {
                    if (!(allCoupons.get(i).getEndDate().toLocalDate().equals(cur.toLocalDate()))) {
                        if (allCoupons.get(i).getEndDate().before(cur)) {
                            int couponId = allCoupons.get(i).getId();
                            cupRep.deleteCouponAssociation(allCoupons.get(i).getId());
                            cupRep.deleteById(allCoupons.get(i).getId());
                            if (!cupRep.existsById(couponId)) {
                                System.out.println("Coupon Id: " + couponId + " has been deleted.");
                            }
                            allCoupons.remove(i);
                        }
                    }
                }
                sleep(200);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

    public void stop() {
        try {
            sleep(1000);
            quit = true;
            System.out.println("************************************************************");
            sleep(400);
            System.out.print("Shutting off");
            for (int i = 0; i < 8; i++) {
                sleep(400);
                System.out.print(".");

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}

