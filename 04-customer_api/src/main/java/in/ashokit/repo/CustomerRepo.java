package in.ashokit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

	public Customer findByEmailAndPwd(String email, String pwd);

	public Optional<Customer> findByEmail(String email);

}
