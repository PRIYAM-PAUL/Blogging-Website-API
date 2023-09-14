package com.website.blogging.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {

	
	private int id;
	@NotEmpty
	@Size(min=3,message="Name should be Greater than 3")
	private String name;
	@Email
	private String email;
	@NotEmpty
	@Size(min=5,max=20,message="Password should be greater than 5 and less than 20")
	private String password;
	@NotEmpty
	private String about;
}
