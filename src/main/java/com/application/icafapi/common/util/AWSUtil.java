package com.application.icafapi.common.util;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import static com.application.icafapi.common.constant.AWS.REGION;

@Service
public class AWSUtil {
    private static S3Client s3Client = null;

    public static S3Client getS3Client() {
        if (s3Client == null) {
            s3Client = S3Client.builder().region(REGION).build();
        }
        return s3Client;
    }

    private AWSUtil() {}
}
