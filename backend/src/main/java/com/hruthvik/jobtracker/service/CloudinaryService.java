package com.hruthvik.jobtracker.service;

import java.io.File;
import com.cloudinary.Cloudinary;
import com.cloudinary.api.exceptions.ApiException;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(
            @Value("${application.cloudinary.cloud-name}") String cloudName,
            @Value("${application.cloudinary.api-key}") String apiKey,
            @Value("${application.cloudinary.api-secret}") String apiSecret
    ) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    public Map upload(MultipartFile file, String folder) throws IOException {
        File tempFile = File.createTempFile("resume-", ".pdf");

        try {
            file.transferTo(tempFile);

            return cloudinary.uploader().upload(
                    tempFile,
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "raw",
                            "use_filename", true,
                            "unique_filename", true,
                            "overwrite", false
                    )
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("REAL ERROR: " + e.getMessage());

        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    public void delete(String publicId) throws IOException {
        cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.asMap("resource_type", "raw")
        );
    }
}