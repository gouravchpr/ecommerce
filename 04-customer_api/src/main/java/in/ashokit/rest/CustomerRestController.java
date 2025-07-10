package in.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.dto.CustomerDto;
import in.ashokit.dto.ResetPwdDto;
import in.ashokit.response.ApiResponse;
import in.ashokit.service.CustomerService;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<String>> register(@RequestBody CustomerDto customerDto) {
		ApiResponse<String> response = new ApiResponse<>();

		CustomerDto customer = customerService.getCustomer(customerDto.getEmail());
		if (customer != null) {
			response.setData("Duplicate Email");
			response.setStatus(400);
			response.setMessage("Duplicate Registration Not ALlowed");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		boolean register = customerService.register(customerDto);
		if (register) {
			response.setData("Registration Success");
			response.setStatus(200);
			response.setMessage("OK");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setData("Registration Failed");
			response.setStatus(500);
			response.setMessage("Error");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<CustomerDto>> login(@RequestBody CustomerDto customerDto) {
		ApiResponse<CustomerDto> response = new ApiResponse<>();

		CustomerDto dto = customerService.login(customerDto.getEmail(), customerDto.getPassword());

		if (dto != null) {
			response.setData(dto);
			response.setStatus(200);
			response.setMessage("OK");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setData(null);
			response.setStatus(500);
			response.setMessage("Invalid Credentials");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/reset-pwd")
	public ResponseEntity<ApiResponse<String>> resetpwd(@RequestBody ResetPwdDto pwdDto) {
		ApiResponse<String> response = new ApiResponse<>();
		boolean status = customerService.resetPwd(pwdDto);
		if (status) {
			response.setData("Reset Pwd success");
			response.setStatus(200);
			response.setMessage("OK");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setData("Reset Pwd Failed");
			response.setStatus(500);
			response.setMessage("Error");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer/{email}")
	public ResponseEntity<ApiResponse<CustomerDto>> getCustomer(@PathVariable String email) {
		ApiResponse<CustomerDto> response = new ApiResponse<>();
		CustomerDto customer = customerService.getCustomer(email);
		if (customer != null) {
			response.setData(customer);
			response.setStatus(200);
			response.setMessage("Fetched Customer Sucessfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setData(null);
			response.setStatus(500);
			response.setMessage("Fetching Customer Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
