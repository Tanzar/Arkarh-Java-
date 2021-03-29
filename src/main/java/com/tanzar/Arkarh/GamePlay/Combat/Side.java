/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat;

import com.tanzar.Arkarh.GamePlay.Units.Army;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author spako
 */
public class Side {
    private final int percentageCappable = 0;
    
    private int width;
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
        this.setupLines(army);
        this.setupReserves(army);
        this.reserves.orderBySpeed();
    }
    
    private void setupLines(Army army){
        this.setupLine(army, this.front, Role.flanker, Role.warrior, false, this.width);
        this.setupLine(army, this.back, Role.mage, Role.shooter, true, this.width);
        this.centerLines();
    }
    
    private void setupLine(Army army, Unit[] line, Role priority, Role second, boolean prioritiesInCenter, int limit){
        Units priorityUnits = army.getUnits(priority);
        Units secondUnits = army.getUnits(second);
        Units priorityUnitsToPlace = this.getUnitsToPlace(priorityUnits, limit);
        int remainingWidth = limit - priorityUnitsToPlace.size();
        Units secondUnitsToPlace = this.getUnitsToPlace(secondUnits, remainingWidth);
        if(prioritiesInCenter){
            this.initLine(line, secondUnitsToPlace, priorityUnitsToPlace);
        }
        else{
            this.initLine(line, priorityUnitsToPlace, secondUnitsToPlace);
        }
    }
    
    private Units getUnitsToPlace(Units units, int availableWidth){
        units.orderBySpeed();
        Units unitsToPlace = new Units();
        int count = 0;
        while(count < availableWidth && (count < units.size())){
            Unit unit = units.get(count);
            if(unit != null){
                count++;
                unitsToPlace.add(unit);
            }
        }
        return unitsToPlace;
    }
    
    private int initLine(Unit[] line, Units sides, Units center){
        int sum = sides.size() + center.size();
        int x = 0;
        for(int i = 0; i < sum; i++){
            Unit unit = null;
            int index = 0;
            if(i % 2 == 1){
                index = (int) Math.floor(this.width / 2) - x;
            }
            else{
                index = (int) Math.floor(this.width / 2) + x;
                x++;
            }
            if(center.isEmpty()){
                unit = sides.getAndRemoveFirst();
            }
            else{
                unit = center.getAndRemoveFirst();
            }
            this.placeInLine(line, index, unit);
        }
        return sum;
    }
    
    private void placeInLine(Unit[] line, int i, Unit unit){
        line[i] = unit;
        if(unit!= null){
            unit.getStatus().setPosition(i);
        }
    }
    
    private void setupReserves(Army army){
        Units units = army.getAll();
        Unit[] unitsArray = units.toArray();
        for(Unit unit: unitsArray){
            if(!this.isFielded(unit)){
                this.reserves.addUnit(unit);
            }
        }
    }
    
    private boolean isFielded(Unit unit){
        if(unit.getPosition() != -1){
            return true;
        }
        else{
            return false;
        }
    }
    
    public BattleSide getBattleSide(){
        return this.battleSide;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public boolean isSideCappableToFight(){
        if(this.isPercentageCappableToFight(this.percentageCappable)){
            return true;
        }
        else{
            return false;
        }
    }
    
    private boolean isPercentageCappableToFight(int percentage){
        if(percentage < 0 || percentage > 100){
            return false;
        }
        int cappableToFight = 0;
        for(int i = 0; i < this.width; i++){
            if(this.front[i] != null && this.front[i].isCappableToFight()){
                cappableToFight++;
            }
            if(this.back[i] != null && this.back[i].isCappableToFight()){
                cappableToFight++;
            }
        }
        cappableToFight += this.reserves.countCappableToFight();
        if(cappableToFight > (0.01 * percentage * this.sideSize)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public Units reorganizeLines(){
        Units affectedUnits = new Units();
        int x = 0;
        for(int i = 0; i < this.width; i++){
            if(i % 2 == 1){
                this.reinforcePosition(affectedUnits, -1 * x);
            }
            else{
                this.reinforcePosition(affectedUnits, x);
                x++;
            }
        }
        this.centerLines();
        return affectedUnits;
    }
    
    private void reinforcePosition(Units affectedUnits, int x){
        int index = (int) Math.round(this.width / 2) + x;
        this.reinforce(this.front, index, affectedUnits, true);
        this.reinforce(this.back, index, affectedUnits, false);
    }
    
    private void reinforce(Unit[] line, int index, Units affectedUnits, boolean isFront){
        Unit unit = null;
        if(line[index] != null){
            unit = this.replaceUnit(line[index], index, affectedUnits);
        }
        else{
            unit = this.checkReserves(isFront, index, affectedUnits);
        }
        line[index] = unit;
    }
    
    private Unit replaceUnit(Unit currentUnit, int index, Units affectedUnits){
        if(currentUnit.isNotCappableToFight()){
            this.reserves.addUnit(currentUnit);
            boolean isFront = currentUnit.isFront();
            Unit reinforcement = this.checkReserves(isFront, index, affectedUnits);
            return reinforcement;
        }
        return currentUnit;
    }
    
    private Unit checkReserves(boolean isFront, int index, Units affectedUnits){
        Unit reinforcement = this.reserves.get(isFront);
        if(reinforcement != null){
            reinforcement.setPosition(index);
            affectedUnits.add(reinforcement);
        }
        return reinforcement;
    }
    
    public Units getFieldedUnitsOrderedBySpeed(){
        Units units = this.getFieldedUnits();
        units.orderBySpeed();
        return units;
    }
    
    public Units getFieldedUnits(){
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
    
    public Units getDeadReserves(){
        Units units = new Units();
        Units reservesList = this.getReserves();
        for(Unit unit: reservesList.toArray()){
            if(unit.isNotAlive()){
                units.add(unit);
            }
        }
        return units;
    }
    
    public void updateUnitsStatus(){
        for(int i = 0; i < this.front.length; i++){
            if(this.front[i] != null){
                this.front[i].updateStatus();
            }
            if(this.back[i] != null){
                this.back[i].updateStatus();
            }
        }
    }
    
    public Units getDeadFieldedUnits(){
        Units units = new Units();
        for(int i = 0; i < this.front.length; i++){
            if(this.front[i] != null){
                if(!this.front[i].isAlive()){
                    units.add(this.front[i]);
                }
            }
            if(this.back[i] != null){
                if(!this.back[i].isAlive()){
                    units.add(this.back[i]);
                }
            }
        }
        return units;
    }
    
    public Unit getUnit(int position){
        if(position < this.width && position > -1){
            if(this.front[position] != null){
                return this.front[position];
            }
            else{
                return this.back[position];
            }
        }
        else{
            return null;
        }
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
    
    public boolean isAnyOnPosition(int position){
        if(position < this.width && position > -1){
            if(this.front[position] != null){
                return true;
            }
            if(this.back[position] != null){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public Units getReserves(){
        return this.reserves.getAll();
    }
    
    @Override
    public String toString(){
        String result = "";
        for(int i = 0; i < this.front.length; i++){
            result += "[";
            if(this.front[i] != null){
                result += this.front[i].getPosition();
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
                result += this.back[i].getPosition();
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
            result += "[" + unit.getAssetName() + "]";
        }
        result += System.lineSeparator();
        result += System.lineSeparator();
        return result;
    }
    
    private void centerLines(){
        int frontWidth = this.countLineUnits(this.front);
        int backWidth = this.countLineUnits(this.back);
        if(frontWidth <= this.width - 1){
            this.front = this.centerLine(this.front, frontWidth);
        }
        if(backWidth <= this.width - 1){
            this.back = this.centerLine(this.back, backWidth);
        }
    }
    
    private int countLineUnits(Unit[] line){
        int count = 0;
        for(int i = 0; i < line.length; i++){
            if(line[i] != null){
                count++;
            }
        }
        return count;
    }
    
    private Unit[] centerLine(Unit[] line, int width){
        int pushRange = (int) Math.round(((double) (this.width - width)) / 2);
        if(pushRange > 0){
            Unit[] pushedLine = new Unit[line.length];
            int index = pushRange;
            for(int i = 0; i < line.length; i++){
                if(line[i] != null && index < line.length){
                    Unit unit = line[i];
                    pushedLine[index] = unit;
                    unit.setPosition(index);
                    index++;
                }
            }
            return pushedLine;
        }
        else{
            return line;
        }
    }
    
    private void fixUnitsPositions(){
        for(int i = 0; i < this.width; i++){
            if(this.front[i] != null){
                this.front[i].getStatus().setPosition(i);
            }
            
            if(this.back[i] != null){
                this.back[i].getStatus().setPosition(i);
            }
        }
    }
    
    public void addUnit(Unit unit){
        unit.setSide(this.battleSide);
        this.army.addUnit(unit);
        this.reserves.addUnit(unit);
    }
    
}
