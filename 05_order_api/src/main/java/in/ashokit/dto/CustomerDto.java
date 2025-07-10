package in.ashokit.dto;

import lombok.Data;

@Data
public class CustomerDto {
	
	private Long customerId;
	private String name;
	private String email;
	private String password;
	private String pwdUpdated;
	private Long phno;

}
