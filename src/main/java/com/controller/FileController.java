package com.controller;

 
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;

import com.entity.DBFile;
import com.fileResponse.UploadFileResponse;
import com.service.DBFileService;

@RestController
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private DBFileService dbFileService;
	 
		@PostMapping("/uploadFile")
	    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
	        DBFile dbFile = dbFileService.storeFile(file);

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/downloadFile/")
	                .path(String.valueOf((char)dbFile.getId()))
	                .toUriString();

	        return new UploadFileResponse(dbFile.getName(), fileDownloadUri,
	                file.getContentType(), file.getSize());
	    }

	    @PostMapping("/uploadMultipleFiles")
	    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
	        return Arrays.asList(files).stream().map(file -> uploadFile(file))
	        		.collect(Collectors.toList());
	    }

	    @GetMapping("/downloadFile/{fileId}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable int fileId) {
	        // Load file from database
	        DBFile dbFile = dbFileService.getFile(fileId);

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(dbFile.getType()))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getName() + "\"")
	                .body(new ByteArrayResource(dbFile.getData()));
	    }
	
}
