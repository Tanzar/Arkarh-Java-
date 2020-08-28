/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author spako
 */
public class AssetsList {
    private String category;
    private List<Asset> list;
    
    public AssetsList(String category){
        this.category = category;
        this.list = new ArrayList<Asset>();
    }
    
    public String getCategory(){
        return this.category;
    }
    
    public Asset get(int index){
        return this.list.get(index);
    }
    
    public void add(Asset asset){
        this.list.add(asset);
    }
    
    public int size(){
        return this.list.size();
    }
    
    public boolean isCategory(String category){
        return this.category.equals(category);
    }
    
    public Asset[] toArray(){
        Asset[] array = new Asset[this.size()];
        this.list.toArray(array);
        return array;
    }
}
