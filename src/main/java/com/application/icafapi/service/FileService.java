package com.application.icafapi.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.utils.IoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.application.icafapi.common.util.AWSUtil.getS3Client;
import static com.application.icafapi.common.util.constant.AWS.*;

@Log4j2
@Service
public class FileService {

    public boolean uploadFile(MultipartFile mFile, String fileName, String type) {
        String extension = FilenameUtils.getExtension(mFile.getOriginalFilename());
        File file = convertMultipartFileToFile(mFile);
        if (type.equals("paper")) {
            PutObjectResponse putObjectResponse = getS3Client().putObject(PutObjectRequest.builder().bucket(PAPER_BUCKET).key(fileName + "." + extension).build(), RequestBody.fromFile(file));
        } else if (type.equals("presentation")) {
            PutObjectResponse putObjectResponse = getS3Client().putObject(PutObjectRequest.builder().bucket(PRESENTATION_BUCKET).key(fileName + "." + extension).build(), RequestBody.fromFile(file));
        } else if (type.equals("proposal")){
            PutObjectResponse putObjectResponse = getS3Client().putObject(PutObjectRequest.builder().bucket(PROPOSAL_BUCKET).key(fileName + "." + extension).build(), RequestBody.fromFile(file));
        } else {
            file.delete();
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
        } else {
            return null;
        }
        ResponseInputStream inputStream = getS3Client().getObject(getObjectRequest);
        try {
            byte[] content = IoUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
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
            return null;
        }
        DeleteObjectResponse deleteObjectResponse = getS3Client().deleteObject(deleteObjectRequest);
        return fileName + " deleted";
    }

    private File convertMultipartFileToFile(MultipartFile mFile) {
        File convertedFile = new File(mFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(mFile.getBytes());
        } catch (IOException e) {
            log.error("Error converting MultipartFile to File", e);
        }
        return convertedFile;
    }

}
