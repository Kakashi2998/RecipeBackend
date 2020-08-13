package com.ApoorvMathur.RecipeBackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {

        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader;
            try {
                if(getGoogleTokenInfo(jwtToken.substring(7))){
                    chain.doFilter(request, response);
                }
                else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
    }

    public boolean getGoogleTokenInfo(String accessToken) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> googleResponse;
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("https://www.googleapis.com/oauth2/v3/tokeninfo")
                    .queryParam("id_token", accessToken);
            ObjectMapper mapper = new ObjectMapper();
            googleResponse = mapper.readValue(restTemplate.getForObject(uriBuilder.toUriString(),String.class), Map.class);

        } catch (HttpClientErrorException e) {
            return false;
        }
        System.out.println(googleResponse.get("name"));
        return true;
    }

}