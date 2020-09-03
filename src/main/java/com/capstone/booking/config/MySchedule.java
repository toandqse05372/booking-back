package com.capstone.booking.config;

import com.capstone.booking.common.key.MonoStatus;
import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.*;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.impl.EmailSenderService;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
public class MySchedule {

    @Value("${spring.mail.username}")
    private String fromMail;

    @Value("${frontend.host}")
    private String hostFrontEnd;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    OrderTokenRepository orderTokenRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    RemainingRepository remainingRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PaymentIntentRepository paymentIntentRepository;

    @Autowired
    VisitorTypeRepository visitorTypeRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    //delete unused data at 00:00 every day
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void deleteUnUsed() {
        List<Token> tokenExpired = tokenRepository.findByTokenExpDateBefore(new Date());
        tokenRepository.deleteAll(tokenExpired);
        List<Remaining> remainings = remainingRepository.findByRedemptionDateBefore(new Date());
        remainingRepository.deleteAll(remainings);
        List<Order> orders = orderRepository.findByStatus(OrderStatus.EXPIRED.toString());
        for (Order order : orders) {
            if (orderRepository.countTicket(order.getId()) == 0) {
                orderItemRepository.deleteAllByOrder(order);
                orderRepository.delete(order);
            }
        }
    }

    //set expired order at 00:00 every day
    @Scheduled(cron = "0 5 0 * * ?")
    public void setExpiredOrder() throws StripeException {
        List<Order> orderExpired = orderRepository.findAllByRedemptionDateBeforeAndStatus(new Date(), OrderStatus.EXPIRED.toString());
        List<Order> updateOrders = new ArrayList<>();
        for (Order order : orderExpired) {
            if (order.getStatus().equals(OrderStatus.PAID.toString())) {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(order.getMail()); //user email
                mailMessage.setSubject("Order expired");
                mailMessage.setFrom(fromMail);
                mailMessage.setText("Xin loi don hang #" + order.getOrderCode()
                        + " cua ban chua duoc gui tra kip thoi do loi cua chung toi. " +
                        "So tien cua ban se duoc hoan tra ngay lap tuc");
                emailSenderService.sendEmail(mailMessage);
                //refund
                PaymentIntent paymentIntent = paymentIntentRepository.findByOid(order.getId());
                if(paymentIntent != null){
                    Refund.create(RefundCreateParams.builder()
                            .setCharge(paymentIntent.getPiId())
                            .build());
                }
            }
            order.setStatus(OrderStatus.EXPIRED.toString());
            updateOrders.add(order);
        }
        orderRepository.saveAll(updateOrders);
    }

    //make unpaid order be expired if after 2 hours user not paid for it every 5 minutes
    @Scheduled(cron = "0 0/5 * * * ?")
    public void expiredOrderToken() {
        List<OrderToken> orderTokens = orderTokenRepository.findExpOrderToken(new Date());
        for (OrderToken token : orderTokens) {
            Optional<Order> orderOptional = orderRepository.findById(token.getOrderId());
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                order.setStatus(OrderStatus.EXPIRED.toString());
                orderRepository.save(order);
                for (OrderItem orderItem : orderItemRepository.findAllByOrder(order)) {
                    Remaining remaining = remainingRepository.findByRedemptionDateAndVisitorTypeId(
                            returnToMidnight(order.getRedemptionDate()), orderItem.getVisitorType().getId());
                    if (remaining != null) {
                        remaining.setTotal(remaining.getTotal() + orderItem.getQuantity());
                        remainingRepository.save(remaining);
                    }
                }
            }
        }
        orderTokenRepository.deleteAll(orderTokens);
    }

    //update total remaining code every 5 minutes
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateRemainingCode() {
        List<Remaining> remainings = remainingRepository.findAll();
        for (Remaining remaining : remainings) {
            remaining.setTotal(codeRepository.countByVisitorTypeIdAndDate(remaining.getVisitorTypeId(), remaining.getRedemptionDate())
                    - orderItemRepository.findTotalBookedTicket(remaining.getVisitorTypeId(), remaining.getRedemptionDate()));
        }
        remainingRepository.saveAll(remainings);
    }

    //return date to midnight
    private Date returnToMidnight(Date redemptionDate) {
        Instant inst = redemptionDate.toInstant();
        LocalDate localDate = inst.atZone(ZoneId.systemDefault()).toLocalDate();
        Instant dayInst = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date day = Date.from(dayInst);
        TimeZone tz = TimeZone.getDefault();
        day = new Date(day.getTime() + tz.getRawOffset());
        return day;
    }

}
