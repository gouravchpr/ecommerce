package in.ashokit.service;

import in.ashokit.dto.CustomerDto;
import in.ashokit.dto.ResetPwdDto;

public interface CustomerService {

	public boolean register(CustomerDto customerDto);

	public CustomerDto login(String email, String pwd);

	public boolean resetPwd(ResetPwdDto pwdDto);

	public CustomerDto getCustomer(String email);

}
