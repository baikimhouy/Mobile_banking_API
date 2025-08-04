package org.example.mobilebankingapi.service;

import org.example.mobilebankingapi.DTO.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    MediaResponse uploadSingle(MultipartFile file);
    List<MediaResponse> uploadMultiple(MultipartFile[] files);
    ResponseEntity<Resource> downloadByName(String fileName);
    ResponseEntity<?> deleteByName(String fileName);
}