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
public class Island extends Biome{
    
    public Island(){
        super();
        this.minHeight = 10;
        this.maxHeight = 15;
        this.minWidth = 10;
        this.maxWidth = 15;
    }

    @Override
    public Field[][] generate(int width, int height) {
        Field[][] fields = new Field[width][height];
        int isleWidth = this.random(4, width - 2);
        int isleHeight = this.random(4, height - 2);
        int isleX = this.random(1, width - isleWidth - 1);
        int isleY = this.random(1, height - isleHeight - 1);
        Pattern pattern = new Pattern(isleWidth, isleHeight, this.randomizer);
        String terrain = this.islandTerrain();
        pattern.setTerrain(terrain);
        pattern.makeZonePattern();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Field field = new Field(this.x + x, this.y + y);
                field.setBiome(this);
                if((x >= isleX && x < isleX + isleWidth) && (y >= isleY && y < isleY + isleHeight)){
                    if(pattern.get(x - isleX, y - isleY) == "none"){
                        field.setTerrain(this.defaultTerrain);
                    }
                    else{
                        field.setTerrain(pattern.get(x - isleX, y - isleY));
                    }
                }
                else{
                    field.setTerrain("water");
                }
                fields[x][y] = field;
            }
        }
        return fields;
    }
    
    private String islandTerrain(){
        int number = this.random(0, 6);
        switch(number){
            case 0:
                return "arcaneGrass";
            case 1:
                return "steppes";
            case 2:
                return "jungleGrass";
            case 3:
                return "sand";
            default:
                return "grass";
        }
    }
}
