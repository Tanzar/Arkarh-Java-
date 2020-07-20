/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Map.Biomes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tanzar
 */
public class BiomeList {
    private List<Biome> list;
    
    public BiomeList(){
        this.list = new ArrayList<Biome>();
    }
    
    public void add(Biome item){
        this.list.add(item);
    }
    
    public Biome get(Integer index){
        return this.list.get(index);
    }
    
    public Biome getAndRemove(Integer index){
        Biome item = this.list.get(index);
        this.list.remove(item);
        return item;
    }
    
    public Biome[] toArray(){
        Biome[] array = new Biome[this.list.size()];
        this.list.toArray(array);
        return array;
    }
    
    public boolean isEmpty(){
        if(this.list.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public int size(){
        return this.list.size();
    }
    
    public void clear(){
        this.list.clear();
    }
}
