package com.swnoe.blog.domain.imgUpload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ImageFile {

    private String originalFileName;
    private String storedFileName;
    private MultipartFile multipartFile;

    static public ImageFile from(MultipartFile multipartFile) {
        return new ImageFile(multipartFile.getOriginalFilename(),
                createStoreFileName(multipartFile.getOriginalFilename()),
                multipartFile
        );
    }

    private static String createStoreFileName(String originalFileName){
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private static String extractExt(String originalFileName){
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

}
