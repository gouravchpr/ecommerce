package in.ashokit.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderDto {

	private Integer orderId;

	private String orderTrackingNum;

	private String razorPayOrderId;

	private String razorPayPaymentId;

	private String email;

	private String orderStatus;

	private Double totalPrice;

	private Integer totalQuantity;

	private LocalDate deliveryDate;

}
