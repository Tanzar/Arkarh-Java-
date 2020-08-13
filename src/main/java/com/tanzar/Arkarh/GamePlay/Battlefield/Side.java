/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Battlefield;

import com.tanzar.Arkarh.GamePlay.CombatLog.Actions;
import com.tanzar.Arkarh.GamePlay.CombatLog.BattleSide;
import com.tanzar.Arkarh.GamePlay.CombatLog.CombatReport;
import com.tanzar.Arkarh.GamePlay.CombatLog.ReportEntry;
import com.tanzar.Arkarh.GamePlay.Units.Army;
import com.tanzar.Arkarh.GamePlay.Units.AttackType;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author spako
 */
public class Side {
    private int width;
    private int armyFrontWidth;
    private int sideSize;
    private Army army;
    private BattleSide battleSide;
    
    private Unit front[];
    private Unit back[];
    
    private Reserves reserves;
    
    public Side(Army army, int width, BattleSide side){
        this.battleSide = side;
        this.army = army;
        this.width = width;
        this.front = new Unit[width];
        this.back = new Unit[width];
        this.reserves = new Reserves();
        this.sideSize = army.size();
        this.army.setSide(side);
        this.formNewFront(army);
        this.formNewBack(army);
        this.centerArmyOld();
        this.setupReserves(army);
        this.fixUnitsPositions();
    }
    
    private void formNewFront(Army army){
        Units flankers = army.getUnits(Role.flanker);
        if(flankers.size() < this.width){
            this.fillFrontWithMix(army);
        }
        else{
            this.fillFrontWithFlankers(army);
        }
    }
    
    private void fillFrontWithMix(Army army){
        Units flankers = army.getUnits(Role.flanker);
        Units warriors = army.getUnits(Role.warrior);
        int leftFlankSize = (int) Math.ceil(flankers.size()/2);
        int rightFlankSize = flankers.size() - leftFlankSize;
        int index = 0;
        while(index < leftFlankSize){
            this.front[index] = flankers.getAndRemoveFirst();
            this.front[index].setPosition(index);
            index++;
        }
        while(index < (this.width - rightFlankSize) && !warriors.isEmpty()){
            this.front[index] = warriors.getAndRemoveFirst();
            this.front[index].setPosition(index);
            index++;
        }
        while(index < this.width && !flankers.isEmpty()){
            this.front[index] = flankers.getAndRemoveFirst();
            this.front[index].setPosition(index);
            index++;
        }
    }
    
    private void fillFrontWithFlankers(Army army){
        Units flankers = army.getUnits(Role.flanker);
        for(int i = 0; i < this.width; i++){
            this.front[i] = flankers.getAndRemoveFirst();
            this.front[i].setPosition(i);
        }
    }
    
    private void formNewBack(Army army){
        int backWidth = this.countBackWidth();
        this.armyFrontWidth = backWidth;
        Units mages = army.getUnits(Role.mage);
        if(mages.size() >= backWidth){
            this.fillBackWithMages(army, backWidth);
        }
        else{
            this.fillBackWithMix(army, backWidth);
        }
    }
    
    private int countBackWidth(){
        int count = 0;
        boolean run = true;
        while(run && count < this.width){
            if(this.front[count] == null){
                run = false;
            }
            else{
                count++;
            }
        }
        return count;
    }
    
    private void fillBackWithMages(Army army, int width){
        Units mages = army.getUnits(Role.mage);
        for(int i = 0; i < width; i++){
            this.back[i] = mages.getAndRemoveFirst();
            this.back[i].setPosition(i);
        }
    }
    
    private void fillBackWithMix(Army army, int width){
        Units mages = army.getUnits(Role.mage);
        Units shooters = army.getUnits(Role.shooter);
        int spaceForShooters = width - mages.size();
        int leftSideShooters = this.countLeftBackFlank(shooters.size(), spaceForShooters);
        int rightSideShooters = this.countRightBackFlank(shooters.size(), spaceForShooters, leftSideShooters);
        int index = 0;
        while(index < leftSideShooters){
            this.back[index] = shooters.getAndRemoveFirst();
            this.back[index].setPosition(index);
            index++;
        }
        while(index < width - rightSideShooters && !mages.isEmpty()){
            this.back[index] = mages.getAndRemoveFirst();
            this.back[index].setPosition(index);
            index++;
        }
        while(index < width && !shooters.isEmpty()){
            this.back[index] = shooters.getAndRemoveFirst();
            this.back[index].setPosition(index);
            index++;
        }
    }
    
