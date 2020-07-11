package com.capstone.booking.api;

import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.service.impl.StripeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaymentController {
    // Reading the value from the application.properties file
    @Autowired
    private StripeService stripeService;

//    @RequestMapping("/payment")
//    public ResponseEntity<?> home(Model model) {
//        model.addAttribute("amount", 50 * 100); // In cents
//        model.addAttribute("stripePublicKey", stripePublicKey);
//        return ResponseEntity.ok().body("ok");
//    }
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public ResponseEntity<?> payForOrder(@RequestPart(value = "order") String orderRequest,
                                        @RequestPart(value = "token") String stripetoken) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        OrderDTO order = mapper.readValue(orderRequest, OrderDTO.class);
        stripeService.chargeNewCard(stripetoken, order.getTotalPayment());
        return ResponseEntity.ok().body("ok");
    }

}
