/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Equipment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tanzar
 */
public class Artifacts {
    
    private List<Artifact> list;
    private int limit;
    
    public Artifacts(){
        this.list = new ArrayList<Artifact>();
        this.limit = 0;
    }
    
    public Artifacts(int limit){
        this.list = new ArrayList<Artifact>();
        this.limit = limit;
    }
    
    public Artifact get(int index){
        if(this.list.size() == 0 || index < 0 || index >= this.list.size()){
            return null;
        }
        else{
            return this.list.get(index);
        }
    }
    
    public void remove(int index){
        if(index >= 0 && index < this.list.size()){
            this.list.remove(index);
        }
    }
    
    public void add(Artifact artifact){
        if(artifact != null && !this.isFull()){
            this.list.add(artifact);
        }
    }
    
    public int size(){
        return this.list.size();
    }
    
    public boolean isEmpty(){
        if(this.list.size() < 1){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isFull(){
        if(this.limit > 0 && this.size() >= this.limit){
            return true;
        }
        else{
            return false;
        }
    }
    
    public Artifact[] toArray(){
        Artifact[] array = new Artifact[this.list.size()];
        for(int i = 0; i < this.list.size(); i++){
            array[i] = this.get(i);
        }
        return array;
    }
}
