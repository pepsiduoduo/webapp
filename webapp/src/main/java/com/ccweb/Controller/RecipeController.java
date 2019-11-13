package com.ccweb.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ccweb.Entity.Account;

import com.ccweb.Entity.AccountResponse;
import com.ccweb.Entity.Image;
import com.ccweb.Entity.Recipe;
import com.ccweb.Service.AccountService;
import com.ccweb.Service.AmazonClient;
import com.ccweb.Service.ImageService;

import com.ccweb.Service.RecipeService;
import com.ccweb.util.EmailValidationUtil;
import com.ccweb.util.PasswordUtilImpl;
import com.google.gson.Gson;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import static org.springframework.http.MediaType.*;

@RestController
//@RequestMapping("/v1/recipie")
public class RecipeController {

    @Autowired
    private AccountService accountService;

    @Autowired
    EmailValidationUtil emailValidationUtil;

    @Autowired
    private RecipeService recipeService;


    @Autowired
    private ImageService imageService;

    @Autowired
    PasswordUtilImpl passwordUtil;

    private AmazonClient amazonClient;

    RecipeController() {
        this.amazonClient = new AmazonClient();
    }


    @RequestMapping(value = "/v1/recipie", method = RequestMethod.POST,consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> registerPost(@RequestBody String recipe) {

        Recipe recipeObj = new Gson().fromJson(recipe, Recipe.class);
        Account account = accountService.currentUser;

        recipeObj.setId(UUID.randomUUID().toString());
        recipeObj.setCreated_ts(new Date().toString());
        recipeObj.setUpdated_ts(new Date().toString());
        recipeObj.setAuthor_id(account.getId());
        recipeObj.setTotal_time_in_min(recipeObj.getCook_time_in_min()+recipeObj.getPrep_time_in_min());



        recipeService.addRecipe(recipeObj);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/recipie/{id}",method = RequestMethod.GET,consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<JSONObject> registerGet(@PathVariable("id") String  recipeId) {
//        if() return null;// log in or not
        //get user id after log in
        Recipe recipe = recipeService.getRecipeById(recipeId);

        return new ResponseEntity<>((JSONObject)JSON.toJSON(recipe),HttpStatus.OK);
        //recipeDao.delete(recipe);

        /// GET API recipeId -->get recipe //
        //DELETE API
    }

    @RequestMapping(value="/v1/recipie/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteRecipeById(@PathVariable("id") String recipeId){
        boolean isSuccess = false;
        if(recipeId == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            Recipe recipe = recipeService.getRecipeById(recipeId);
            if(recipe == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            isSuccess = recipeService.deleteRecipeById(recipeId);

            if(isSuccess)
                return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }




    }
//
//    @RequestMapping(method = RequestMethod.GET,consumes = APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public ResponseEntity<Collection<JSONObject>> registerGet() {
////        if() return null;// log in or not
//        //get user id after log in
//        Collection<Recipe> recipes = recipeService.getAllRecipe();
//
//
//        return new ResponseEntity<>((Collection<JSONObject>)JSON.toJSON(recipes),HttpStatus.OK);
//        //recipeDao.delete(recipe);
//
//        /// GET API recipeId -->get recipe //
//        //DELETE API
//    }

    @RequestMapping(value="/v1/recipie/{id}",method = RequestMethod.PUT,consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<JSONObject> updateRecipe(@PathVariable String  recipeId,@RequestBody Recipe infos) {
//        if() return null;// log in or not
        //get user id after log in
        Recipe recipe = recipeService.getRecipeById(recipeId);
        recipe.setTotal_time_in_min(infos.getCook_time_in_min());


        recipeService.updateRecipe(recipe);
        return new ResponseEntity<>((JSONObject)JSON.toJSON(recipe), HttpStatus.OK);
        //recipeDao.delete(recipe);

        /// GET API recipeId -->get recipe //
        //DELETE API
    }




    @RequestMapping(value="/v1/recipie/uploadFile",method = RequestMethod.POST)
//    @PostMapping(value = "/uploadFile")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        String url = this.amazonClient.uploadFile(file);

        return this.amazonClient.uploadFile(file);
    }

    @DeleteMapping(value="/v1/recipie/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }




    //@RequestMapping(value = "/v1/recipie/{id}/image",method = RequestMethod.POST, consumes = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, "image/jpg"})
    @RequestMapping(value = "/v1/recipie/{id}/image",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addImage(@PathVariable("id") String  recipeId,@RequestParam(value = "file") MultipartFile file) throws IOException {


        InputStream myInputStream = new ByteArrayInputStream(file.getBytes());
        if (!isImage(myInputStream)) {
            System.out.println("not image");
            return null;
        }

        String url = this.amazonClient.uploadFile(file);
        ObjectMetadata datas=this.amazonClient.getMetaData();
        System.out.println("m5:"+datas.getETag()+datas.getContentType()+datas.getContentLength()+datas.getLastModified());
        String oldUrl = this.imageService.getUrlByImageId(recipeId);
        Image img = new Image(recipeId, url);
        if(oldUrl == null){
            this.imageService.addImage(img);
        }
        else{
            String oldImg = imageService.getUrlByImageId(recipeId);
            this.amazonClient.deleteFileFromS3Bucket(oldImg);

            this.imageService.updateImage(img);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }
    public static boolean isImage(InputStream file)
    {
        try {
            return ImageIO.read(file) != null;
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping(value = "/v1/recipie/{recipieId}/image/{imageId}", method = RequestMethod.DELETE)
    public ResponseEntity<AccountResponse> deleteImage(@PathVariable("recipieId") String recipeId, @PathVariable("imageId") String imageId){
        String imageUrl = imageService.getUrlByImageId(imageId);
        this.amazonClient.deleteFileFromS3Bucket(imageUrl);

        imageService.deleteImage(imageId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/recipie/{recipieId}/image/{imageId}", method = RequestMethod.GET)
    public ResponseEntity<?> getImageById(@PathVariable("recipieId") String recipeId, @PathVariable("imageId") String imageId){
        String url = imageService.getUrlByImageId(recipeId);
        return new ResponseEntity<>(url,HttpStatus.OK);
    }

}
