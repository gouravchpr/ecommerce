package in.ashokit.service;

import java.util.List;

import in.ashokit.dto.OrderDto;
import in.ashokit.dto.PurchaseOrderRequestDto;
import in.ashokit.dto.PurchseOrderResponseDto;

public interface OrderService {

	public PurchseOrderResponseDto createOrder(PurchaseOrderRequestDto orderRequest) throws Exception;

	public PurchseOrderResponseDto updateOrder(OrderDto orderDto);

	public List<OrderDto> getAllOrderByEmail(String email);
}
