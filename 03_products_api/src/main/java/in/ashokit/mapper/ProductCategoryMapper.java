package in.ashokit.mapper;

import org.modelmapper.ModelMapper;

import in.ashokit.dto.ProductCategoryDto;
import in.ashokit.entity.ProductCategory;

public class ProductCategoryMapper {

	public static final ModelMapper mapper = new ModelMapper();

	public static ProductCategoryDto convertToDto(ProductCategory categoryEntity) {
		return mapper.map(categoryEntity, ProductCategoryDto.class);
	}

	public static ProductCategory covertToEntity(ProductCategoryDto categoryDto) {
		return mapper.map(categoryDto, ProductCategory.class);
	}

}
