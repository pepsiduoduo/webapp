package com.ccweb.Dao;

import com.ccweb.Entity.Recipe;
import com.ccweb.Sql.SqlRecipe;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public class RecipeDaoImpl implements RecipeDao {

    private SqlRecipe repo;

    RecipeDaoImpl(){
        this.repo = new SqlRecipe();
    }
    @Override
    public Collection<Recipe> getAllRecipe() {
        return this.repo.getRecipe();
    }

    @Override
    public Recipe getRecipeById(String id) {
        return this.repo.getRecipeById(id);
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        this.repo.updateRecipe(recipe);
    }

    @Override
    public void insertRecipe(Recipe recipe) {
        this.repo.addRecipe(recipe);
    }

    @Override
    public void deleteRecipe(Recipe recipe) {
        this.repo.deleteRecipe(recipe);
    }

    @Override
    public boolean deleteRecipeById(String id) {
        return this.repo.deleteRecipeById(id);
    }


//    public List<Recipe> findAll() {
//        return null;
//    }
//
//
//    public List<Recipe> findAll(Sort sort) {
//        return null;
//    }
//
//
//    public Page<Recipe> findAll(Pageable pageable) {
//        return null;
//    }
//
//
//    public List<Recipe> findAllById(Iterable<String> iterable) {
//        return null;
//    }
//
//
//    public long count() {
//        return 0;
//    }
//
//
//    public void deleteById(String s) {
//
//    }
//
//    public void delete(Recipe recipe) {
//
//    }
//
//
//    public void deleteAll(Iterable<? extends Recipe> iterable) {
//
//    }
//
//
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public <S extends Recipe> S save(S s) {
//        return null;
//    }
//
//    @Override
//    public <S extends Recipe> List<S> saveAll(Iterable<S> iterable) {
//        return null;
//    }
//
//    @Override
//    public Optional<Recipe> findById(String s) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(String s) {
//        return false;
//    }
//
//    @Override
//    public void flush() {
//
//    }
//
//    @Override
//    public <S extends Recipe> S saveAndFlush(S s) {
//        return null;
//    }
//
//    @Override
//    public void deleteInBatch(Iterable<Recipe> iterable) {
//
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//
//    }
//
//    @Override
//    public Recipe getOne(String s) {
//        return null;
//    }
//
//    @Override
//    public <S extends Recipe> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends Recipe> List<S> findAll(Example<S> example) {
//        return null;
//    }
//
//    @Override
//    public <S extends Recipe> List<S> findAll(Example<S> example, Sort sort) {
//        return null;
//    }
//
//    @Override
//    public <S extends Recipe> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends Recipe> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends Recipe> boolean exists(Example<S> example) {
//        return false;
//    }
}
