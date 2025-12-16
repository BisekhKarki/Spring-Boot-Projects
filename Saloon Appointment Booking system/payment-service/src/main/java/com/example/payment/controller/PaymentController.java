package com.example.payment.controller;

import com.example.payment.domain.PaymentMethod;
import com.example.payment.model.PaymentOrder;
import com.example.payment.payload.dto.BookingDTO;
import com.example.payment.payload.dto.UserDTO;
import com.example.payment.payload.response.PaymentLinkResponse;
import com.example.payment.service.PaymentService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDTO booking,
            @RequestParam PaymentMethod paymentMethod
            ) throws StripeException, RazorpayException {
        UserDTO user = new UserDTO();
        user.setFullName("Bisekh");
        user.setEmail("bisekh@gmal.com");
        user.setId(1L);


        PaymentLinkResponse res = paymentService.createOrder(
                user,
                booking,
                paymentMethod
        );
        return  ResponseEntity.ok(res);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
           @PathVariable Long paymentOrderId
    ) throws Exception {



        PaymentOrder res = paymentService.getPaymentOrderById(paymentOrderId);
        return  ResponseEntity.ok(res);
    }

    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPaymentOrder(
           @RequestParam String paymentId,
           @RequestParam String paymentLinkId
    ) throws Exception {

        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        Boolean res = paymentService.proceedPayment(paymentOrder,
                paymentId,
                paymentLinkId);

        return  ResponseEntity.ok(res);
    }





}
