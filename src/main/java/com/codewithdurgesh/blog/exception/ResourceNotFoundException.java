package com.codewithdurgesh.blog.exception;

import lombok.Getter;
import lombok.Setter;
@SuppressWarnings("serial")
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	
	String resourceName;
	String fieldName;
	Long fieldValue;
	public ResourceNotFoundException(String resourceName, String fieldName,long fieldValue)
	{
		super(String.format("% not found with %s :%s",resourceName,fieldName,fieldValue ));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	

}
