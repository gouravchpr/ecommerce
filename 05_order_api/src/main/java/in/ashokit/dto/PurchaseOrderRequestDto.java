package in.ashokit.dto;

import java.util.List;

import lombok.Data;

@Data
public class PurchaseOrderRequestDto {

	private CustomerDto customer;

	private AddressDto address;

	private OrderDto order;

	private List<OrderItemDto> orderItems;

}
