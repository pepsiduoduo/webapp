
package com.ccweb.Controller;

import com.alibaba.fastjson.JSON;
import com.ccweb.Entity.Account;
import com.ccweb.Entity.AccountResponse;
import com.ccweb.Entity.Image;
import com.ccweb.Service.AccountService;
import com.ccweb.Service.AmazonClient;
import com.ccweb.Service.ImageService;
import com.ccweb.util.EmailValidationUtil;
import com.ccweb.util.PasswordUtilImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/v1/recipe")
public class ImageController {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailValidationUtil emailValidationUtil;

    @Autowired
    private ImageService imageService;

    @Autowired
    PasswordUtilImpl passwordUtil;

    private AmazonClient amazonClient;

    ImageController() {
        this.amazonClient = new AmazonClient();
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }




    @RequestMapping(value = "/{id}/image",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addImage(@PathVariable("id") String  recipeId,@RequestBody Image image) {

        imageService.addImage(image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//
//    public ResponseEntity<AccountResponse> deleteImage(@PathVariable("recipieId") String recipeId){//}，@PathVariable("imageId") String  recipeId){
//
//        imageService.deleteImage(recipeId);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/{recipieId}/image/{imageId}", method = RequestMethod.GET)
//    public ResponseEntity<AccountResponse> getImageById(@PathVariable("recipieId") String recipeId){
//        imageService.getImageById(recipeId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
