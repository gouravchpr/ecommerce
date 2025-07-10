package in.ashokit.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import in.ashokit.dto.AddressDto;
import in.ashokit.dto.CustomerDto;
import in.ashokit.dto.OrderDto;
import in.ashokit.dto.OrderItemDto;
import in.ashokit.dto.PurchaseOrderRequestDto;
import in.ashokit.dto.PurchseOrderResponseDto;
import in.ashokit.entity.Address;
import in.ashokit.entity.Customer;
import in.ashokit.entity.OrderItem;
import in.ashokit.repo.AddressRepo;
import in.ashokit.repo.CustomerRepo;
import in.ashokit.repo.OrderItemRepo;
import in.ashokit.repo.OrderRepo;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private OrderItemRepo orderItemRepo;

	private RazorpayClient client;

	@Value("${razorpay.key.id}")
	private String keyId;

	@Value("${razorpay.key.secret}")
	private String keySecret;

	@Override
	public PurchseOrderResponseDto createOrder(PurchaseOrderRequestDto orderRequest) throws Exception {

		// create razorpay order

		JSONObject rpayOrderReq = new JSONObject();

		rpayOrderReq.put("amount", orderRequest.getOrder().getTotalPrice() * 100); // amt in paisa
		rpayOrderReq.put("currency", "INR");
		rpayOrderReq.put("receipt", orderRequest.getCustomer().getEmail());

		this.client = new RazorpayClient(keyId, keySecret);
		Order razorpayOrderCreated = client.Orders.create(rpayOrderReq);

		CustomerDto customerDto = orderRequest.getCustomer();
		Optional<Customer> customerRecord = customerRepo.findByEmail(customerDto.getEmail());
		Customer c = new Customer();
		if (customerRecord.isEmpty()) {
			// save customer
			BeanUtils.copyProperties(customerDto, c);
			c = customerRepo.save(c);
		} else {
			c = customerRecord.get();
		}

		// save addr into db

		AddressDto addressDto = orderRequest.getAddress();
		Address addr = new Address();
		addr.setCustomer(c);
		BeanUtils.copyProperties(addressDto, addr);
		addressRepo.save(addr);

		// Save order to database

		OrderDto orderDto = orderRequest.getOrder();
		String orderTrackingNum = generateOrderTrackingId();

		in.ashokit.entity.Order order = new in.ashokit.entity.Order();
		order.setOrderTrackingNum(orderTrackingNum);
		order.setRazorPayOrderId(razorpayOrderCreated.get("id"));
		order.setOrderStatus(razorpayOrderCreated.get("status"));
		order.setTotalPrice(orderDto.getTotalPrice());
		order.setTotalQuantity(orderDto.getTotalQuantity());
		order.setEmail(c.getEmail());
		order.setCustomer(c);
		order.setAddress(addr);
		in.ashokit.entity.Order savedOrder = orderRepo.save(order);

		// save order items
		List<OrderItemDto> orderItemDtos = orderRequest.getOrderItems();

		for (OrderItemDto itemDto : orderItemDtos) {
			OrderItem orderItem = new OrderItem();
			BeanUtils.copyProperties(itemDto, orderItem);
			orderItem.setOrder(savedOrder);
			orderItemRepo.save(orderItem);
		}

		// Create and return the OrderResponse

		PurchseOrderResponseDto responseDto = new PurchseOrderResponseDto();
		responseDto.setRazorpayOrderId(razorpayOrderCreated.get("id"));
		responseDto.setOrderStatus(razorpayOrderCreated.get("status"));
		responseDto.setOrderTrackingNumber(orderTrackingNum);

		return responseDto;
	}

	@Override
	public PurchseOrderResponseDto updateOrder(OrderDto orderDto) {

		PurchseOrderResponseDto responseDto = new PurchseOrderResponseDto();

		Optional<in.ashokit.entity.Order> byId = orderRepo.findById(orderDto.getOrderId());
		if (byId.isPresent()) {
			in.ashokit.entity.Order order = byId.get();
			order.setOrderStatus(orderDto.getOrderStatus());
			orderRepo.save(order);
			responseDto.setRazorpayOrderId(order.getRazorPayOrderId());
			responseDto.setOrderStatus(order.getOrderStatus());
			responseDto.setOrderTrackingNumber(order.getOrderTrackingNum());
		}

		return responseDto;
	}

	@Override
	public List<OrderDto> getAllOrderByEmail(String email) {

		List<OrderDto> ordersList = new ArrayList<>();

		List<in.ashokit.entity.Order> entities = orderRepo.findByEmail(email);

		for (in.ashokit.entity.Order entity : entities) {
			OrderDto orderDto = new OrderDto();
			BeanUtils.copyProperties(entity, orderDto);
			ordersList.add(orderDto);
		}

		return ordersList;
	}

	private String generateOrderTrackingId() {
		// Get the current timestamp
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());

		// Generate a random UUID for uniqueness
		String randomUUID = UUID.randomUUID().toString().substring(0, 5).toUpperCase();

		// Combine timestamp and UUID to form the tracking ID
		return "OD_" + timestamp + "_" + randomUUID;
	}

}
