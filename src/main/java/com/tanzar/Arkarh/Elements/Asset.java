/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Elements;

/**
 *
 * @author Tanzar
 */
public class Asset {
    private String path;
    private String name;

    public Asset(String path, String name) {
        this.path = this.correctPath(path);
        this.name = name;
    }
    
    private String correctPath(String path){
        for(int i = 0; i < path.length(); i++){
            char character = path.charAt(i);
            if(character == '\\' && i < (path.length() - 1)){
                String before = path.substring(0, i);
                String after = path.substring(i + 1);
                path = before + "/" + after;
            }
        }
        return path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
