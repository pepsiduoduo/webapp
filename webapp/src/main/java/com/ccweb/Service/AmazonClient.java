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
import com.ccweb.util.ParseKey;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonClient {

    private static final String endpointUrl = "https://s3.us-east-1.amazonaws.com";
//    private static final String bucketName = "webapp-cui.best.tld";
    private static final String bucketName = "webapp-dezhiyang.xyz.tld";




    private static String fileName="";

    public AmazonS3 s3client;
    public ParseKey keyGenerator;


    public AmazonClient(){
        this.keyGenerator = new ParseKey();
        AWSCredentials credentials = new BasicAWSCredentials(this.getAccessKey(), this.getSecretKey());
        //this.s3client = new AmazonS3Client(credentials);

        this.s3client = AmazonS3ClientBuilder.standard().withRegion("us-east-1").withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }

    public String getAccessKey(){

        return this.keyGenerator.readAccessKey();
    }

    public String getSecretKey(){
        return this.keyGenerator.readSecretKey();
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
