package com.ccweb.Entity;



public class Steps {

    private String id;


   // @JSONField(serialize = false)
    private Recipe recipe;//所属recipe

    private int position;
    private String items;


    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Steps(int position, String items) {
        this.position = position;
        this.items = items;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
