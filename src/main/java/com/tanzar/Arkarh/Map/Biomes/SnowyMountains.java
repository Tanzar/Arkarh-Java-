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
public class SnowyMountains extends Biome{
    
    public SnowyMountains(){
        super();
        this.minHeight = 10;
        this.maxHeight = 15;
        this.minWidth = 10;
        this.maxWidth = 15;
        this.defaultTerrain = "snow";
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
                if(field.getTerrain() == this.defaultTerrain){
                    int chance = 20;
                    int number = this.random(1, 100);
                    if(number <= chance){
                        field.setTerrain("stone");
                    }
                }
                fields[x][y] = field;
            }
        }
        return fields;
    }
    
}
