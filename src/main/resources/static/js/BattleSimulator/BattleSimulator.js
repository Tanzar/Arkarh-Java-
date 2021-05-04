/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function Simulation(attackersCanvas, defendersCanvas, battlefieldCanvas, combatlogTextArea, armiesData, simulationControls){
    this.combatlog = new CombatLog(combatlogTextArea);
    
    this.getAssets = function(){
        var response = $.ajax({
            async: false,
            type: "GET",
            contentType:"application/json; charset=utf-8",
            dataType: "json",
            url: "/getUnitsAssets",
            success: function (response) {
                return response;
                console.log();
            },
            error : function(response) {
                console.log(response);
                return undefined;
            }
        });
        return new Assets(response.responseJSON);
    }
    this.assets = this.getAssets();
    
    this.width;
    this.entries;
    this.getSimulation = function(data){
        var response = $.ajax({
            async: false,
            type: "POST",
            contentType:"application/json",
            data: JSON.stringify(data),
            dataType: "json",
            url: '/Simulate',
            success: function (response) {
                return response;
            },
            error : function(response) {
                console.log(response);
                return undefined;
            }
        });
        response = response.responseJSON;
        this.width = response.width;
        this.entries = new CombatEntries(response.entries);
        console.log(response);
    }
    this.getSimulation(armiesData);
    
    this.battlefield = new Battlefield(battlefieldCanvas, this.assets, this.width);
    
    
    this.attackersReserves = new Reserves(attackersCanvas, this.assets, 'attacker');
    this.defendersReserves = new Reserves(defendersCanvas, this.assets, 'defender');
    
    this.isTickMode = true;
    this.entries.setupStages(this.isTickMode);
    this.currentStage = 0;
    this.isAuto = false;
    this.isRunning = false;
    
    this.showSummary = function(){
        var lines = this.entries.summary.formText();
        this.combatlog.clear();
        for(var i = 0; i < lines.length; i++){
            this.combatlog.newLogLine(lines[i]);
        }
    }
    
    this.changeStage = function(stage){
        this.currentStage = stage;
        var entries = this.entries.getStageEntries(this.currentStage);
        this.battlefield.setupBattleField(entries, this.isTickMode);
        this.attackersReserves.setupReserves(entries);
        this.defendersReserves.setupReserves(entries);
        this.combatlog.clear();
        for(var i = 0; i < entries.length; i++){
            if(entries[i].entryGroup != 'attackersState' && entries[i].entryGroup != 'defendersState')
            this.combatlog.newLogLine(entries[i].entryText);
        }
    }
    this.changeStage(this.currentStage);
    
    this.nextStage = function(){
        var lastStage = this.entries.stagesCount() - 1;
        if(this.currentStage < lastStage){
            this.currentStage++;
            this.changeStage(this.currentStage);
        }
    }
    
    this.previousStage = function(){
        if(this.currentStage > 0){
            this.currentStage--;
            this.changeStage(this.currentStage);
        }
    }
    
    this.isLastStage = function(){
        var lastStage = this.entries.stagesCount() - 1;
        if(this.currentStage >= lastStage){
            return true;
        }
        return false;
    }
    
    this.changeTickMode = function(){
        this.isTickMode = !this.isTickMode;
        this.entries.setupStages(this.isTickMode);
        this.changeStage(0);
    }
    
    this.waitTime = 2000;
    var simulation = this;
    this.autoNextStage = function(){
        simulation.nextStage();
        if(simulation.isRunning && !simulation.isLastStage()){
            window.setTimeout(simulation.autoNextStage, simulation.waitTime);
        }
    }
    
    this.newButton = function(text, onClick){
        var button = document.createElement('button');
        button.innerHTML = text;
        button.onclick = onClick;
        return button;
    }
    
    this.tickModeButton
    this.autoModeButton;
    this.startButton;
    this.stopButton;
    this.summaryButton;
    this.setupTickModeButton = function(div, simulation){
        this.tickModeButton = this.newButton('Current mode: tick', function(){
            simulation.changeTickMode();
            if(simulation.isTickMode){
                this.innerHTML = 'Current mode: tick';
            }
            else{
                this.innerHTML = 'Current mode: per unit';
            }
        });
        var br = document.createElement('br');
        div.appendChild(this.tickModeButton);
        div.appendChild(br);
    }
    this.initControls = function(div){
        var simulation = this;
        this.tickModeButton = this.setupTickModeButton(div, simulation)
        this.autoModeButton = this.newButton('Current mode: Manual', function(){
            if(simulation.isAuto){
                simulation.isRunning = false;
                simulation.isAuto = false;
                this.innerHTML = 'Current mode: Manual';
            }
            else{
                simulation.isAuto = true;
                this.innerHTML = 'Current mode: Auto';
            }
        });
        var br = document.createElement('br');
        div.appendChild(this.autoModeButton);
        div.appendChild(br);
        this.startButton = this.newButton('Start / next', function(){
            if(simulation.isAuto){
                if(!simulation.isRunning){
                    simulation.isRunning = true;
                    simulation.autoNextStage();
                }
            }
            else{
                simulation.nextStage();
            }
        });
        br = document.createElement('br');
        div.appendChild(this.startButton);
        div.appendChild(br);
        this.stopButton = this.newButton('Stop / previous', function(){
            if(simulation.isAuto){
                simulation.isRunning = false;
            }
            else{
                simulation.previousStage();
            }
        });
        br = document.createElement('br');
        div.appendChild(this.stopButton);
        div.appendChild(br);
        this.summaryButton = this.newButton('Combat Summary', function(){
            if(!simulation.isAuto){
                simulation.showSummary();
            }
        });
        br = document.createElement('br');
        div.appendChild(this.summaryButton);
        div.appendChild(br);
    }
    this.initControls(simulationControls);
    
    this.draw = function(){
        this.attackersReserves.draw();
        this.battlefield.draw();
        this.defendersReserves.draw();
    }
    
    this.newFrame = function(){
        simulation.draw();
        requestAnimationFrame(simulation.newFrame);
    }
    
    requestAnimationFrame(simulation.newFrame);
}

