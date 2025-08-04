package org.example.mobilebankingapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.mobilebankingapi.DTO.MediaResponse;
import org.example.mobilebankingapi.service.MediaService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping("/single")
    public MediaResponse uploadSingle(@RequestPart MultipartFile file) {
        return mediaService.uploadSingle(file);
    }

    @PostMapping("/multiple")
    public List<MediaResponse> uploadMultiple(@RequestPart MultipartFile[] files) {
        return mediaService.uploadMultiple(files);
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadByName(@PathVariable String fileName) {
        return mediaService.downloadByName(fileName);
    }

    @DeleteMapping("/{fileName:.+}")
    public ResponseEntity<?> deleteByName(@PathVariable String fileName) {
        return mediaService.deleteByName(fileName);
    }
}