package com.swnoe.blog.app.service.imgUpload;

import org.springframework.web.multipart.MultipartFile;

public interface ImgUploadService {
    String storeImg(MultipartFile img);
}
