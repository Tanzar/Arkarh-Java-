/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Map;

import com.tanzar.Arkarh.Map.Biomes.BiomeGenerator;
import com.tanzar.Arkarh.Map.Exceptions.FieldLocationException;
import com.tanzar.Arkarh.Map.Exceptions.FieldsVerificationException;
import com.tanzar.Arkarh.Map.Exceptions.MapSizeException;
import com.tanzar.Arkarh.Map.Exceptions.SplitMapException;

/**
 *
 * @author Tanzar
 */
public class Map {
    private Integer width;
    private Integer height;
    private Integer seed;
    private final Integer minSize = 100;
    private Field[][] fields;
    
    public Map(Integer width, Integer height) throws MapSizeException{
        this.setWidth(width);
        this.setHeight(height);
        this.fields = new Field[width][height];
        for(int x = 0; x < this.width; x++){
            for(int y = 0; y < this.height; y++){
                this.fields[x][y] = new Field(x, y);
            }
        }
    }
    
    private void setWidth(Integer width) throws MapSizeException{
        if(width < this.minSize){
            throw new MapSizeException("Map width must me at least: " + this.minSize);
        }
        else{
            this.width = width;
        }
    }
    
    private void setHeight(Integer height) throws MapSizeException{
        if(height < this.minSize){
            throw new MapSizeException("Map height must me at least: " + this.minSize);
        }
        else{
            this.height = height;
        }
    }
    
    public void setSeed(int seed){
        this.seed = seed;
    }
    
    public Integer getWidth(){
        return this.width;
    }
    
    public Integer getHeight(){
        return this.height;
    }
    
    public int getSeed(){
        return this.seed;
    }
    
    public Field getField(Integer x, Integer y) throws FieldLocationException{
        if(x >= this.width || x < 0){
            throw new FieldLocationException("Wrong X cooridnate, must be between: 0 - " + (this.width - 1));
        }
        if(y >= this.height || y < 0){
            throw new FieldLocationException("Wrong Y cooridnate, must be between: 0 - " + (this.height - 1));
        }
        return this.fields[x][y];
    }
    
    public void setField(Field field) throws FieldLocationException{
        Integer x = field.getX();
        Integer y = field.getY();
        if(x <= 0 && x > this.width){
            throw new FieldLocationException("Wrong X cooridnate, must be between: 0 - " + (this.width - 1));
        }
        if(y <= 0 && y > this.height){
            throw new FieldLocationException("Wrong Y cooridnate, must be between: 0 - " + (this.height - 1));
        }
        this.fields[x][y] = field;
    }
    
    public void setFields(Field[][] fields) throws FieldsVerificationException{
        this.verifyFields(fields);
        for(int i = 0; i < fields.length; i++){
            for(int j = 0; j < fields[0].length; j++){
                int x = fields[i][j].getX();
                int y = fields[i][j].getY();
                this.fields[x][y] = fields[i][j];
            }
        }
    }
    
    private void verifyFields(Field[][] fields) throws FieldsVerificationException{
        this.sizeCheck(fields);
        this.fieldsLocationsCheck(fields);
        
    }
    
    private void sizeCheck(Field[][] fields) throws FieldsVerificationException{
        if(fields.length > this.width){
            throw new FieldsVerificationException("Size do not match");
        }
        if(fields.length == 0 || fields[0].length > this.height){
            throw new FieldsVerificationException("Size do not match");
        }
    }
    
    private void fieldsLocationsCheck(Field[][] fields) throws FieldsVerificationException{
        for(int i = 0; i < fields.length; i++){
            for(int j = 0; j < fields[0].length; j++){
                try {
                    int x = fields[i][j].getX();
                    int y = fields[i][j].getY();
                    this.getField(x, y);
                }
                catch (FieldLocationException ex) {
                    throw new FieldsVerificationException("Field cannot be located for x = " + fields[i][j].getX() + " and y = " + fields[i][j].getY());
                }
            }
        }
    }
    
    public boolean isOnBorder(int x, int y){
        return (x == 0 || x == this.width - 1 || y == 0 || y == this.height - 1);
    }
    
    public boolean isTerrainSet(int x, int y) throws FieldLocationException{
        Field field = this.getField(x, y);
        return field.isTerrainSet();
    }
}