function Battlefield(canvas, assets, combatWidth){
    this.canvas = canvas;
    this.assets = assets;
    this.width = combatWidth;
    
    this.initializeEmptyLines = function(width){
        var line = [];
        for(var i = 0; i < width; i++){
            line.push('none');
        }
        return line;
    }
    this.attackersFront = this.initializeEmptyLines(this.width);
    this.attackersBack = this.initializeEmptyLines(this.width);
    this.defendersFront = this.initializeEmptyLines(this.width);
    this.defendersBack = this.initializeEmptyLines(this.width);
    this.markedUnits = [];
    
    this.setupBattleField = function(entries, isTickMode){
        this.markedUnits = [];
        for(var i = 0; i < entries.length; i++){
            var entry = entries[i];
            if(entry.entryGroup == 'attackersState'){
                this.attackersFront = entry.frontLine;
                this.attackersBack = entry.backLine;
            }
            if(entry.entryGroup == 'defendersState'){
                this.defendersFront = entry.frontLine;
                this.defendersBack = entry.backLine;
            }
            if(!isTickMode){
                this.markUnits(entry);
            }
        }
    }
    
    this.markUnits = function(entry){
        var source = entry.source;
        var target = entry.target;
        if(source !== undefined){
            var sourcePosition = new Position(source.side, source.positionInLine, source.front);
            if(!this.isMarked(sourcePosition)){
                this.markedUnits.push(sourcePosition);
            }
        }
        if(target !== undefined){
            var targetPosition = new Position(target.side, target.positionInLine, target.front);
            if(!this.isMarked(targetPosition)){
                this.markedUnits.push(targetPosition);
            }
        }
    }
    
    this.draw = function(){
        var ctx = this.canvas.getContext("2d");
        ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        ctx.save();
        for(var index = 0; index < this.attackersFront.length; index++){
            this.drawUnits(ctx, index);
        }
        ctx.restore();
    }
    
    this.drawUnits = function(ctx, index){
        var scale = 50;
        var x = index * scale;
        var y = 0;
        var position = new Position('attacker', index, false);
        this.drawUnit(ctx, this.attackersBack[index], x, y, scale, position);
        y = y + 50;
        position = new Position('attacker', index, true);
        this.drawUnit(ctx, this.attackersFront[index], x, y, scale, position);
        y = y + 100;
        position = new Position('defender', index, true);
        this.drawUnit(ctx, this.defendersFront[index], x, y, scale, position);
        y = y + 50;
        position = new Position('defender', index, false);
        this.drawUnit(ctx, this.defendersBack[index], x, y, scale, position);
    }
    
    this.drawUnit = function(ctx, assetName, x, y, scale, position){
        var asset = this.assets.findUnit(assetName);
        if(assetName !== 'none'){
            ctx.drawImage(asset.img, x, y, scale, scale);                       // draw image at current position
        }
        if(this.isMarked(position)){
            ctx.drawImage(this.assets.markerMove, x, y, scale, scale);                       // draw image at current position
        }
    }
    
    this.isMarked = function(position){
        for(var i = 0; i < this.markedUnits.length; i++){
            if(position.isTheSameAs(this.markedUnits[i])){
                return true;
            }
        }
        return false;
    }
    
}

