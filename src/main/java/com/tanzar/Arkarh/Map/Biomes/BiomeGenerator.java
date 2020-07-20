/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Map.Biomes;

/**
 *
 * @author Tanzar
 */
public class BiomeGenerator {
    private final Integer biomeCodeCount = 14;  //not counting defaultBiome
    
    public Biome newBiome(int code){
        switch(code){
            case 0:
                return new ArcaneForest();
            case 1:
                return new Deadlands();
            case 2:
                return new Desert();
            case 3:
                return new Farmlands();
            case 4:
                return new Island();
            case 5:
                return new Jungle();
            case 6:
                return new RockyMountains();
            case 7:
                return new Scorchlands();
            case 8:
                return new Forest();
            case 9:
                return new SnowyMountains();
            case 10:
                return new Steppes();
            case 11:
                return new Tundra();
            case 12:
                return new Volcanic();
            case 13:
                return new Sea();
            default:
                return new DefaultBiome();
        }
    }
    
    public int defaultBiome(){
        return -1;
    }
    
    public Integer minBiomeWidth(){
        Integer min = Integer.MAX_VALUE;
        for(int i = 0; i < this.biomeCodeCount; i++){
            Biome biome = this.newBiome(i);
            Integer dimension = biome.getMinWidth();
            if(dimension < min){
                min = dimension;
            }
        }
        return min;
    }
    
    public Integer minBiomeHeight(){
        Integer min = Integer.MAX_VALUE;
        for(int i = 0; i < this.biomeCodeCount; i++){
            Biome biome = this.newBiome(i);
            Integer dimension = biome.getMinHeight();
            if(dimension < min){
                min = dimension;
            }
        }
        return min;
    }
    
    public Integer maxBiomeWidth(){
        Integer max = 0;
        for(int i = 0; i < this.biomeCodeCount; i++){
            Biome biome = this.newBiome(i);
            Integer dimension = biome.getMaxWidth();
            if(dimension > max){
                max = dimension;
            }
        }
        return max;
    }
    
    public Integer maxBiomeHeight(){
        Integer max = 0;
        for(int i = 0; i < this.biomeCodeCount; i++){
            Biome biome = this.newBiome(i);
            Integer dimension = biome.getMaxHeight();
            if(dimension > max){
                max = dimension;
            }
        }
        return max;
    }
    
    public int getMatchingBiomesMinHeight(int width){
        int minHeight = Integer.MAX_VALUE;
        for(int i = 0; i < this.biomeCodeCount; i++){
            Biome biome = this.newBiome(i);
            int min = biome.getMinHeight();
            if(minHeight > min && width >= biome.getMinWidth() && width <= biome.getMaxWidth()){
                minHeight = min;
            }
        }
        if(minHeight == Integer.MAX_VALUE){
            return -1;
        }
        else{
            return minHeight;
        }
    }
    
    public int getMatchingBiomesMaxHeight(int width){
        int maxHeight = 0;
        for(int i = 0; i < this.biomeCodeCount; i++){
            Biome biome = this.newBiome(i);
            int max = biome.getMaxHeight();
            if(maxHeight < max && width >= biome.getMinWidth() && width <= biome.getMaxWidth()){
                maxHeight = max;
            }
        }
        return maxHeight;
    }
    
    public int[] getMatchingBiomes(int width, int freeHeight){
        boolean[] biomesAcceptFlags = new boolean[this.biomeCodeCount];
        int count = 0;
        for(int i = 0; i < this.biomeCodeCount; i++){
            Biome biome = this.newBiome(i);
            int minWidth = biome.getMinWidth();
            int maxWidth = biome.getMaxWidth();
            int minHeight = biome.getMinHeight();
            if(width >= minWidth && width <= maxWidth && freeHeight >= minHeight){
                biomesAcceptFlags[i] = true;
                count++;
            }
            else{
                biomesAcceptFlags[i] = false;
            }
        }
        if(count == 0){
            int[] matchingBiomes = new int[1];
            matchingBiomes[0] = this.defaultBiome();
            return matchingBiomes;
        }
        else{
            int[] matchingBiomes = new int[count];
            int index = 0;
            for(int i = 0; i < this.biomeCodeCount; i++){
                if(biomesAcceptFlags[i] == true){
                    matchingBiomes[index] = i;
                    index++;
                }
            }
            return matchingBiomes;
        }
    }
    /*
    public int[] getMatchingBiomes(int width){
        boolean[] biomesAcceptFlags = new boolean[this.biomeCodeCount];
        int count = 0;
        for(int i = 0; i < this.biomeCodeCount; i++){
            Biome biome = this.newBiome(i);
            int minWidth = biome.getMinWidth();
            int maxWidth = biome.getMaxWidth();
            if(width >= minWidth && width <= maxWidth){
                biomesAcceptFlags[i] = true;
                count++;
            }
            else{
                biomesAcceptFlags[i] = false;
            }
        }
        int[] matchingBiomes = new int[count];
        int index = 0;
        for(int i = 0; i < this.biomeCodeCount; i++){
            if(biomesAcceptFlags[i] == true){
                matchingBiomes[index] = i;
                index++;
            }
        }
        return matchingBiomes;
    }*/
    
    
}
