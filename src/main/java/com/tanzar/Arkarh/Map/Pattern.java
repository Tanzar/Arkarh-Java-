/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Map;

/**
 *
 * @author Tanzar
 */
public class Pattern {
    private int width;
    private int height;
    private String terrain;
    private String[][] pattern;
    private Randomizer randomizer;
    
    public Pattern(int width, int height, Randomizer randomizer){
        if(width < 2){
            width = 2;
        }
        if(height < 2){
            height = 2;
        }
        this.width = width;
        this.height = height;
        this.pattern = new String [width][height];
        this.terrain = "terrain";
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                this.pattern[x][y] = "none";
            }
        }
        this.randomizer = randomizer;
    }
    
    public void setTerrain(String terrain){
        this.terrain = terrain;
    }
    
    public String get(int x, int y){
        if(x >= 0 && x < this.width && y >= 0 && y < height){
            return this.pattern[x][y];
        }
        else{
            return "none";
        }
    }
    
    public boolean areAllNeighbours(int x, int y, String terrainToCheck){
        boolean flag = true;
        for(int xi = x - 1; xi < x + 2; xi++){
            for(int yi = y - 1; yi < y + 2; yi++){
                if(xi != x || yi != y){
                    if(this.isNotTerrain(xi, yi, terrainToCheck)){
                        flag = false;
                        xi = x + 2;
                        yi = y + 2;
                    }
                }
            }
        }
        return flag;
    }
    
    public boolean isTerrain(int x, int y, String terrain){
        if(this.get(x, y) == terrain){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isNotTerrain(int x, int y, String terrain){
        if(this.get(x, y) == terrain){
            return false;
        }
        else{
            return true;
        }
    }
    
    public String[][] getLastPattern(){
        return this.pattern;
    }
    
    public String[][] makeIslandPattern(){
        for(int x = 0; x < this.width; x++){
            for(int y = 0; y < this.height; y++){
                this.pattern[x][y] = this.terrain;
            }
        }
        int chance = 20;
        for(int x = 0; x < this.width; x++){
            for(int y = 0; y < this.height; y++){
                if((x == 0 || x == this.width - 1) || (y == 0 || y == this.height - 1)){
                    int number = this.randomizer.random(1, 100);
                    if(number <= chance){
                        this.pattern[x][y] = "water";
                    }
                }
            }
        }
        
        return this.pattern;
    }
    
    public String[][] makeHorizontalCoastPattern(boolean waterOnTop){
        int shoreLine = this.randomizer.random(1, height - 2);
        String top = this.terrain;
        String bottom = "water";
        if(waterOnTop){
            bottom = this.terrain;
            top = "water";
        }
        for(int x = 0; x < this.width; x++){
            this.pattern[x][0] = top;
            this.pattern[x][this.height - 1] = bottom;
            for(int y = 1; y < this.height - 1; y++){
                if(y < shoreLine){
                    this.pattern[x][y] = top;
                }
                else{
                    this.pattern[x][y] = bottom;
                }
            }
        }
        return this.pattern;
    }
    
    public String[][] makeVerticalCoastPattern(boolean waterOnLeft){
        int shoreLine = this.randomizer.random(1, width - 2);
        String left = this.terrain;
        String right = "water";
        if(waterOnLeft){
            right = this.terrain;
            left = "water";
        }
        for(int y = 0; y < this.height; y++){
            this.pattern[0][y] = left;
            this.pattern[this.width - 1][y] = right;
            for(int x = 1; x < this.width - 1; x++){
                if(x < shoreLine){
                    this.pattern[x][y] = left;
                }
                else{
                    this.pattern[x][y] = right;
                }
            }
        }
        return this.pattern;
    }
    
    public String[][] makeZonePattern(){
        int chance = 30;
        this.fillTerrain();
        for(int x = 0; x < this.width; x++){
            this.nullifyPosition(x, 0, chance);
        }
        for(int y = 0; y < this.height; y++){
            this.nullifyPosition(this.width - 1, y, chance);
        }
        for(int x = (this.width - 1); x > 0; x--){
            this.nullifyPosition(x, this.height - 1, chance);
        }
        for(int y = (this.height - 1); y > 0; y--){
            this.nullifyPosition(0, y, chance);
        }
        int numberOfLayers = 0;
        if(this.width > this.height){
            numberOfLayers = (int) Math.floor(this.height / 2) - 1;
        }
        else{
            numberOfLayers = (int) Math.floor(this.width / 2) - 1;
        }
        for(int i = 1; i <= numberOfLayers; i++){
            workLayer(i, chance);
        }
        return this.pattern;
    }
    
    private void nullifyPosition(int x, int y, int chance){
        int number = this.randomizer.random(1, 100);
        if(number <= chance){
            this.pattern[x][y] = "none";
        }
        else{
            this.pattern[x][y] = this.terrain;
        }
    }
    
    private void workLayer(int layerLevel, int chance){
        if(layerLevel < 1)
            layerLevel = 1;
        for(int x = layerLevel; x < (this.width - layerLevel); x++){
            workPosition(x, layerLevel, chance);
        }
        for(int y = layerLevel; y < (this.height - layerLevel); y++){
            workPosition((this.width - layerLevel - 1), y, chance);
        }
        for(int x = (this.width - layerLevel - 1); x > layerLevel; x--){
            workPosition(x, (this.height - layerLevel - 1), chance);
        }
        for(int y = (this.height - layerLevel - 1); y > layerLevel; y--){
            workPosition(layerLevel, y, chance);
        }
    }
    
    private void workPosition(int x, int y, int chance){
        if(this.pattern[x - 1][y] == "none" || this.pattern[x][y - 1] == "none" || this.pattern[x + 1][y] == "none" || this.pattern[x][y + 1] == "none"){
            this.nullifyPosition(x, y, chance);
        }
        else{
            this.pattern[x][y] = this.terrain;
        }
    }
    
    public String[][] makeLakePattern(boolean lavaInsteadOfWater, boolean haveShores){
        String liquid = "water";
        if(lavaInsteadOfWater){
            liquid = "lava";
        }
        for(int x = 0; x < this.width; x++){
            for(int y = 0; y < this.height; y++){
                if(haveShores){
                    
                }
                else{
                    
                }
            }
        }
        return this.pattern;
    }
    
    private boolean isOnBorder(int x, int y){
        return (x == 0 || x == this.width - 1 || y == 0 || y == this.height - 1);
    }
    
    private void fillTerrain(){
        for(int x = 0; x < this.width; x++){
            for(int y = 0; y < this.height; y++){
                this.pattern[x][y] = this.terrain;
            }
        }
    }
    
    private void clearPattern(){
        for(int x = 0; x < this.width; x++){
            for(int y = 0; y < this.height; y++){
                this.pattern[x][y] = "none";
            }
        }
    }
}