function Position(side, index, isFront){
    this.side = side;
    this.index = index;
    this.isFront = isFront;
    
    this.isTheSameAs = function(position){
        if(this.side == position.side){
            if(this.index == position.index){
                if(this.isFront == position.isFront){
                    return true;
                }
            }
        }
        return false;
    }
}

function Reserves(canvas, assets, side){
    this.canvas = canvas;
    this.assets = assets;
    this.side = side;
    this.reserves = [];
    this.reservesState = [];
    
    this.setupReserves = function(entries){
        for(var i = 0; i < entries.length; i++){
            var entry = entries[i];
            if(this.side == 'attacker'){
                if(entry.entryGroup == 'attackersState'){
                    this.reserves = entry.reserves;
                    this.reservesState = entry.reservesState;
                }
            }
            else{
                if(entry.entryGroup == 'defendersState'){
                    this.reserves = entry.reserves;
                    this.reservesState = entry.reservesState;
                }
            }
        }
    }
    
    this.draw = function(){
        var ctx = this.canvas.getContext("2d");
        ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        var scale = 25;
        var width = this.canvas.width;
        for(var index = 0; index < this.reserves.length; index++){
            ctx.save();
            var x = (index % (width/scale)) * scale;
            var y = Math.floor(index / (width/scale) ) * scale;
            var asset = this.assets.findUnit(this.reserves[index]);
            ctx.drawImage(asset.img, x, y, scale, scale);                       // draw image at current position
            if(this.reservesState[index] == 'dead'){
                ctx.drawImage(this.assets.markerDead, x, y, scale, scale);                       // draw image at current position
            }
            if(this.reservesState[index] == 'risen'){
                ctx.drawImage(this.assets.markerRisen, x, y, scale, scale);                       // draw image at current position
            }
            ctx.restore();
        }
    }
}

function CombatLog(combatTextArea){
    this.combatTextArea = combatTextArea;
    
    this.clear = function(){
        this.combatTextArea.value = "";
    }
    this.newLogLine = function(text){
        if(this.combatTextArea.value != ""){
            this.combatTextArea.value = this.combatTextArea.value + '\n' + text;
        }
        else{
            this.combatTextArea.value = text;
        }
    }
}

function CombatEntries(entries){
    this.entries = entries;
    this.stagesIndexes = [];
    this.summary = new Summary(entries);
    
    this.setupStages = function(isTickMode){
        if(isTickMode){
            this.setupTickModeStages();
        }
        else{
            this.setupPerUnitModeStages();
        }
    }
    
    this.setupTickModeStages = function(){
        this.stagesIndexes = [];
        for(var i = 0; i < this.entries.length; i++){
            if(this.entries[i].entryGroup == "tickStart" || this.entries[i].entryGroup == "reinforceStart"){
                this.stagesIndexes.push(i);
            }
        }
    }
    
    this.setupPerUnitModeStages = function(){
        this.stagesIndexes = [];
        var currentUnit = {
            front: true,
            positionInLine: -1,
            side: 'defender'
        };
        for(var i = 0; i < this.entries.length; i++){
            var entry = this.entries[i];
            if(entry.entryGroup == "tickStart" || entry.entryGroup == "reinforceStart"){
                this.stagesIndexes.push(i);
            }
            else{
                if(entry.entryGroup == "onAction" || entry.entryGroup == "onDeath" || entry.entryGroup == "onEntry"){
                    var source = entry.source;
                    if(currentUnit.front !== source.front || currentUnit.positionInLine !== source.positionInLine || currentUnit.side !== source.side){
                        this.stagesIndexes.push(i);
                        currentUnit = source
                    }
                }
            }
            
        }
    }
    
    this.getStageEntries = function(stage){
        var resultEntries = [];
        var index = this.getStageEntryIndex(stage);
        var nextIndex = this.getStageEntryIndex(stage + 1);
        for(var i = index; i < nextIndex; i++){
            resultEntries.push(this.entries[i]);
        }
        return resultEntries;
    }
    
    this.getStageEntryIndex = function(stage){
        if(stage < 0){
            return 0;
        }
        if(stage >= this.stagesIndexes.length){
            return this.entries.length - 1;
        }
        var index = this.stagesIndexes[stage];
        return index;
    }
    
    this.stagesCount = function(){
        return this.stagesIndexes.length;
    }
}

