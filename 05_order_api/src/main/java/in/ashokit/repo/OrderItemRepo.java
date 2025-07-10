package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Address;
import in.ashokit.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

}
