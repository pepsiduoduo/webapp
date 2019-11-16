package com.ccweb.Entity;

import java.util.LinkedHashSet;
import java.util.Set;

public class Recipie {
    private String id;
    private String created_ts;
    private String updated_ts;
    private String author_id;
    private Integer cook_time_in_min;
    private Integer prep_time_in_min;
    private Integer total_time_in_min;
    private String title;
    private String cusine;
    private Integer servings;
    private OrderedList steps;
    private NutritionInformation nutritionInformation;
    private Ingredients ingredients;


    public Recipie(String id, String created_ts, String updated_ts, String author_id, Integer cook_time_in_min, Integer prep_time_in_min, Integer total_time_in_min, String title, String cusine, Integer servings, OrderedList steps, NutritionInformation nutritionInformation, Ingredients ingredients) {
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
        this.steps= new OrderedList();
        this.nutritionInformation= new NutritionInformation();
        this.ingredients = new Ingredients();
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }

    public OrderedList getSteps() {
        return steps;
    }

    public void setSteps(OrderedList steps) {
        this.steps = steps;
    }

    public NutritionInformation getNutritionInformation() {
        return nutritionInformation;
    }

    public void setNutritionInformation(NutritionInformation nutritionInformation) {
        this.nutritionInformation = nutritionInformation;
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

    public Integer getCook_time_in_min() {
        return cook_time_in_min;
    }

    public void setCook_time_in_min(Integer cook_time_in_min) {
        this.cook_time_in_min = cook_time_in_min;
    }

    public Integer getPrep_time_in_min() {
        return prep_time_in_min;
    }

    public void setPrep_time_in_min(Integer prep_time_in_min) {
        this.prep_time_in_min = prep_time_in_min;
    }

    public Integer getTotal_time_in_min() {
        return total_time_in_min;
    }

    public void setTotal_time_in_min(Integer total_time_in_min) {
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

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }
}
