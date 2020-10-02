/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Converter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 *
 * @author Tanzar
 */
public class Json {
    private Gson gson;
    private JsonObject json;
    
    public Json(String jsonString){
        this.gson = new Gson();
        this.json = this.gson.fromJson(jsonString, JsonObject.class);
    }
    
    public Json(){
        this.gson = new Gson();
        this.json = new JsonObject();
    }
    
    public String formJson(){
        String jsonString = this.gson.toJson(this.json);
        return jsonString;
    }
    
    public void add(String name, String value){
        this.json.addProperty(name, value);
    }
    
    public void add(String name, int value){
        this.json.addProperty(name, value);
    }
    
    public String getString(String name){
        return this.json.get(name).getAsString();
    }
    
    public int getInt(String name){
        return this.json.get(name).getAsInt();
    }
    
    public static String toJson(Object object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }
}
