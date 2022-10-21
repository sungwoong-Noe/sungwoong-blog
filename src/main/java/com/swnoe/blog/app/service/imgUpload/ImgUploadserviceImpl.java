package com.swnoe.blog.app.service.imgUpload;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.swnoe.blog.domain.imgUpload.ImageFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * S3 이미지 업로드 전략 구현체
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ImgUploadserviceImpl implements ImgUploadService{

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String storeImg(MultipartFile multipartFile) {
        validateFile(multipartFile);
        ImageFile imageFile = ImageFile.from(multipartFile);
        return uploadFile(imageFile);
    }

    /**
     * 요청된 이미지 파일 검증 메서드
     * @param multipartFile
     */
    private void validateFile(MultipartFile multipartFile){
        if(multipartFile.isEmpty()){
            throw new IllegalArgumentException("이미지가 존재하지 않습니다.");
        }
    }


    private String uploadFile(ImageFile imageFile){
        MultipartFile file = imageFile.getMultipartFile();
        ObjectMetadata metadata = createObjectMetadata(file);
        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, imageFile.getStoredFileName(), inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }catch (IOException e){
            throw new IllegalArgumentException("파일 업로드에 실패했습니다.");
        }
        return amazonS3.getUrl(bucket, imageFile.getStoredFileName()).toString();
    }


    /**
     * s3 api를 위한 dto 생송 매서드
     * @param file 업로드 요청된 이미지 파일
     * @return s3 api 요구 스펙 dto
     */
    private ObjectMetadata createObjectMetadata(MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        return metadata;
    }
}
