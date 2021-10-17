package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//With no Entity insertion/ Using standard IO library and insert data to db by 
// setting String name which contains a path URI
@Service
public class FileCopyService {
	
	public void upload(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {
		Path upload = Paths.get("directory");
		if(!Files.exists(upload)) {
			try {
				Files.createDirectories(upload);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = upload.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }   
		
	}
}
