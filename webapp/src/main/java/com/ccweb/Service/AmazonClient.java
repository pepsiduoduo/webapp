package com.ccweb.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonClient {

    private static final String endpointUrl = "https://s3.us-east-1.amazonaws.com";
//    private static final String accessKey = "AKIAQLNXKZZS22C77H4R";
//    private static final String secretKey = "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";
//    private static final String bucketName = "webapp-cui.best.tld";


    private static final String accessKey = "AKIA5DOX37WM6IHEPF37";
    private static final String secretKey = "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";
    private static final String bucketName = "webapp-dezhiyang.xyz.tld";




    private static String fileName="";

    public AmazonS3 s3client;


    public AmazonClient(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        //this.s3client = new AmazonS3Client(credentials);
        this.s3client = AmazonS3ClientBuilder.standard().withRegion("us-east-1").withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }


    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return fileUrl;
    }
    public ObjectMetadata getMetaData()
    {
        try{
            GetObjectMetadataRequest request=new GetObjectMetadataRequest(bucketName,fileName);
            return s3client.getObjectMetadata(request);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }

}
