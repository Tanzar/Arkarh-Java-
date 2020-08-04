/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh;

import com.tanzar.Arkarh.GamePlay.Battlefield.Battlefield;
import com.tanzar.Arkarh.GamePlay.Battlefield.Position;
import com.tanzar.Arkarh.GamePlay.Battlefield.Side;
import com.tanzar.Arkarh.GamePlay.Equipment.Equipment;
import com.tanzar.Arkarh.GamePlay.Equipment.Slot;
import com.tanzar.Arkarh.GamePlay.Units.Army;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;
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
        int width = 20;
        Army attack = this.attacker();
        Army def = this.defender();
        
        Battlefield battlefield = new Battlefield(width, attack, def);
        
        Side side = new Side(this.defender(), width);
        Unit unit = side.getUnit(new Position(1, true));
        Position[] positions = side.calculateTargetsPositions(unit);
        
        
        System.out.println(battlefield.toString());
        
    }
    
    private Army defender(){
        int flankers = 4;
        int warriors = 20;
        int mages = 8;
        int shooters = 8;
        Army army = new Army();
        for(int i = 0; i < flankers; i++){
            Unit unit = new Unit();
            unit.setRole(Role.flanker);
            unit.changeRange(2);
            army.addUnit(unit);
        }
        for(int i = 0; i < warriors; i++){
            Unit unit = new Unit();
            unit.setRole(Role.warrior);
            army.addUnit(unit);
        }
        for(int i = 0; i < mages; i++){
            Unit unit = new Unit();
            unit.setRole(Role.mage);
            army.addUnit(unit);
        }
        for(int i = 0; i < shooters; i++){
            Unit unit = new Unit();
            unit.setRole(Role.shooter);
            army.addUnit(unit);
        }
        return army;
    }
    
    
    private Army attacker(){
        int flankers = 10;
        int warriors = 10;
        int mages = 4;
        int shooters = 10;
        Army army = new Army();
        for(int i = 0; i < flankers; i++){
            Unit unit = new Unit();
            unit.setRole(Role.flanker);
            unit.changeSpeed(9);
            army.addUnit(unit);
        }
        for(int i = 0; i < warriors; i++){
            Unit unit = new Unit();
            unit.setRole(Role.warrior);
            unit.changeSpeed(4);
            army.addUnit(unit);
        }
        for(int i = 0; i < mages; i++){
            Unit unit = new Unit();
            unit.setRole(Role.mage);
            
            army.addUnit(unit);
        }
        for(int i = 0; i < shooters; i++){
            Unit unit = new Unit();
            unit.setRole(Role.shooter);
            unit.changeSpeed(7);
            army.addUnit(unit);
        }
        return army;
    }
    
}
