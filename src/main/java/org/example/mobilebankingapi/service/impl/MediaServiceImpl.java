package org.example.mobilebankingapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mobilebankingapi.DTO.MediaResponse;
import org.example.mobilebankingapi.domain.Media;
import org.example.mobilebankingapi.repository.MediaRepository;
import org.example.mobilebankingapi.service.MediaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    @Override
    public MediaResponse uploadSingle(MultipartFile file) {
        String name = UUID.randomUUID().toString();
        String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        String fileName = String.format("%s.%s", name, extension);

        Path path = Paths.get(serverPath + fileName);

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Media upload failed");
        }

        Media media = new Media();
        media.setName(name);
        media.setExtension(extension);
        media.setMimeTypeFile(file.getContentType());
        media.setIsDeleted(false);

        media = mediaRepository.save(media);

        return MediaResponse.builder()
                .name(fileName)
                .mimeTypeFile(media.getMimeTypeFile())
                .size(file.getSize())
                .uri(baseUri + fileName)
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(MultipartFile[] files) {
        List<MediaResponse> responses = new ArrayList<>();
        for (MultipartFile file : files) {
            responses.add(uploadSingle(file));
        }
        return responses;
    }

    @Override
    public ResponseEntity<Resource> downloadByName(String fileName) {
        try {
            Path filePath = Paths.get(serverPath).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }

            // Determine content type
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File URL is malformed");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while accessing file");
        }
    }

    @Override
    public ResponseEntity<?> deleteByName(String fileName) {
        try {
            // Extract name and extension from fileName
            int lastDotIndex = fileName.lastIndexOf('.');
            if (lastDotIndex == -1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file name");
            }

            String name = fileName.substring(0, lastDotIndex);
            String extension = fileName.substring(lastDotIndex + 1);

            // Delete from database
            Media media = mediaRepository.findByNameAndExtension(name, extension)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found in database"));

            media.setIsDeleted(true);
            mediaRepository.save(media);

            // Delete from file system
            Path filePath = Paths.get(serverPath + fileName);
            Files.deleteIfExists(filePath);

            return ResponseEntity.ok().body("File deleted successfully");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while deleting file");
        }
    }

    private String getFileExtension(String originalFilename) {
        int lastIndex = originalFilename.lastIndexOf('.');
        if (lastIndex == -1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File has no extension");
        }
        return originalFilename.substring(lastIndex + 1);
    }
}