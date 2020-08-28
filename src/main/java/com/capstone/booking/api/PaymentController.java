package com.capstone.booking.api;

import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.service.OrderService;
import com.capstone.booking.service.impl.StripeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//payment api
@Controller
public class PaymentController {

    @Autowired
    private StripeService stripeService;

    @Autowired
    private OrderService orderService;

    //check payment information and save order
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('PAYMENT')")
    public ResponseEntity<?> payForOrder(@RequestPart(value = "order") String orderRequest,
                                         @RequestPart(value = "token") String stripetoken,
                                         @RequestPart(value = "action") String action) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        OrderDTO order = mapper.readValue(orderRequest, OrderDTO.class);
        String paymentIntentId = stripeService.chargeNewCard(stripetoken, order.getTotalPayment());
        if(action.equals("new")){
            orderService.create(order, OrderStatus.PAID, paymentIntentId);
        }else if (action.equals("existed")){
            orderService.update(order, OrderStatus.PAID, paymentIntentId);
        }
        return ResponseEntity.ok().body("ok");
    }

}
