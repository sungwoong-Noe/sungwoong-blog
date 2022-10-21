package com.swnoe.blog.dto.request.img;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadImgRequest {

    private MultipartFile img;
}
