/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uxmalsoft.challenge.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.uxmalsoft.challenge.utils.RandomString;
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
    
    
    
    
    /**
     * Crea un alias
     * @param url
     * @return 
     */
    public Map createAlias(String url) throws HttpClientErrorException,HttpServerErrorException{
        HashMap entity = new HashMap();
        
        if (url!= null && !url.isEmpty()){
            String alias = generateAlias(url);
            
            //Pendiente - validaciones de existencia
            aliasMap.put(alias, url);
            entity.put("status" , HttpStatus.OK);
            entity.put("message", "Alias created");
            entity.put("alias"  , alias);
            
        }else{
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Empty url");
        }   
           
        return entity;
    }//createAlias
    
    
    /**
     * Crea un alias
     * @param url
     * @return 
     */
    private String generateAlias(String url){
        String alias = null;
        if(url.toLowerCase().contains("google")){
            alias = RandomString.getAlphaString(5);
        }else 
            if(url.toLowerCase().contains("yahoo")){
                alias = RandomString.getAlphaNumericString(7);
            }else{
                alias = url.toLowerCase().replaceAll("/[^A-Za-z ]/", "").replaceAll("[a|e|i|o|u]","");
            }
        
        return alias;
    }//generateAlias
    
  
}//class
