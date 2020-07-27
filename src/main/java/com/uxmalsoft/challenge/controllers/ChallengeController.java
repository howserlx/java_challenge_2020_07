/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uxmalsoft.challenge.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uxmalsoft.challenge.services.URLShortenerService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

/**
 *
 * @author JFGH
 */
@RestController
@RequestMapping("/")
public class ChallengeController {
    
    private URLShortenerService urlShortenerService;
    
    public ChallengeController(){
        urlShortenerService = new URLShortenerService();
    }//constructor
    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Java Challenge / Java School / Nearsoft 2020-07");
    }//redirect
    
    /**
     * Redirect
     * @param alias
     * @return 
     */
    @RequestMapping(value = "/{alias}", method = RequestMethod.GET)
    public ResponseEntity<String> redirect(@PathVariable("alias") String alias) {
        ResponseEntity response = null;
        URI location;
        
        try {
            location = urlShortenerService.askForAlias(alias);

            //redirect
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);
            response = new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

        } catch (HttpClientErrorException ex) {
            //4xx url syntaxis error
            Logger.getLogger(ChallengeController.class.getName()).log(Level.SEVERE, null, ex);
            response = new ResponseEntity(ex.getStatusCode());

        } catch (HttpServerErrorException ex) {
            //5xx url syntaxis error
            Logger.getLogger(ChallengeController.class.getName()).log(Level.SEVERE, null, ex);
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(Exception ex){
            Logger.getLogger(ChallengeController.class.getName()).log(Level.SEVERE, null, ex);
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }//redirect
    
    /**
     * Intenta crear un alias para la url
     * @param url
     * @return 
     */
    @RequestMapping(value = "/api/create/", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map create(@RequestBody Map<String, String> data) {
        //List<JSONObject> entities = new ArrayList<JSONObject>();
        HashMap entity = new HashMap();
        entity.put("status" , "500");
        entity.put("message", "Internal Server Error");
        entity.put("alias"  , data.get("url"));
        //entities.add(entity);
        
        return entity;    
    }
    
    
}//class
