package com.bg.BoardGame.controller;

import com.bg.BoardGame.dto.checkout.CheckoutItemDto;
import com.bg.BoardGame.dto.checkout.StripeResponse;
import com.bg.BoardGame.service.AuthenticationService;
import com.bg.BoardGame.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

//@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
@PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
@RestController
@RequestMapping("/user")
public class OrderController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private OrderService orderService;

    //stripe session checkout api

    @PostMapping("/order/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList)
    throws StripeException {
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }

}
