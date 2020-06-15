package com.capstone.booking.api;

import com.capstone.booking.common.StripeToken;
import com.capstone.booking.entity.dto.PaymentDTO;
import com.capstone.booking.service.impl.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaymentController {
    // Reading the value from the application.properties file
    private String stripePublicKey = "pk_test_51Gs1h2GNT0lWowJdNu2saBCmJ2BzwKowJQuTGkUmxcFxHHY6L80y8GAQhAsEUV30KWLeEn2zXxRspwDJEHdIgMKv00ZCZWEaKE";

    @Autowired
    private StripeService stripeService;

    @RequestMapping("/payment")
    public ResponseEntity<?> home(Model model) {
        model.addAttribute("amount", 50 * 100); // In cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        return ResponseEntity.ok().body("ok");
    }
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public ResponseEntity<?> chargeCard(@RequestBody PaymentDTO paymentRequest) throws Exception {
   //     Double amount = Double.parseDouble(request.getParameter("amount"));
        stripeService.chargeNewCard(paymentRequest.getStripeToken(), paymentRequest.getTotalPayment());
        return ResponseEntity.ok().body("ok");
    }

}
