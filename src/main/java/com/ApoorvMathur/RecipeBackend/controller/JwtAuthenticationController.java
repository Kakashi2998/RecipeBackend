package com.ApoorvMathur.RecipeBackend.controller;

import com.ApoorvMathur.RecipeBackend.DAO.AccountDAO;
import com.ApoorvMathur.RecipeBackend.Entities.Account;
import com.ApoorvMathur.RecipeBackend.Entities.JwtRequest;
import com.ApoorvMathur.RecipeBackend.Entities.JwtResponse;
import com.ApoorvMathur.RecipeBackend.security.JwtTokenUtil;
import com.ApoorvMathur.RecipeBackend.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private AccountDAO accountDAO;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/authenticate/signUp")
    public ResponseEntity<?> signUp(@RequestBody JwtRequest signUpRequest){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(accountDAO.findByUsername(signUpRequest.getUsername()) != null){
            return ResponseEntity.status(403).body("Username already exists!");
        }
        Account account = new Account(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));
        accountDAO.save(account);
        return ResponseEntity.ok("User: " + signUpRequest.getUsername() + " signed up successfully");
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}