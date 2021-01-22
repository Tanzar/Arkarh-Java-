/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Tanzar
 */
@Controller
public class MainController {
    
    @RequestMapping("/")
    public String index(Model model){
        return "index";
    }
    
    @RequestMapping("/mapGenerator")
    public String mapGenerator(Model model){
        return "mapGenerator";
    }
    
    @RequestMapping("/battleSimEditor")
    public String battleSimEditor(Model model){
        return "BattleSimEditor";
    }
    
    @RequestMapping("/battleSim")
    public String battleSim(Model model){
        return "battleSim";
    }
    
    @RequestMapping("/unitEditor")
    public String unitEdit(Model model){
        return "unitEdit";
    }
    
    @RequestMapping("/artifactsEditor")
    public String artifactsEdit(Model model){
        return "artifactsEditor";
    }
    
    @RequestMapping("/test")
    public String test(Model model){
        return "testPage";
    }
}
