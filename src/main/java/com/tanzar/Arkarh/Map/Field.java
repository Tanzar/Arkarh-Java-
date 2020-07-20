/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Map;

import com.tanzar.Arkarh.Map.Biomes.Biome;

/**
 *
 * @author Tanzar
 */
public class Field {
    private Integer x;
    private Integer y;
    private Biome biome;
    private String terrain;
    private String structure;

    public Field(Integer x, Integer y, Biome biome, String terrain, String structure) {
        this.x = x;
        this.y = y;
        this.biome = biome;
        this.terrain = terrain;
        this.structure = structure;
    }
    
    public Field(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.terrain = "none";
        this.structure = "none";
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Biome getBiome() {
        return biome;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }
    
    public String getDefaultTerrain(){
        return this.biome.getDefaultTerrain();
    }
    
    public boolean isBiomeSet(){
        if(this.biome == null){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean isTerrainSet(){
        if(this.terrain == null || this.terrain == "none"){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean isTerrainNotSet(){
        if(this.terrain == null || this.terrain == "none"){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void setDefaultTerrain(){
        String terrain = this.biome.getDefaultTerrain();
        this.terrain = terrain;
    }
    
    public boolean isDefaultTerrainSet(){
        if(this.terrain == this.biome.getDefaultTerrain()){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isDefaultTerrainNotSet(){
        if(this.terrain == this.biome.getDefaultTerrain()){
            return false;
        }
        else{
            return true;
        }
    }
}
