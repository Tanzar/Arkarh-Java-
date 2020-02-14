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
        Gson jsonMaker = new Gson();/*
        int width = 30;
        int height = 30;
        GameBoard maptest = new GameBoard(width, height, 50, 50);
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if((x == 0 || x == height - 1) || (y == 0 || y == width - 1)){
                    maptest.setField(x, y, "water", true);
                }
                else{
                    Random rand = new Random();
                    int kod = rand.nextInt(7);
                    if(kod == 0){
                        maptest.setField(x, y, "ash", true);
                    }
                    if(kod == 1){
                        maptest.setField(x, y, "ash", true);
                    }
                    if(kod == 2){
                        maptest.setField(x, y, "ash", true);
                    }
                    if(kod == 3){
                        maptest.setField(x, y, "lava", true);
                    }
                    if(kod == 4){
                        maptest.setField(x, y, "mountains", true);
                    }
                    if(kod == 5){
                        maptest.setField(x, y, "sand", true);
                    }
                    if(kod == 6){
                        maptest.setField(x, y, "sand", true);
                    }
                }
            }
        }
        String result =jsonMaker.toJson(maptest);*/
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
        Integer fieldsWidth = parameters.get("fieldsWidth").getAsInt();
        Integer fieldsHeight = parameters.get("fieldsHeight").getAsInt();
        GameBoard newBoard = gameboardService.prepareEmptyBoard(width, height, fieldsWidth, fieldsHeight);
        String result = jsonMaker.toJson(newBoard);
        return result;
    }
    
    @RequestMapping(value = "/allTerrains", method = RequestMethod.GET)
    public String getAllTerrains(){
        return gameboardService.getTerrains();
    }
}
