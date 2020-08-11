package com.ApoorvMathur.RecipeBackend.controller;

import com.ApoorvMathur.RecipeBackend.DAO.RecipeDAO;
import com.ApoorvMathur.RecipeBackend.Entities.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    private RecipeDAO recipeDAO;

    @GetMapping("recipes")
    public List<Recipe> getRecipes(){
        return recipeDAO.findAll();
    }

    @GetMapping("/recipes/{id}")
    public Recipe getRecipe(@PathVariable int id){
        return recipeDAO.findById(id).get();
    }

    @PutMapping("recipes/{recipeId}/bookmark")
    public ResponseEntity<String> bookmark(@PathVariable int recipeId){
        Recipe recipe = recipeDAO.getOne(recipeId);
        recipe.toggleBookmark();
        recipeDAO.save(recipe);
        return ResponseEntity.ok("Bookmark toggled for id: " + recipeId);
    }
}
