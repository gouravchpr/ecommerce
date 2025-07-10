package in.ashokit.dto;

import lombok.Data;

@Data
public class AddressDto {

	private Long addrId;
	private String street;
	private String houseNum;
	private String city;
	private String state;
	private String zipCode;
	private String country;

}
