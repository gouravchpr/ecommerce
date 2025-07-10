package in.ashokit.service;

import java.util.List;

import in.ashokit.dto.ProductCategoryDto;
import in.ashokit.dto.ProductDto;

public interface ProductService {

	public List<ProductCategoryDto> findAllCategories();

	public List<ProductDto> findProductByCategoryId(Long categoryId);

	public List<ProductDto> findByProductName(String productName);

	public ProductDto findByProductId(Long productId);

}
