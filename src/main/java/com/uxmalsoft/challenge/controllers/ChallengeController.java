/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uxmalsoft.challenge.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uxmalsoft.challenge.services.URLShortenerService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

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
     * 
     * @param alias
     * @return 
     */
    @RequestMapping(value = "/{alias}", method = RequestMethod.GET)
    public ResponseEntity<String> redirect(@PathVariable("alias") String alias) {
        ResponseEntity response = null;
        
        if(alias!=null && !alias.isEmpty()){
            URI location;
            try {
                location = urlShortenerService.askForAlias(alias);
                
                if(location!=null){
                    //redirect
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(location);
                    response = new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
                }else{
                    //404 not found
                    response = new ResponseEntity(HttpStatus.NOT_FOUND);
                }
                
            } catch (URISyntaxException ex) {
                //500 url syntaxis error
                Logger.getLogger(ChallengeController.class.getName()).log(Level.SEVERE, null, ex);
                response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
        }else{
            //400 bad request
            response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        
        return response;
    }//redirect
    
}//class
