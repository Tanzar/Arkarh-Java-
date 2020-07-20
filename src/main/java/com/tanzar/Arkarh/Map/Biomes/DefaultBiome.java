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
public class DefaultBiome extends Biome{

    public DefaultBiome() {
        super();
        this.minHeight = 1;
        this.minWidth = 1;
        this.maxWidth = 1;
        this.maxHeight = 1;
        this.defaultTerrain = "water";
    }
    
    
    @Override
    public Field[][] generate(int width, int height) {
        Field[][] fields = new Field[width][height];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Field field = new Field(this.x + x, this.y + y);
                field.setBiome(this);
                field.setTerrain(this.defaultTerrain);
                fields[x][y] = field;
            }
        }
        return fields;
    }
    
}
