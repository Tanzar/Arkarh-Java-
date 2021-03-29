/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Controllers;

import com.tanzar.Arkarh.Services.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author spako
 */
@RestController
public class BattleController {
    
    @Autowired
    private BattleService battleService;
    
    @RequestMapping(value="/Simulate", method=RequestMethod.POST, consumes="application/json; charset=UTF-8")
    public String add(@RequestBody String form){
        String result = "";
        try{
            result = this.battleService.battleSimulation(form);
        }
        catch(Exception ex){
            int k = 0;
        }
        return result;
    }
}
