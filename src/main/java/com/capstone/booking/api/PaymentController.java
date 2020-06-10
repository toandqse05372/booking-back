package com.capstone.booking.api;

import com.capstone.booking.common.StripeToken;
import com.capstone.booking.service.impl.StripeService;
import com.stripe.model.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.Objects;

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
    @RequestMapping(value = "/charge", method = RequestMethod.POST)
    public ResponseEntity<?> chargeCard(@RequestBody StripeToken token) throws Exception {
   //     Double amount = Double.parseDouble(request.getParameter("amount"));
        stripeService.chargeNewCard(token.getToken(), 120);
        return ResponseEntity.ok().body("ok");
    }

}
