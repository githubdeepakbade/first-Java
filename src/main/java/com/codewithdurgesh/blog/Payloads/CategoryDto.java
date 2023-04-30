package com.codewithdurgesh.blog.Payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
	private Integer categoryId;
	@NotBlank
	@Size(min = 4,message = "min sis of category 4")
	private String categoryTitle;
	@NotBlank
	@Size(min = 10,message = "min size of category10")
	private String categoryDescription;
	

	

}
