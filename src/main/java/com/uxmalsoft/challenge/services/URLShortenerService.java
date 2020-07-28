/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uxmalsoft.challenge.services;

import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.uxmalsoft.challenge.utils.RandomString;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author JFGH
 */
public class URLShortenerService {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)";
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
            
            //Ya fue insertada
            String alreadyInsertedURL = aliasMap.get(alias);
            if(alreadyInsertedURL!= null && !alreadyInsertedURL.isEmpty()){
            
            }
            
            try {
                //Hace uan peticion a la url para verificar que exista
                int responseCode = sendGET(url);
                
                //Si los status son 200 o 302, la inserta
                if(responseCode == HttpStatus.OK.value() || responseCode == HttpStatus.FOUND.value()){
                    //Si responde 200 lo inserta
                    aliasMap.put(alias, url);
                    entity.put("status" , HttpStatus.OK);
                    entity.put("message", "Alias created");
                    entity.put("alias"  , alias);
                }else{
                    //No insertado por TIMEOUT, BAD_REQUEST, NOT_FOUND, FORBIDDEN
                    HttpStatus status = (HttpStatus.resolve(responseCode)!=null)?HttpStatus.resolve(responseCode):HttpStatus.INTERNAL_SERVER_ERROR;
                    entity.put("status" , status.value());
                    entity.put("message", status.name());
                    entity.put("alias"  , "");
                }
                
            } catch (Exception ex) {
                Logger.getLogger(URLShortenerService.class.getName()).log(Level.SEVERE, null, ex);
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            }
             
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
                alias = URI.create(url).getHost().toLowerCase().replaceAll("[^A-Za-z]", "").replaceAll("[a|e|i|o|u]","");
            }
        
        return alias;
    }//generateAlias
    
    
    /**
     * Send a Get Request
     * @param url
     * @return
     * @throws IOException 
     */
    private static int sendGET(String url) throws IOException {
        int responseCode = 0;
        HttpURLConnection con = null;
                
        try{
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent",USER_AGENT);
            con.setConnectTimeout(5000);
            con.setRequestProperty("Accept","*/*");
            con.connect();

            responseCode = con.getResponseCode();
            
        }catch (IOException ex) {
            if(con!=null) con.disconnect();
            Logger.getLogger(URLShortenerService.class.getName()).log(Level.SEVERE, null, ex);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }finally{
            if(con!=null) con.disconnect();
        }
        
        
        return responseCode;
    }//sendGET
    
  
}//class
