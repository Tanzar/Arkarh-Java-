/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Services;

import com.google.gson.Gson;
import com.tanzar.Arkarh.Containers.MapContainer;
import com.tanzar.Arkarh.DAO.FieldDAO;
import com.tanzar.Arkarh.DAO.MapDAO;
import com.tanzar.Arkarh.Elements.Assets;
import com.tanzar.Arkarh.Elements.GameBoard;
import com.tanzar.Arkarh.Map.Exceptions.GenerationException;
import com.tanzar.Arkarh.Map.MapGenerator;
import com.tanzar.Arkarh.Map.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("assets")
    private Assets assets;
    
    private int fieldSize = 50;
    private int defaultTerrainIndex = 1;
    
    public MapContainer getMapList(){
        return mapDAO.getAll();
    }
    
    public Map newMap(int seed, int width, int height) throws GenerationException{
        MapGenerator generator = new MapGenerator(width, height);
        Map newMap;
        if(seed == 0){
            newMap = generator.generate();
        }
        else{
            newMap = generator.generate(seed);
        }
        return newMap;
    }
    
    public GameBoard newBoard(int width, int height){
        GameBoard newBoard = new GameBoard(width, height, this.defaultTerrainIndex);
        return newBoard;
    }
    
    public String getAssets(){
        Gson jsonFormatter = new Gson();
        String result = jsonFormatter.toJson(this.assets);
        return result;
    }
    
}
