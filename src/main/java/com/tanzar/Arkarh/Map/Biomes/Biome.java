/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Map.Biomes;

import com.tanzar.Arkarh.Map.Field;
import com.tanzar.Arkarh.Map.Randomizer;

/**
 *
 * @author Tanzar
 */
public abstract class Biome {
    protected Integer x;
    protected Integer y;
    protected Integer width;
    protected Integer height;
    protected Integer minWidth;
    protected Integer minHeight;
    protected Integer maxWidth;
    protected Integer maxHeight;
    protected String defaultTerrain;
    protected Randomizer randomizer;
    
    
    public Biome(){
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.maxHeight = 20;
        this.maxWidth = 20;
        this.minHeight = 10;
        this.minWidth = 10;
        this.defaultTerrain = "water";
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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(Integer minWidth) {
        this.minWidth = minWidth;
    }

    public Integer getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Integer minHeight) {
        this.minHeight = minHeight;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Integer getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
    }
    
    public void setRandomizer(Randomizer randomizer){
        this.randomizer = randomizer;
    }
    
    public void setDefaultTerrain(String terrain){
        this.defaultTerrain = terrain;
    }
    
    public String getDefaultTerrain(){
        return this.defaultTerrain;
    }
    
    protected int random(int min, int max){
        return this.randomizer.random(min, max);
    }
    
    public Field[][] generate(){
        return this.generate(this.width, this.height);
    }
    
    protected Field newField(int x, int y, Biome biome, String terrain){
        Field field = new Field(x, y);
        field.setBiome(biome);
        field.setTerrain(terrain);
        return field;
    }
    
    public boolean isOnBorder(int x, int y){
        return (x == 0 || x == this.width - 1 || y == 0 || y == this.height - 1);
    }
    
    public boolean isCorner(int x, int y){
        if(x == 0 && y == 0){
            return true;
        }
        if(x == 0 && y == this.getHeight() - 1){
            return true;
        }
        if(x == this.getWidth() - 1 && y == 0){
            return true;
        }
        if(x == this.getWidth() - 1 && y == this.getHeight() - 1){
            return true;
        }
        return false;
    }
    
    protected abstract Field[][] generate(int width, int height);
    
}
