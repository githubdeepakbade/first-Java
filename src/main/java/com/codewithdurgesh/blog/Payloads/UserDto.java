package com.codewithdurgesh.blog.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	@NotEmpty
	@Size(min =4,message = "Username must be min of 4 characters")
	private String name;
	
	@Email(message = "Email addrss is no valid!!")
	private String email;
	
	@NotEmpty
	@Size(min = 3,max = 10,message ="passoword must be min of 3 chars and mas of 10 char" )
	private String password;
	
	@NotEmpty
	private String about;

}
