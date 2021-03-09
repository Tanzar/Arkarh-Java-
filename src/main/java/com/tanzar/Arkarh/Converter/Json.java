/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Converter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Set;

/**
 *
 * @author Tanzar
 */
public class Json {
    private Gson gson;
    private JsonObject json;
    
    public Json(){
        this.gson = new Gson();
        this.json = new JsonObject();
    }
    
    public Json(String jsonString){
        this.gson = new Gson();
        this.json = this.gson.fromJson(jsonString, JsonObject.class);
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
    
    public void add(String name, double value){
        this.json.addProperty(name, value);
    }
    
    public void add(String name, boolean value){
        this.json.addProperty(name, value);
    }
    
    public void add(String name, Json json){
        this.json.add(name, json.getInnerJsonObject());
    }
    
    public void add(String name, Json[] json){
        JsonObject[] tmp = new JsonObject[json.length];
        for(int i = 0; i < json.length; i++){
            tmp[i] = json[i].getInnerJsonObject();
        }
        this.add(name, tmp);
    }
    
    public void add(String name, Object obj){
        JsonElement tmp = this.gson.toJsonTree(obj);
        this.json.add(name, tmp);
    }
    
    public String getString(String name){
        return this.json.get(name).getAsString();
    }
    
    public Json getJson(String name){
        JsonElement element = this.json.get(name);
        String jsonString = this.gson.toJson(element);
        return new Json(jsonString);
    }
    
    public String getInnerJson(String name){
        JsonElement element = json.get(name);
        String str = this.gson.toJson(element);
        return str;
    }
    
    public String[] getProperitiesNames(){
        Set<String> keySet = this.json.keySet();
        String[] result = new String[keySet.size()];
        keySet.toArray(result);
        return result;
    }
    
    public JsonObject getInnerJsonObject(){
        return this.json;
    }
    
    public String[] getStringArray(String name){
        JsonArray jsonArray = this.json.get(name).getAsJsonArray();
        String[] resultArray = new String[jsonArray.size()];
        for(int i = 0; i < resultArray.length; i++){
            JsonElement element = jsonArray.get(i);
            resultArray[i] = this.gson.toJson(element);
        }
        return resultArray;
    }
    
    public int getInt(String name){
        return this.json.get(name).getAsInt();
    }
    
    public double getDouble(String name){
        return this.json.get(name).getAsDouble();
    }
    
    public boolean getBoolean(String name){
        return this.json.get(name).getAsBoolean();
    }
    
    public static String toJson(Object object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }
    
    public static Object fromJson(String json, Object objectClass){
        Gson gson = new Gson();
        Object obj = gson.fromJson(json, objectClass.getClass());
        return obj;
    }
}
