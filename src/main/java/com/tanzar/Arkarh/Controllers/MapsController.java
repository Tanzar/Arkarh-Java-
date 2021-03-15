/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tanzar.Arkarh.Map.Exceptions.GenerationException;
import com.tanzar.Arkarh.Map.Map;
import com.tanzar.Arkarh.Services.GameBoardService;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @RequestMapping(value = "/newBoard", method = RequestMethod.POST)
    public String createNewBoard(@RequestBody String boardParameters) throws GenerationException{
        Gson jsonMaker = new Gson();
        JsonObject parameters = jsonMaker.fromJson(boardParameters, JsonObject.class);
        Integer width = parameters.get("width").getAsInt();
        Integer height = parameters.get("height").getAsInt();
        Integer seed = parameters.get("seed").getAsInt();
        Map newBoard = gameboardService.newMap(seed, width, height);
        String result = jsonMaker.toJson(newBoard);
        return result;
    }
    
    @RequestMapping(value = "/getAssets", method = RequestMethod.GET)
    public String getAllTerrains(){
        return gameboardService.getAssets();
    }
}