    private int countLeftBackFlank(int shootersSize, int spaceOnBackFlanks){
        if(shootersSize > spaceOnBackFlanks){
            return (int) Math.ceil(spaceOnBackFlanks/2);
        }
        else{
            return (int) Math.ceil(shootersSize/2);
        }
    }
    
    private int countRightBackFlank(int shootersSize, int spaceOnBackFlanks, int spaceOnLeftFlank){
        if(shootersSize > spaceOnBackFlanks){
            return spaceOnBackFlanks - spaceOnLeftFlank;
        }
        else{
            return shootersSize - spaceOnLeftFlank;
        }
    }
    
    private void centerArmyOld(){
        int freeFront = this.countFree(this.front);
        int freeBack = this.countFree(this.back);
        int halfFront = (int) Math.floor(freeFront/2);
        int halfBack = (int) Math.floor(freeBack/2);
        for(int i = this.width - 1; i >= 0; i--){
            if(i >= halfFront){
                this.front[i] = this.front[i - halfFront];
            }
            else{
                this.front[i] = null;
            }
            if(i >= halfBack){
                this.back[i] = this.back[i - halfBack];
            }
            else{
                this.back[i] = null;
            }
        }
    }
    //WIP new version - groups units to left than pushes to right
    private void centerArmy(){
        boolean searching = true;
        int count = 0;
        for(int i = 0; i < this.width; i++){
            if(searching){
                if(this.front[i] == null){
                    
                }
            }
            else{
                
            }
        }
    }
    
    private int countFree(Unit line[]){
        int count = 0;
        for(int i = 0; i < line.length; i++){
            if(line[i] == null){
                count++;
            }
        }
        return count;
    }
    
    private void setupReserves(Army army){
        Role[] roles = Role.values();
        for(int i = 0; i < Role.countRoles(); i++){
            Units units = army.getUnits(roles[i]);
            for(int j = 0; j < units.size(); j++){
                Unit unit = units.get(j);
                this.reserves.addUnit(unit);
            }
        }
    }
    
