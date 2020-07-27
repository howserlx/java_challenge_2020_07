/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uxmalsoft.challenge.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 *
 * @author JFGH
 */
public class URLShortenerService {
    
    private HashMap<String, String> aliasMap;
    
    public URLShortenerService(){
        aliasMap = new HashMap<String, String>();
        
        aliasMap.put("abc","https://www.facebook.com/uxmalsoft");
        aliasMap.put("123","hps://twitter.com/uxmalsoft");
    }//constructor
    
    /**
     * Pregunta si un alias existe 
     * @param alias
     * @return 
     */
    public URI askForAlias(String alias) throws URISyntaxException{
        URI uri = null;
        if (alias!= null && !alias.isEmpty()){
            String url = aliasMap.get(alias);
            
            if(url!= null && !url.isEmpty())
                uri = URI.create(url);
        }else{
            throw new URISyntaxException("","");
        }
            
        return uri;
    }//askForAlias
    
}//class
