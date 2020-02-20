/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tanzar.Arkarh.Containers.MapContainer;
import com.tanzar.Arkarh.DAO.MapDAO;
import com.tanzar.Arkarh.Elements.GameBoard;
import com.tanzar.Arkarh.Entities.Map;
import com.tanzar.Arkarh.Services.GameBoardService;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Tanzar
 */
@RestController
public class MapsController {
    
    @Autowired
    private GameBoardService gameboardService;
    
    @RequestMapping(value = "/fields/{id}", method = RequestMethod.GET)
    public String getMapFields(@PathVariable String id){
        Gson jsonMaker = new Gson();
        GameBoard gameBoard = gameboardService.prepareGameBoard(Integer.parseInt(id));
        String result = jsonMaker.toJson(gameBoard);
        return result;
    }
    
    @RequestMapping(value = "/newBoard", method = RequestMethod.POST)
    public String createNewBoard(@RequestBody String boardParameters){
        Gson jsonMaker = new Gson();
        JsonObject parameters = jsonMaker.fromJson(boardParameters, JsonObject.class);
        Integer width = parameters.get("width").getAsInt();
        Integer height = parameters.get("height").getAsInt();
        GameBoard newBoard = gameboardService.prepareEmptyBoard(width, height);
        String result = jsonMaker.toJson(newBoard);
        return result;
    }
    
    @RequestMapping(value = "/allTerrains", method = RequestMethod.GET)
    public String getAllTerrains(){
        return gameboardService.getTerrains();
    }
    
    @RequestMapping(value = "/allObstacles", method = RequestMethod.GET)
    public String getAllObstacles(){
        return gameboardService.getObstacles();
    }
}
