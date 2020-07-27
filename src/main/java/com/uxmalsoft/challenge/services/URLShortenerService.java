/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uxmalsoft.challenge.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

/**
 *
 * @author JFGH
 */
public class URLShortenerService {
    
    private HashMap<String, String> aliasMap;
    
    public URLShortenerService(){
        aliasMap = new HashMap<String, String>();
        
    }//constructor
    
    /**
     * Pregunta por un alias
     * @param alias
     * @return 
     */
    public URI askForAlias(String alias) throws HttpClientErrorException,HttpServerErrorException{
        URI uri = null;
        if (alias!= null && !alias.isEmpty()){
            String url = aliasMap.get(alias);

            if(url!= null && !url.isEmpty())
                try{
                    uri = URI.create(url);
                }catch(Exception ex){
                    throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ex.toString());
                }
            else
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Alias Not Found");

        }else{
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Empty alias");
        }   
           
        return uri;
    }//askForAlias
    
  
}//class
