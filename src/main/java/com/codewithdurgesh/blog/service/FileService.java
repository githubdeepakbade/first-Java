package com.codewithdurgesh.blog.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemNotFoundException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	String uploadImage(String path,MultipartFile file)throws IOException;
	InputStream getReswource(String path,String fileName)throws FileSystemNotFoundException;

}
