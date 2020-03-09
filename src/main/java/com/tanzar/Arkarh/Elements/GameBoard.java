/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Elements;

import com.tanzar.Arkarh.Entities.Terrain;

/**
 *
 * @author Tanzar
 */
public class GameBoard {
    
    private BoardSpace[][] fields;
    private int height;
    private int width;
    private int fieldHeight;
    private int fieldWidth;
    
    public GameBoard(int widthInFields, int heightInFields, int fieldWidth, int fieldHeight, Terrain defaultTerrain){
        this.fields = new BoardSpace[widthInFields][heightInFields];
        this.height = heightInFields;
        this.width = widthInFields;
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        for(int x = 0; x < widthInFields; x++){
            for(int y = 0; y < heightInFields; y++){
                BoardSpace currentField = new BoardSpace(x, y, defaultTerrain);
                this.fields[x][y] = currentField;
            }
        }
    }

    public BoardSpace[][] getFields() {
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
    
    public void setField(int x, int y, BoardSpace field){
        this.fields[x][y] = field;
    }
    
    public BoardSpace getField(int x, int y){
        return this.fields[x][y];
    }
}
