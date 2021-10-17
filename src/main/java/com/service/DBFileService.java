package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.entity.DBFile;
import com.repository.DBFileRepository;

@Service
public class DBFileService {

	@Autowired 
	private DBFileRepository dbFileRepo;
	
	public DBFile storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if(fileName.contains("..")) {
	            throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
	        }
			DBFile DBFile = new DBFile(fileName, file.getContentType(), file.getBytes());
			return dbFileRepo.save(DBFile);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public DBFile getFile(int id) {
		Optional<DBFile> op = dbFileRepo.findById(id);
		return op.orElseThrow(RuntimeException::new);
	}
	
}
