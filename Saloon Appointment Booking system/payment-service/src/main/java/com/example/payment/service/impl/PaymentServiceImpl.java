package com.example.payment.service.impl;

import com.example.payment.domain.PaymentMethod;
import com.example.payment.domain.PaymentOrderStatus;
import com.example.payment.model.PaymentOrder;
import com.example.payment.payload.dto.BookingDTO;
import com.example.payment.payload.dto.UserDTO;
import com.example.payment.payload.response.PaymentLinkResponse;
import com.example.payment.repository.PaymentOrderRepository;
import com.example.payment.service.PaymentService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


    private final PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String razorpayApiSecretKey;



    @Override
    public PaymentLinkResponse createOrder(
                                            UserDTO user,
                                           BookingDTO booking,
                                           PaymentMethod paymentMethod) throws RazorpayException, StripeException {


    Long amount = (long) booking.getTotalPrice();


    PaymentOrder order = new PaymentOrder();
    order.setAmount(amount);
    order.setPaymentMethod(paymentMethod);
    order.setBookingId(booking.getId());
    order.setSalonId(booking.getSalonId());
    PaymentOrder saveOrder = paymentOrderRepository.save(order);

    PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();

    if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
        PaymentLink paymentLink = createRazorPayPaymentLink(
                user,
                saveOrder.getAmount(),
                saveOrder.getId()
        );
        String paymentUrl = paymentLink.get("short_url");
        String paymentUrlId = paymentLink.get("id");
        paymentLinkResponse.setPayment_link_url(paymentUrl);
        paymentLinkResponse.setGetPayment_link_id(paymentUrlId);

        saveOrder.setPaymentLinkId(paymentUrlId);

        paymentOrderRepository.save(saveOrder);

    } else {
        String paymentUrl = createStripePaymentLink(
                user,
                saveOrder.getAmount(),
                saveOrder.getId()
        );


        paymentLinkResponse.setPayment_link_url(paymentUrl);
//        paymentLinkResponse.setGetPayment_link_id(paymentUrlId);

//        saveOrder.setPaymentLinkId(paymentUrlId);

        paymentOrderRepository.save(saveOrder);

    }
        return paymentLinkResponse;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {

        PaymentOrder paymentOrder = paymentOrderRepository.findById(id).orElse(null);
        if(paymentOrder == null){
            throw  new Exception("payment order not found");
        }
        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {


        return paymentOrderRepository.findByPaymentLinkId(paymentId);
    }

    @Override
    public PaymentLink createRazorPayPaymentLink(UserDTO user, Long Amount, Long orderId) throws RazorpayException {

        Long amount = Amount*100;

        RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey, razorpayApiSecretKey);
        JSONObject paymentLinkRequest = new JSONObject();

        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency","NPR");

        JSONObject customer = new JSONObject();
        customer.put("name",user.getFullName());
        customer.put("email",user.getEmail());

        paymentLinkRequest.put("customer",customer);

        JSONObject notify = new JSONObject();
        notify.put("email",true);

        paymentLinkRequest.put("notify",notify);

        paymentLinkRequest.put("reminder_enable",true);


        paymentLinkRequest.put("callback_url","http://localhost:3000/payment-success/" + orderId);

        paymentLinkRequest.put("callback_method","get");

        return razorpayClient.paymentLink.create(paymentLinkRequest);


    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success"+orderId)
                .setCancelUrl("http://localhost:3000/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder()
                                        .setName("salon appointment booking ")
                                        .build()
                                ).build()
                        ).build()
                ).build()
                ;

        Session session = Session.create(params);


        return session.getUrl();
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException {
      if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
          if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
              RazorpayClient razorpay = new RazorpayClient(razorpayApiKey,razorpayApiSecretKey);

              Payment payment = razorpay.payments.fetch(paymentId);
              Integer amount = payment.get("amount");
              String status = payment.get("status");

              if(status.equals("captured")){
//                  produce kafka event
                  paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                  paymentOrderRepository.save(paymentOrder);
                  return true;
              }
              return false;
          }else{
              paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
              paymentOrderRepository.save(paymentOrder);
              return  true;
          }
      }
      return  false;
    }


}
