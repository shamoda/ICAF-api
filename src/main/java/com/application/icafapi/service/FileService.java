package com.application.icafapi.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.utils.IoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.application.icafapi.common.util.AWSUtil.getS3Client;
import static com.application.icafapi.common.util.constant.AWS.*;

@Log4j2
@Service
public class FileService {

    public boolean uploadFile(MultipartFile mFile, String fileName, String type) {
        File file = convertMultipartFileToFile(mFile);
        if (type.equals("paper")) {
            getS3Client().putObject(PutObjectRequest.builder().bucket(PAPER_BUCKET).key(fileName).build(), RequestBody.fromFile(file));
        } else if (type.equals("presentation")) {
            getS3Client().putObject(PutObjectRequest.builder().bucket(PRESENTATION_BUCKET).key(fileName).build(), RequestBody.fromFile(file));
        } else if (type.equals("proposal")){
            getS3Client().putObject(PutObjectRequest.builder().bucket(PROPOSAL_BUCKET).key(fileName).build(), RequestBody.fromFile(file));
        } else {
            file.delete();
            log.info("Invalid file type");
            return false;
        }
        file.delete();
        return true;
    }

    public byte[] downloadFile(String fileName, String type) {
        GetObjectRequest getObjectRequest = null;
        if (type.equals("paper")) {
            getObjectRequest = GetObjectRequest.builder().bucket(PAPER_BUCKET).key(fileName).build();
        } else if (type.equals("presentation")) {
            getObjectRequest = GetObjectRequest.builder().bucket(PRESENTATION_BUCKET).key(fileName).build();
        } else if (type.equals("proposal")) {
            getObjectRequest = GetObjectRequest.builder().bucket(PROPOSAL_BUCKET).key(fileName).build();
        } else if (type.equals("template")) {
            getObjectRequest = GetObjectRequest.builder().bucket(TEMPLATE_BUCKET).key(fileName).build();
        } else {
            log.info("Invalid file type");
            return null;
        }
        ResponseInputStream inputStream = getS3Client().getObject(getObjectRequest);
        try {
            byte[] content = IoUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            log.error("Error while converting to ByteArray: ", e);
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName, String type) {
        DeleteObjectRequest deleteObjectRequest = null;
        if (type.equals("paper")) {
            deleteObjectRequest = DeleteObjectRequest.builder().bucket(PAPER_BUCKET).key(fileName).build();
        } else if (type.equals("presentation")) {
            deleteObjectRequest = DeleteObjectRequest.builder().bucket(PRESENTATION_BUCKET).key(fileName).build();
        } else if (type.equals("proposal")) {
            deleteObjectRequest = DeleteObjectRequest.builder().bucket(PROPOSAL_BUCKET).key(fileName).build();
        } else {
            log.info("Invalid file type");
            return null;
        }
        DeleteObjectResponse deleteObjectResponse = getS3Client().deleteObject(deleteObjectRequest);
        return fileName + " deleted";
    }

    public List<String> getFileNames(String type) {
        List<String> fileNames = new ArrayList<>();
        List<S3Object> s3Objects = new ArrayList<>();
        ListObjectsV2Request request = null;
        if (type.equals("paper")) {
            request = ListObjectsV2Request.builder().bucket(PAPER_BUCKET).build();
        } else if (type.equals("presentation")) {
            request = ListObjectsV2Request.builder().bucket(PRESENTATION_BUCKET).build();
        } else if (type.equals("proposal")) {
            request = ListObjectsV2Request.builder().bucket(PROPOSAL_BUCKET).build();
        } else {
            log.info("Invalid file type");
            return null;
        }
        ListObjectsV2Response result = getS3Client().listObjectsV2(request);
        s3Objects = result.contents();
        for (S3Object object : s3Objects) {
            fileNames.add(object.key());
        }
        return fileNames;
    }

    private File convertMultipartFileToFile(MultipartFile mFile) {
        File convertedFile = new File(mFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(mFile.getBytes());
        } catch (IOException e) {
            log.error("Error converting MultipartFile to File: ", e);
        }
        return convertedFile;
    }

}
