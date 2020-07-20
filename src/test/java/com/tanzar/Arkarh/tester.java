/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh;

import com.tanzar.Arkarh.Map.Biomes.BiomeGenerator;
import com.tanzar.Arkarh.Map.Exceptions.GenerationException;
import com.tanzar.Arkarh.Map.MapGenerator;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Tanzar
 */
public class tester {
    
    @Test
    public void test(){
        MapGenerator gener = new MapGenerator(100, 100);
        try {
            gener.generate(1357908642);
        }
        catch (GenerationException ex) {
        }
    }
}
