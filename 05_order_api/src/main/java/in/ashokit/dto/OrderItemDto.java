package in.ashokit.dto;

import lombok.Data;

@Data
public class OrderItemDto {

	private Integer itemId;
	private String itemName;
	private Double unitPrice;
	private String imageUrl;
	private Integer quantity;
}
