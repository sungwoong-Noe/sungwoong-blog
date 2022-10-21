package com.swnoe.blog.app.controller.imgUpload;

import com.swnoe.blog.app.service.imgUpload.ImgUploadService;
import com.swnoe.blog.dto.request.img.UploadImgRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class UploadImgController {

    private final ImgUploadService imgUploadService;

    @PostMapping("/post/uploadImg")
    public String imgUpload(@ModelAttribute UploadImgRequest imgRequest){
        String url = imgUploadService.storeImg(imgRequest.getImg());
        log.info("url = {}", url);
        return url;
    }

    @PostMapping("/post/test")
    public String test(){
        return "test입니다.";
    }
}
