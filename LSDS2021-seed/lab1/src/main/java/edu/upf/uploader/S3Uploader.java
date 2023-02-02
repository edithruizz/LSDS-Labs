package edu.upf.uploader;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.util.List;

public class S3Uploader implements Uploader {
    private final String bucket;
    private final String prefix;
    private final String profilename;
    
    
    public S3Uploader(String bucket, String prefix, String profilename){
        this.bucket = bucket;
        this.prefix = prefix;
        this.profilename = profilename;
    }


    @Override
    public void upload(List<String> files) {
        try {
            ProfileCredentialsProvider profile = new ProfileCredentialsProvider(profilename);
    
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                     .withCredentials(profile)
                     .withRegion("us-east-1")
                     .build();
    
            for (String file : files) {
                PutObjectRequest request = new PutObjectRequest(bucket, prefix, new File(file));
                s3Client.putObject(request);
            }
    
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }
}

