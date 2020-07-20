/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Elements;

import com.tanzar.Arkarh.Entities.Field;

/**
 *
 * @author Tanzar
 */
public class GameBoard {
    
    private Field[][] fields;
    private int height;
    private int width;
    private int fieldHeight = 50;
    private int fieldWidth = 50;
    
    public GameBoard(int widthInFields, int heightInFields, int terrainIndex){
        this.fields = new Field[widthInFields][heightInFields];
        this.height = heightInFields;
        this.width = widthInFields;
        for(int x = 0; x < widthInFields; x++){
            for(int y = 0; y < heightInFields; y++){
                Field currentField = new Field(0, x, y, 0, terrainIndex, 0, "none", "none");
                this.fields[x][y] = currentField;
            }
        }
    }

    public Field[][] getFields() {
        return fields;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }
    
    public void setField(int x, int y, Field field){
        this.fields[x][y] = field;
    }
    
    public Field getField(int x, int y){
        return this.fields[x][y];
    }
}
