package com.ccweb.Dao;

import com.ccweb.Entity.Recipe;

import java.util.Collection;

public interface RecipeDao {

    Collection<Recipe> getAllRecipe();

    Recipe getRecipeById(String id);

    void updateRecipe(Recipe recipe);

    void insertRecipe(Recipe recipe);

    void deleteRecipe(Recipe recipe);

    boolean deleteRecipeById(String id);
}
