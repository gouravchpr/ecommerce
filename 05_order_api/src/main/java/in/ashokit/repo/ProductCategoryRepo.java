package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.ProductCategory;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long>{

}
