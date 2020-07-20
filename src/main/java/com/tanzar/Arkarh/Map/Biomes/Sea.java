/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Map.Biomes;

import com.tanzar.Arkarh.Map.Field;

/**
 *
 * @author Tanzar
 */
public class Sea extends Biome{
    
    public Sea(){
        super();
        this.minHeight = 10;
        this.maxHeight = 60;
        this.minWidth = 10;
        this.maxWidth = 60;
    }

    @Override
    public Field[][] generate(int width, int height) {
        Field[][] fields = new Field[width][height];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Field field = new Field(this.x + x, this.y + y);
                field.setBiome(this);
                field.setTerrain("water");
                fields[x][y] = field;
            }
        }
        return fields;
    }
    
}
