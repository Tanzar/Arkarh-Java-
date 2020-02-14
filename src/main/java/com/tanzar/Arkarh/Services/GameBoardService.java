/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Services;

import com.google.gson.Gson;
import com.tanzar.Arkarh.Containers.FieldContainer;
import com.tanzar.Arkarh.Containers.TerrainContainer;
import com.tanzar.Arkarh.Containers.MapContainer;
import com.tanzar.Arkarh.DAO.FieldDAO;
import com.tanzar.Arkarh.DAO.TerrainDAO;
import com.tanzar.Arkarh.DAO.MapDAO;
import com.tanzar.Arkarh.Elements.BoardSpace;
import com.tanzar.Arkarh.Elements.GameBoard;
import com.tanzar.Arkarh.Entities.Field;
import com.tanzar.Arkarh.Entities.Terrain;
import com.tanzar.Arkarh.Entities.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tanzar
 */
@Service
public class GameBoardService {
    
    @Autowired
    private MapDAO mapDAO;
    
    @Autowired
    private FieldDAO fieldDAO;
    
    @Autowired
    private TerrainDAO terrainDAO;
    
    private int fieldSize = 50;
    
    public MapContainer getMapList(){
        return mapDAO.getAll();
    }
    
    public GameBoard prepareGameBoard(Integer mapID){
        Map gameMap = mapDAO.getById(mapID);
        TerrainContainer terrains = terrainDAO.getAll();
        FieldContainer fields = fieldDAO.getByMapId(mapID);
        GameBoard newBoard = new GameBoard(gameMap.getWidth(), gameMap.getHeight(), this.fieldSize, this.fieldSize);
        Field[] fieldsArray = fields.toArray();
        for(Field field: fieldsArray){
            Terrain terrain = terrains.getById(field.getIdTerrain());
            BoardSpace boardSpace = prepareField(field, terrain);
            newBoard.setField(field.getX(), field.getY(), boardSpace);
        }
        return newBoard;
    }
    
    private BoardSpace prepareField(Field field, Terrain terrain){
        BoardSpace boardSpace = new BoardSpace(field.getX(), field.getY());
        boardSpace.setTerrain(terrain);
        return boardSpace;
    }
    
    public GameBoard prepareEmptyBoard(int width, int height, int fieldsWidth, int fieldsHeight){
        GameBoard newBoard = new GameBoard(width, height, fieldsWidth, fieldsHeight);
        return newBoard;
    }
    
    public String getTerrains(){
        TerrainContainer terrains = terrainDAO.getAll();
        Terrain[] terrainsArray = new Terrain[terrains.size()];
        for(int i = 0; i < terrainsArray.length; i++){
            terrainsArray[i] = terrains.get(i);
        }
        Gson jsonFormatter = new Gson();
        String result = jsonFormatter.toJson(terrainsArray);
        return result;
    }
}
