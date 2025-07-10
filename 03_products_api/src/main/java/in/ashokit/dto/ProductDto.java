package in.ashokit.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ProductDto {

	private Long productId;
	private String name;
	private String description;
	private String title;
	private BigDecimal unitPrice;
	private String imageUrl;
	private boolean active;
	private int unitsInStock;
	private LocalDate dateCreated;
	private LocalDate lastUpdated;

}
