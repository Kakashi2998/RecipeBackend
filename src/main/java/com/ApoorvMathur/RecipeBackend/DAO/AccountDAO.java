package com.ApoorvMathur.RecipeBackend.DAO;

import com.ApoorvMathur.RecipeBackend.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface AccountDAO extends JpaRepository<Account, String> {

    @Query("SELECT i from Account i where i.username = :username")
    Account findByUsername(@Param("username") String username);
}
