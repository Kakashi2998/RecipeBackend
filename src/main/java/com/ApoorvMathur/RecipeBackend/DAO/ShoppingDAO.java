package com.ApoorvMathur.RecipeBackend.DAO;

import com.ApoorvMathur.RecipeBackend.Entities.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface ShoppingDAO extends JpaRepository<ShoppingItem, Integer> {
}
