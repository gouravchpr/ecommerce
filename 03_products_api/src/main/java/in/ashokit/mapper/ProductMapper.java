package in.ashokit.mapper;

import org.modelmapper.ModelMapper;

import in.ashokit.dto.ProductDto;
import in.ashokit.entity.Product;

public class ProductMapper {

	public static final ModelMapper mapper = new ModelMapper();

	public static ProductDto convertToDto(Product productEntity) {
		return mapper.map(productEntity, ProductDto.class);
	}

	public static Product convertToEntity(ProductDto productDto) {
		return mapper.map(productDto, Product.class);
	}

}
