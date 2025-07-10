package in.ashokit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.dto.ProductCategoryDto;
import in.ashokit.dto.ProductDto;
import in.ashokit.mapper.ProductCategoryMapper;
import in.ashokit.mapper.ProductMapper;
import in.ashokit.repo.ProductCategoryRepo;
import in.ashokit.repo.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductCategoryRepo categoryRepo;

	@Autowired
	private ProductRepo productRepo;

	@Override
	public List<ProductCategoryDto> findAllCategories() {
		return categoryRepo.findAll()
						   .stream()
					       .map(ProductCategoryMapper::convertToDto)
					       .collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> findProductByCategoryId(Long categoryId) {
		return productRepo.findByCategoryCategoryId(categoryId)
				   		  .stream()
				   		  .map(ProductMapper::convertToDto)
				   		  .collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> findByProductName(String productName) {
		return productRepo.findByNameContaining(productName)
				   		  .stream()
				   		  .map(ProductMapper::convertToDto)
				   		  .collect(Collectors.toList());
	}

	@Override
	public ProductDto findByProductId(Long productId) {
		return productRepo.findById(productId)
						  .map(ProductMapper::convertToDto)
						  .orElse(null);
	}

}
