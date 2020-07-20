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
public class Desert extends Biome{

    public Desert(){
        super();
        this.minHeight = 20;
        this.maxHeight = 40;
        this.minWidth = 20;
        this.maxWidth = 40;
        this.defaultTerrain = "sand";
    }
    
    @Override
    public Field[][] generate(int width, int height) {
        Field[][] fields = new Field[width][height];
        Pattern pattern = new Pattern(width, height, this.randomizer);
        pattern.setTerrain(this.defaultTerrain);
        pattern.makeZonePattern();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                fields[x][y] = this.newField(this.x + x, this.y + y, this, pattern.get(x, y));
                if(fields[x][y].isTerrainSet()){
                    int number = this.random(1, 100);
                    if(number < 10){
                        fields[x][y].setStructure("cactus");
                    }
                }
            }
        }
        return fields;
    }
    
}
