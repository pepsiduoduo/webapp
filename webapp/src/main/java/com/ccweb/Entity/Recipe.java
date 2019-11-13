package com.ccweb.Entity;

import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name="recipe")
public class Recipe {
    private String id;
    private String created_ts;
    private String updated_ts;
    private String author_id;
    private int cook_time_in_min;
    private int prep_time_in_min;
    private int total_time_in_min;
    private String title;
    private String cusine;
    private int servings;

    private Image image;
    private List<String> ingredients;

    private List<Steps> steps = new ArrayList<>();

    private NutritionInformation nutrition_information = new NutritionInformation();

    public Recipe(String id, String created_ts, String updated_ts, String author_id, int cook_time_in_min, int prep_time_in_min, int total_time_in_min, String title, String cusine, int servings) {
        this.id = id;
        this.created_ts = created_ts;
        this.updated_ts = updated_ts;
        this.author_id = author_id;
        this.cook_time_in_min = cook_time_in_min;
        this.prep_time_in_min = prep_time_in_min;
        this.total_time_in_min = total_time_in_min;
        this.title = title;
        this.cusine = cusine;
        this.servings = servings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_ts() {
        return created_ts;
    }

    public void setCreated_ts(String created_ts) {
        this.created_ts = created_ts;
    }

    public String getUpdated_ts() {
        return updated_ts;
    }

    public void setUpdated_ts(String updated_ts) {
        this.updated_ts = updated_ts;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public int getCook_time_in_min() {
        return cook_time_in_min;
    }

    public void setCook_time_in_min(int cook_time_in_min) {
        this.cook_time_in_min = cook_time_in_min;
    }

    public int getPrep_time_in_min() {
        return prep_time_in_min;
    }

    public void setPrep_time_in_min(int prep_time_in_min) {
        this.prep_time_in_min = prep_time_in_min;
    }

    public int getTotal_time_in_min() {
        return total_time_in_min;
    }

    public void setTotal_time_in_min(int total_time_in_min) {
        this.total_time_in_min = total_time_in_min;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCusine() {
        return cusine;
    }

    public void setCusine(String cusine) {
        this.cusine = cusine;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public NutritionInformation getNutrition_information() {
        return nutrition_information;
    }

    public void setNutrition_information(NutritionInformation nutrition_information) {
        this.nutrition_information = nutrition_information;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}


