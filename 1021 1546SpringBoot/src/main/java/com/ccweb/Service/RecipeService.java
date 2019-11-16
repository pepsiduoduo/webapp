package com.ccweb.Service;

import com.ccweb.Dao.RecipeDaoImpl;
import com.ccweb.Entity.Recipe;
import com.ccweb.Entity.Steps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service


public class RecipeService {


//    @Autowired
//    private FakeAccountDaoImpl fakeAccountDaoImpl;

    @Autowired
    private RecipeDaoImpl recipeDaoImpl;

    public Collection<Recipe> getAllRecipe(){
        return this.recipeDaoImpl.getAllRecipe();
    }

    public Recipe getRecipeById(String id){
        return this.recipeDaoImpl.getRecipeById(id);
    }

    public void updateRecipe(Recipe recipe){
        this.recipeDaoImpl.updateRecipe(recipe);
    }

    public void addRecipe(Recipe recipe){

        this.recipeDaoImpl.insertRecipe(recipe);
    }

    public void delete(Recipe recipe){
        this.recipeDaoImpl.deleteRecipe(recipe);
    }

    public List<Steps> orderSteps(List<Steps> steps){

        return null;
    }

}
