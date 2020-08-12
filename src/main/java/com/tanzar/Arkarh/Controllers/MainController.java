/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
    @RequestMapping("/battleSim")
    public String worldEdit(Model model){
        return "battleSim";
    }
}
