package in.ashokit.dto;

import lombok.Data;

@Data
public class PurchseOrderResponseDto {

	private String razorpayOrderId;

	private String orderStatus;

	private String orderTrackingNumber;

}
