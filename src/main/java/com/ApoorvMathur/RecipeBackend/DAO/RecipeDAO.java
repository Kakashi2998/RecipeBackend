package com.ApoorvMathur.RecipeBackend.DAO;

import com.ApoorvMathur.RecipeBackend.Entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestResource(exported = false)
public interface RecipeDAO extends JpaRepository<Recipe, Integer> {
}
