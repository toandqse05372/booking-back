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

    private String API_SECRET_KEY = "sk_test_51Gs1h2GNT0lWowJdLbUe9goCVT6aFdritt01C51BGzVFJo4ZMLcxXr1eQYUcAqyW3F4YR0Y9lvapjNZmefFajzh300L3NlgfLQ";

    @Autowired
    public StripeService() {
        Stripe.apiKey = API_SECRET_KEY;
    }

    public Charge chargeNewCard(String token, double amount) throws Exception {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }


}