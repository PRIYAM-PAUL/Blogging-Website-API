package com.website.blogging.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.website.blogging.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException{
		String originalFilename = file.getOriginalFilename();
		String randomUUID = UUID.randomUUID().toString();
		System.out.println(randomUUID);
		System.out.println(originalFilename);
		System.out.println(originalFilename.lastIndexOf("."));
		System.out.println(originalFilename.substring(originalFilename.lastIndexOf(".")));
		String fileName = randomUUID.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
		System.out.println(fileName);
		String filePath =path+File.separator+fileName;
		System.out.println(filePath);
		File f = new File(path);
		if(!f.exists()) {
			//make file directory
			f.mkdir();
		}
		Files.copy(file.getInputStream(),Paths.get(filePath));
		return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String orginalPath =path+File.separator+fileName;
		InputStream is = new FileInputStream(orginalPath);
		
		return is;
	}

}
