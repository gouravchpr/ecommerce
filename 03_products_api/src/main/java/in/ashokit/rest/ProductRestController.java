package in.ashokit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.dto.ProductCategoryDto;
import in.ashokit.dto.ProductDto;
import in.ashokit.response.ApiResponse;
import in.ashokit.service.ProductService;

@RestController
@CrossOrigin
public class ProductRestController {

	@Autowired
	private ProductService productService;

	@GetMapping("/categories")
	public ResponseEntity<ApiResponse<List<ProductCategoryDto>>> productCategories() {

		ApiResponse<List<ProductCategoryDto>> response = new ApiResponse<>();
		List<ProductCategoryDto> allCategories = productService.findAllCategories();

		if (!allCategories.isEmpty()) {
			response.setData(allCategories);
			response.setMessage("Fetched All Categories");
			response.setStatus(200);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setData(null);
			response.setMessage("Failed to fetch categories");
			response.setStatus(500);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/products/{categoryId}")
	public ResponseEntity<ApiResponse<List<ProductDto>>> productsByCategory(@PathVariable Long categoryId) {

		ApiResponse<List<ProductDto>> response = new ApiResponse<>();

		List<ProductDto> products = productService.findProductByCategoryId(categoryId);

		if (!products.isEmpty()) {
			response.setData(products);
			response.setMessage("Fetched Products");
			response.setStatus(200);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setData(null);
			response.setMessage("Failed to Fetch Products");
			response.setStatus(500);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/productsByName/{name}")
	public ResponseEntity<ApiResponse<List<ProductDto>>> productsByName(@PathVariable String name) {

		ApiResponse<List<ProductDto>> response = new ApiResponse<>();

		List<ProductDto> products = productService.findByProductName(name);

		if (!products.isEmpty()) {
			response.setData(products);
			response.setMessage("Fetched Products");
			response.setStatus(200);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setData(null);
			response.setMessage("Failed to Fetch Products");
			response.setStatus(500);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<ApiResponse<ProductDto>> product(@PathVariable Long productId) {

		ApiResponse<ProductDto> response = new ApiResponse<>();

		ProductDto productDto = productService.findByProductId(productId);

		if (productDto != null) {
			response.setData(productDto);
			response.setMessage("Fetched Product");
			response.setStatus(200);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setData(null);
			response.setMessage("Failed to Fetch Product");
			response.setStatus(500);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
