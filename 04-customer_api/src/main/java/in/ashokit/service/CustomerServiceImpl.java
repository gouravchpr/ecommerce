package in.ashokit.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.dto.CustomerDto;
import in.ashokit.dto.ResetPwdDto;
import in.ashokit.entity.Customer;
import in.ashokit.mapper.CustomerMapper;
import in.ashokit.repo.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private EmailService emailService;

	private Random random = new Random();

	@Override
	public boolean register(CustomerDto customerDto) {

		Customer customer = CustomerMapper.convertToEntity(customerDto);

		String pwd = generatePwd();
		customer.setPassword(pwd);
		customer.setPwdUpdated("NO");

		Customer save = customerRepo.save(customer);

		if (save.getCustomerId() != null) {
			String subject = "Your Registration Success @ Ashok IT";
			String body = "Your Temporary Pwd : " + pwd;
			return emailService.sendEmail(customerDto.getEmail(), subject, body);
		} else {
			return false;
		}
	}

	@Override
	public CustomerDto login(String email, String pwd) {

		Customer customer = customerRepo.findByEmailAndPwd(email, pwd);

		if (customer != null) {
			return CustomerMapper.convertToDto(customer);
		}

		return null;
	}

	@Override
	public boolean resetPwd(ResetPwdDto pwdDto) {

		Optional<Customer> byEmail = customerRepo.findByEmail(pwdDto.getEmail());

		if (byEmail.isPresent()) {
			Customer customer = byEmail.get();
			customer.setPassword(pwdDto.getNewPwd());
			customer.setPwdUpdated("YES");
			customerRepo.save(customer);
			return true;
		}
		return false;
	}

	@Override
	public CustomerDto getCustomer(String email) {
		return customerRepo.findByEmail(email)
						   .map(CustomerMapper::convertToDto)
						   .orElse(null);
	}

	private String generatePwd() {
		StringBuilder builder = new StringBuilder();
		int pwdLength = 5;
		String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		for (int i = 0; i < pwdLength; i++) {
			int index = random.nextInt(charPool.length());
			char charAt = charPool.charAt(index);
			builder.append(charAt);
		}
		return builder.toString();
	}

}