function Summary(entries){
    this.totalAttackers = {
        physical : 0,
        magical : 0,
        healing : 0
    }
    this.totalDefenders = {
        physical : 0,
        magical : 0,
        healing : 0
    }
    this.mostDamageDone = new SummaryForm("", 0, 0);
    this.mostDamageDoneArray = [];
    this.leastDamageTaken = new SummaryForm("", 0, 0);//todo
    this.leastDamageTakenArray = [];
    this.tickCount = 0;
    
    this.roundDamagePerTick = function(value){
        var decimalPlaces = 2;
        var tens = Math.pow(10, decimalPlaces);
        var number = Math.round( (value/this.tickCount) * tens) / tens;
        return number;
    }
    
    this.canBeAnalized = function(entry){
        if(entry.abilityGroup != undefined){
            return true;
        }
        return false;
    }
    
    this.findValue = function(text){
        var found = false;
        var number = "";
        for(var i = 0; i < text.length; i++){
            var charAt = text.charAt(i);
            var toCheck = parseInt(charAt, 10);
            if(!isNaN(toCheck)){
                found = true;
            }
            else{
                if(found){
                    return Number(number);
                }
            }
            if(found){
                number += charAt;
            }
        }
        if(number == ""){
            return 0;
        }
        return Number(number);
    }
    
    this.addTotalDamage = function(entry, value){
        if(entry.entryText.includes("physical")){
            if(entry.source.side == "attacker"){
                this.totalAttackers.physical += value;
            }
            else{
                this.totalDefenders.physical += value;
            }
        }
        else{
            if(entry.source.side == "attacker"){
                this.totalAttackers.magical += value;
            }
            else{
                this.totalDefenders.magical += value;
            }
        }
    }
    
    this.addToMostDamageDone = function(entry, value){
        var unit = 0;
        for(var i = 0; i < this.mostDamageDoneArray.length; i++){
            if(entry.source.unitName == this.mostDamageDoneArray[i].unit){
                unit = this.mostDamageDoneArray[i];
            }
        }
        if(unit == 0){
            unit = new SummaryForm(entry.source.unitName, 0, 0);
            this.mostDamageDoneArray.push(unit);
        }
        unit.addDamage(entry, value);
    }
    
    this.addToLeastDamageTaken = function(entry, value){
        var target = 0;
        var source = 0;
        for(var i = 0; i < this.leastDamageTakenArray.length; i++){
            if(entry.target.unitName == this.leastDamageTakenArray[i].unit){
                target = this.leastDamageTakenArray[i];
            }
            if(entry.source.unitName == this.leastDamageTakenArray[i].unit){
                source = this.leastDamageTakenArray[i];
            }
        }
        if(target == 0){
            target = new SummaryForm(entry.target.unitName, 0, 0);
            this.leastDamageTakenArray.push(target);
        }
        if(source == 0){
            source = new SummaryForm(entry.source.unitName, 0, 0);
            this.leastDamageTakenArray.push(source);
        }
        target.addDamage(entry, value);
    }
    
    this.selectMostDamageDone = function(){
        for(var i = 0; i < this.mostDamageDoneArray.length; i++){
            var totalCurrentTop = this.mostDamageDone.magical + this.mostDamageDone.physical;
            var totalCurrent = this.mostDamageDoneArray[i].magical + this.mostDamageDoneArray[i].physical;
            if(totalCurrentTop < totalCurrent){
                this.mostDamageDone = this.mostDamageDoneArray[i];
            }
        }
    }
    
    this.selectLeastDamageTaken = function(){
        for(var i = 0; i < this.leastDamageTakenArray.length; i++){
            if(this.leastDamageTaken.unit == ""){
                this.leastDamageTaken = this.leastDamageTakenArray[i];
            }
            else{
                var totalCurrentTop = this.leastDamageTaken.magical + this.leastDamageTaken.physical;
                var totalCurrent = this.leastDamageTakenArray[i].magical + this.leastDamageTakenArray[i].physical;
                if(totalCurrentTop > totalCurrent){
                    this.leastDamageTaken = this.leastDamageTakenArray[i];
                }
            }
        }
    }
    
    this.addTotalHealing = function(entry, value){
        if(entry.source.side == "attacker"){
            this.totalAttackers.healing += value;
        }
        else{
            this.totalDefenders.healing += value;
        }
    }
    
    this.analizeAttack = function(entry, value){
        this.addTotalDamage(entry, value);
        this.addToMostDamageDone(entry, value);
        this.addToLeastDamageTaken(entry, value);
    }
    
    this.analizeEntry = function(entry, value){
        if(entry.abilityGroup == "heal" || entry.abilityGroup == "regeneration"){
            this.addTotalHealing(entry, value);
        }
        if(entry.abilityGroup == "attack"){
            this.analizeAttack(entry, value);
        }
    }
    
    this.analizeEntries = function(entries){
        for(var i = 0; i < entries.length; i++){
            var entry = entries[i];
            if(this.canBeAnalized(entry)){
                var value = this.findValue(entry.entryText);
                this.analizeEntry(entry, value);
            }
            if(entry.entryGroup == "tickStart"){
                this.tickCount++;
            }
        }
        this.selectMostDamageDone();
        this.selectLeastDamageTaken();
    }
    this.analizeEntries(entries);
    
    this.formText = function(){
        var textArray = [];
        textArray.push("Total damage done");
        textArray.push("Attackers physical damage done: " + this.totalAttackers.physical + " (" + this.roundDamagePerTick(this.totalAttackers.physical) + " dmg/tick).");
        textArray.push("Attackers magical damage done: " + this.totalAttackers.magical + " (" + this.roundDamagePerTick(this.totalAttackers.magical) + " dmg/tick).");
        textArray.push("Defenders physical damage done: " + this.totalDefenders.physical + " (" + this.roundDamagePerTick(this.totalDefenders.physical) + " dmg/tick).");
        textArray.push("Defenders magical damage done: " + this.totalDefenders.magical + " (" + this.roundDamagePerTick(this.totalDefenders.magical) + " dmg/tick).");
        textArray.push("Total healing done");
        textArray.push("Attackers healing damage done: " + this.totalAttackers.healing);
        textArray.push("Defenders healing damage done: " + this.totalDefenders.healing);
        textArray.push(" ");
        textArray.push("Most damage done: " + this.mostDamageDone.unit);
        textArray.push("Physical damage done: " + this.mostDamageDone.physical);
        textArray.push("Magical damage done: " + this.mostDamageDone.magical);
        textArray.push(" ");
        textArray.push("Least damage taken: " + this.leastDamageTaken.unit);
        textArray.push("Physical damage taken: " + this.leastDamageTaken.physical);
        textArray.push("Magical damage taken: " + this.leastDamageTaken.magical);
        return textArray;
    }
}

