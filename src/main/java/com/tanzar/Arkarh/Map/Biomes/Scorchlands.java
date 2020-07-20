/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Map.Biomes;

import com.tanzar.Arkarh.Map.Field;
import com.tanzar.Arkarh.Map.Pattern;

/**
 *
 * @author Tanzar
 */
public class Scorchlands extends Biome{
    
    public Scorchlands(){
        super();
        this.minHeight = 30;
        this.maxHeight = 60;
        this.minWidth = 30;
        this.maxWidth = 60;
        this.defaultTerrain = "obsidian";
    }

    @Override
    public Field[][] generate(int width, int height) {
        Field[][] fields = new Field[width][height];
        Pattern pattern = new Pattern(width, height, this.randomizer);
        pattern.setTerrain(this.defaultTerrain);
        pattern.makeZonePattern();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Field field = this.newField(this.x + x, this.y + y, this, pattern.get(x, y));
                fields[x][y] = field;
            }
        }
        return fields;
    }
    
}
