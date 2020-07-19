package com.capstone.booking.service.impl;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Coupon;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Autowired
    public StripeService() {
        Stripe.apiKey = "sk_test_51Gs1CYGtpdysubsW6yWTpHbdN5SZmte1xK1stIvSOIbZKhjabrbj0aCnwOSuHIhrSeVMMHbwg82sg7MUDTcykVFF00quh18j1z";
    }

    // check if stripe token is legal
    public Charge chargeNewCard(String token, int amount) throws Exception {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }


}