function SummaryForm(unit, physical, magical){
    this.unit = unit;
    this.physical = physical;
    this.magical = magical;
    
    this.addDamage = function(entry, value){
        if(entry.entryText.includes("physical")){
            this.physical += value;
        }
        else{
            this.magical += value;
        }
    }
}

function Assets(pattern){
    this.units = [];
    this.markerDead = new Image();
    this.markerDead.src = "/img/units/markerDead.png";
    this.markerRisen = new Image();
    this.markerRisen.src = "/img/units/markerRisen.png";
    this.markerMove = new Image();
    this.markerMove.src = "/img/units/markerMove.png";
    
    for(i = 0; i < pattern.length; i++){
        asset = new Asset();
        asset.name = pattern[i].name;
        asset.img.src = pattern[i].path;
        this.units.push(asset);
    };
    this.findUnit = function(name){
        var result;
        for(i = 0; i < this.units.length; i++){
            if(this.units[i].name.includes(name)){
                if(name != "none"){
                    result = this.units[i];
                    break;
                }
                else{
                    if(this.units[i].name == "none.png"){
                        result = this.units[i];
                        break;
                    }
                }
            }
            
        }
        return result;
    }
}

function Asset(){
    this.name;
    this.img = new Image();
    this.img.onload = test();
}

function test(){
    console.log("test");
}

function newFrame(){
    simulator.newFrame();
    if(simulator.run == true){
        requestAnimationFrame(newFrame);
    }
}