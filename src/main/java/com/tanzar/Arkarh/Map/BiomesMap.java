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
public class BiomesMap {
    private Integer width;
    private Integer height;
    private Integer[][] map;
    
    public BiomesMap(Integer width, Integer height){
        this.height = height;
        this.width = width;
        this.map = new Integer[this.width][this.height];
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                this.map[x][y] = -1;
            }
        }
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer countSpaceX(Integer x, Integer y){
        Integer counter = 0;
        for(int xi = x; xi < this.width; xi++){
            if(this.map[xi][y] == -1){
                counter++;
            }
            else{
                xi = this.width;
            }
        }
        return counter;
    }
    
    public Integer countSpaceY(Integer x, Integer y){
        Integer counter = 0;
        for(int yi = y; yi < this.height; yi++){
            if(this.map[x][yi] == -1){
                counter++;
            }
            else{
                yi = this.height;
            }
        }
        return counter;
    }
    
    public int get(int x, int y){
        if(x <= this.width && x >= 0 && y <= this.height && y >= 0){
            return this.map[x][y];
        }
        else{
            return -2;
        }
    }
    
    public boolean isFree(int x, int y){
        if(this.map[x][y] == -1){
            return true;
        }
        else{
            return false;
        }
    }
    
    public Integer firstFreeXIndex(Integer y){
        Integer x = -1;
        for(int xi  = 0; xi < this.width; xi++){
            if(this.map[xi][y] == -1){
                x = xi;
                xi = this.width;
            }
        }
        return x;
    }
    
    public void markZone(int x, int y, int width, int height, int number){
        if(width > 0 && height > 0){
            for(int xi = x; xi < x + width; xi++){
                for(int yi = y; yi < y + height; yi++){
                    if(xi <= this.width && xi >= 0 && yi <= this.height && yi >= 0){
                        this.map[xi][yi] = number;
                    }
                }
            }
        }
    }
}