    private void fixUnitsPositions(){
        for(int i = 0; i < this.width; i++){
            if(this.front[i] != null){
                this.front[i].setPosition(i);
            }
            
            if(this.back[i] != null){
                this.back[i].setPosition(i);
            }
        }
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int[] getUnitTargetsPositions(Unit attacker){
        int position = attacker.getPosition();
        int range = attacker.getRange();
        int[] firstPositions = new int[2 * range + 1];
        int count = 0;
        for(int i = 0; i <= firstPositions.length; i++){
            firstPositions[i] = i + position - range;
            if(firstPositions[i] >= 0){
                count++;
            }
        }
        int[] finalPositions = new int[count];
        int index = 0;
        for(int i = 0; i < firstPositions.length; i++){
            if(firstPositions[i] <= 0 && firstPositions[i] < this.width){
                finalPositions[index] = firstPositions[i];
                index++;
            }
        }
        return finalPositions;
    }
    
    public boolean isSideCappableToFight(){
        if(this.isPercentageCappableToFight(20)){
            return true;
        }
        else{
            return false;
        }
    }
    
    private boolean isPercentageCappableToFight(int percentage){
        if(percentage < 1 || percentage > 100){
            return false;
        }
        int cappableToFight = 0;
        for(int i = 0; i < this.width; i++){
            if(this.front[i] != null && this.front[i].isCapableToFight()){
                cappableToFight++;
            }
            if(this.back[i] != null && this.back[i].isCapableToFight()){
                cappableToFight++;
            }
        }
        cappableToFight += this.reserves.countCappableToFight();
        if(cappableToFight >= (0.01 * percentage * this.sideSize)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean reorganizeLines(CombatReport report, boolean somethingWasReinforced){
        boolean flag = somethingWasReinforced;
        for(int i = 0; i < this.width; i++){
            if(this.front[i] != null){
                flag = this.reinforce(this.front, i, report, flag);
            }
            if(this.back[i] != null){
                flag = this.reinforce(this.back, i, report, flag);
            }
        }
        this.fixUnitsPositions();
        return flag;
    }
    
    private boolean reinforce(Unit[] line, int position, CombatReport report, boolean somethingWasReinforced){
        boolean flag = somethingWasReinforced;
        if(!line[position].isCapableToFight()){
            if(!somethingWasReinforced){
                report.nextWeave();
                flag = true;
            }
            Unit retreat = line[position];
            report.unitRemovalFromBattlefield(retreat);
            retreat.setPosition(-1);
            line[position] = null;
            this.reserves.addUnit(retreat);
            Role currentRole = retreat.getRole();
            Unit unit = this.reserves.getUnit(currentRole);
            if(unit == null){
                unit = this.reserves.getUnit(currentRole.nextRoleInLine());
            }
            if(unit != null){
                this.reserves.remove(unit);
                unit.setPosition(position);
                report.reinforcing(unit, position);
                line[position] = unit;
            }
        }
        return flag;
    }
    
    public Units getFieldedUnitsOrderedBySpeed(){
        Units units = this.getFieldedUnits();
        units.orderBySpeed();
        return units;
    }
    
    private Units getFieldedUnits(){
        Units units = new Units();
        for(int i = 0; i < this.front.length; i++){
            if(this.front[i] != null){
                units.add(this.front[i]);
            }
            if(this.back[i] != null){
                units.add(this.back[i]);
            }
        }
        return units;
    }
    
    public Units getTargets(Unit source){
        int center = source.getPosition();
        int range = source.getRange();
        Position[] positions = this.calculateTargetsPositions(center, range);
        Units targets = this.getTargetsList(source, positions);
        return targets;
    }
    
    private Position[] calculateTargetsPositions(int center, int range){
        Position[] positions = new Position[2 * (2 * range + 1)];
        int index = 0;
        int currentRange = 1;
        while(index < positions.length){
            if(index == 0){
                positions[index] = new Position(center, true);
                positions[index + 1] = new Position(center, false);
                index = index + 2;
            }
            else{
                positions[index] = new Position(center + currentRange, true);
                positions[index + 1] = new Position(center - currentRange, true);
                positions[index + 2] = new Position(center + currentRange, false);
                positions[index + 3] = new Position(center - currentRange, false);
                index = index + 4;
                currentRange++;
            }
        }
        return positions;
    }
    
    private Units getTargetsList(Unit source, Position[] positions){
        if(this.battleSide == source.getSide()){
            return this.getAllTargets(positions);
        }
        else{
            return this.getUnitsOnPositions(source, positions);
        }
    }
    
    private Units getUnitsOnPositions(Unit attacker, Position[] positions){
        AttackType attackType = attacker.getAttackType();
        switch(attackType){
            case single:
                return this.getFirstTarget(positions);
            case cleave:
                return this.getCleaveTargets(positions);
            case splash:
                return this.getAllTargets(positions);
            default:
                return new Units();
        }
    }
    
    private Units getFirstTarget(Position[] positions){
        Units target = new Units();
        for(int i = 0; i < positions.length; i++){
            Position position = positions[i];
            Unit unit = this.getUnit(position);
            if(unit != null){
                target.add(unit);
                break;
            }
        }
        return target;
    }
    
    private Units getCleaveTargets(Position[] positions){
        Units targets = new Units();
        for(int i = 0; i < positions.length; i++){
            Position position = positions[i];
            if(position.isValid(this.width - 1)){
                int index = position.getPositionInLine();
                Unit target = null;
                if(position.isOnFrontLine()){
                    target = this.front[index];
                }
                else{
                    if(this.front[index] == null){
                        target = this.back[index];
                    }
                }
                if(target != null){
                    targets.add(target);
                }
            }
        }
        return targets;
    }
    
    private Units getAllTargets(Position[] positions){
        Units target = new Units();
        for(int i = 0; i < positions.length; i++){
            Position position = positions[i];
            Unit unit = this.getUnit(position);
            if(unit != null){
                target.add(unit);
            }
        }
        return target;
    }
    
    
    
    public Unit getUnit(Position position){
        if(position.isValid(this.width - 1)){
            int index = position.getPositionInLine();
            if(position.isOnFrontLine()){
                return this.front[index];
            }
            else{
                return this.back[index];
            }
        }
        else{
            return null;
        }
    }
    
    @Override
    public String toString(){
        String result = "";
        for(int i = 0; i < this.front.length; i++){
            result += "[";
            if(this.front[i] != null){
                result += this.front[i].getRole();
            }
            else{
                result += "0";
            }
            result += "]";
        }
        result += System.lineSeparator();
        for(int i = 0; i < this.back.length; i++){
            result += "[";
            if(this.back[i] != null){
                result += this.back[i].getRole();
            }
            else{
                result += "0";
            }
            result += "]";
        }
        result += System.lineSeparator();
        result += "Reserves: ";
        result += System.lineSeparator();
        for(int i = 0; i < this.reserves.size(); i++){
            Unit unit = this.reserves.get(i);
            result += "[" + unit.getRole() + "]";
        }
        result += System.lineSeparator();
        result += System.lineSeparator();
        return result;
    }
    
}
