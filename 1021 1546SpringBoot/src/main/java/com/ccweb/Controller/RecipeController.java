package com.ccweb.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/v1/recipie")
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


    @RequestMapping(method = RequestMethod.POST,consumes = APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/{id}",method = RequestMethod.GET,consumes = APPLICATION_JSON_VALUE)
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

    @RequestMapping(method = RequestMethod.GET,consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Collection<JSONObject>> registerGet() {
//        if() return null;// log in or not
        //get user id after log in
        Collection<Recipe> recipes = recipeService.getAllRecipe();


        return new ResponseEntity<>((Collection<JSONObject>)JSON.toJSON(recipes),HttpStatus.OK);
        //recipeDao.delete(recipe);

        /// GET API recipeId -->get recipe //
        //DELETE API
    }

    @RequestMapping(method = RequestMethod.PUT,consumes = APPLICATION_JSON_VALUE)
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


    @RequestMapping(method = RequestMethod.DELETE,consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteRecipe(@PathVariable String  recipeId) {
//        if() return null;// log in or not
        //get user id after log in
        Recipe recipe=recipeService.getRecipeById(recipeId);
        String response = "Delete recipe successfully!";
        recipeService.delete(recipe);
        return new ResponseEntity<>(response, HttpStatus.OK);
        //recipeDao.delete(recipe);

        /// GET API recipeId -->get recipe //
        //DELETE API
    }

    @RequestMapping(value="/uploadFile",method = RequestMethod.POST)
//    @PostMapping(value = "/uploadFile")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }




    @RequestMapping(value = "/{id}/image",method = RequestMethod.POST,consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addImage(@PathVariable("id") String  recipeId,@RequestBody Image image) {

        imageService.addImage(image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{recipieId}/image/{imageId}", method = RequestMethod.DELETE)
    public ResponseEntity<AccountResponse> deleteImage(@PathVariable("recipieId") String recipeId){//}，@PathVariable("imageId") String  recipeId){

        imageService.deleteImage(recipeId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{recipieId}/image/{imageId}", method = RequestMethod.GET)
    public ResponseEntity<AccountResponse> getImageById(@PathVariable("recipieId") String recipeId){
        imageService.getImageById(recipeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
