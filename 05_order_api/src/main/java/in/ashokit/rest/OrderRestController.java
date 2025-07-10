package in.ashokit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.dto.OrderDto;
import in.ashokit.dto.PurchaseOrderRequestDto;
import in.ashokit.dto.PurchseOrderResponseDto;
import in.ashokit.response.ApiResponse;
import in.ashokit.service.OrderService;

@RestController
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/order")
	public ResponseEntity<ApiResponse<PurchseOrderResponseDto>> createOrder(
			@RequestBody PurchaseOrderRequestDto orderReq) throws Exception {

		ApiResponse<PurchseOrderResponseDto> response = new ApiResponse<>();

		PurchseOrderResponseDto orderRespDto = orderService.createOrder(orderReq);

		response.setStatus(201);
		response.setMessage("Order Placed Successfully");
		response.setData(orderRespDto);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/order")
	public ResponseEntity<ApiResponse<PurchseOrderResponseDto>> updateOrder(@RequestBody OrderDto orderDto)
			throws Exception {

		ApiResponse<PurchseOrderResponseDto> response = new ApiResponse<>();

		PurchseOrderResponseDto orderRespDto = orderService.updateOrder(orderDto);

		response.setStatus(200);
		response.setMessage("Order Updated Successfully");
		response.setData(orderRespDto);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/orders/{email}")
	public ResponseEntity<ApiResponse<List<OrderDto>>> getOrders(@PathVariable String email) throws Exception {

		ApiResponse<List<OrderDto>> response = new ApiResponse<>();

		List<OrderDto> ordersList = orderService.getAllOrderByEmail(email);

		response.setStatus(200);
		response.setMessage("Fetched Orders Successfully");
		response.setData(ordersList);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
