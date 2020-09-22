/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var simulator = new Simulator();

function startSimulation(attackersCanvas, defendersCanvas, battleCanvas, combatlog){
    if(!simulator.auto){
        simulator.stop();
        simulator.init(attackersCanvas, defendersCanvas, battleCanvas, combatlog);
        simulator.start();
    }
}

function nextStage(){
    if(simulator.isRunning() && !simulator.auto){
        simulator.nextStage();
    }
}

function previousStage(){
    if(simulator.isRunning() && !simulator.auto){
        simulator.previousStage();
    }
}

function autoBattle(){
    if(simulator.isRunning()){
        simulator.auto = true;
        window.setTimeout(autoStage, 2000);
    }
}

function autoStage(){
    console.log(simulator.currentStage);
    simulator.nextStage();
    if(!simulator.isLastStage() && simulator.auto == true){
        window.setTimeout(autoStage, 2000);
    }
    else{
        if(simulator.isLastStage()){
            stopAutoBattle();
        }
    }
}

function stopAutoBattle(){
    simulator.auto = false;
}

function Simulator(){
    this.attackersCanvas;
    this.defendersCanvas;
    this.battleCanvas;
    this.combatlog;
    this.assets;
    this.attackersFront = [];
    this.attackersBack = [];
    this.attackersReserves = [];
    this.attackersReservesState = [];
    this.defendersFront = [];
    this.defendersBack = [];
    this.defendersReserves = [];
    this.defendersReservesState = [];
    this.width;
    this.entries;
    this.currentStage = 0;
    this.firstPhase = true;
    this.run = true;
    this.auto = false;
    this.init = function(attackersCanvas, defendersCanvas, battleCanvas, combatTextArea){
        this.attackersCanvas = attackersCanvas;
        this.defendersCanvas = defendersCanvas;
        this.battleCanvas = battleCanvas;
        this.combatlog = new CombatLog(combatTextArea);
        this.setAssets();
        this.getSimulation();
        this.updateBattleState();
        console.log(this.stagesIndexes);
    }
    this.setAssets = function(){
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
        this.assets = new Assets(response.responseJSON);
    }
    this.getSimulation = function(){
        var response = $.ajax({
            async: false,
            type: "GET",
            contentType:"application/json; charset=utf-8",
            dataType: "json",
            url: "/testBattle",
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
    this.updateBattleState = function(){
        this.combatlog.clear();
        var entries = this.entries.getStageEntries(this.currentStage);
        if(this.firstPhase == true){
            this.battlePhase(entries);
        }
        else{
            this.reinforcementsPhase(entries);
        }
    }
    this.battlePhase = function(entries){
        for(var i = 0; i < entries.length; i++){
            var entry = entries[i];
            if(entry.entryCategory == "reinforcePhase"){
                break;
            }
            else{
                this.interpretEntry(entry);
            }
        }
    }
    this.interpretEntry = function(entry){
        if(entry.entryCategory == "attackersState" || entry.entryCategory == "defendersState"){
            if(entry.entryCategory == "attackersState"){
                this.attackersFront = entry.frontLine;
                this.attackersBack = entry.backLine;
                this.attackersReserves = entry.reserves;
                this.attackersReservesState = entry.aliveReserves;
            }
            else{
                this.defendersFront = entry.frontLine;
                this.defendersBack = entry.backLine;
                this.defendersReserves = entry.reserves;
                this.defendersReservesState = entry.aliveReserves;
            }
        }
        else{
            var text = entry.stringFormat;
            this.combatlog.newLogLine(text);
        }
    }
    this.reinforcementsPhase = function(entries){
        var found = false;
        for(var i = 0; i < entries.length; i++){
            var entry = entries[i];
            if(!found){
                if(entry.entryCategory == "reinforcePhase"){
                    found = true;
                    var text = entry.stringFormat;
                    this.combatlog.newLogLine(text);
                }
            }
            else{
                if(entry.entryCategory != "tick"){
                    this.interpretEntry(entry);
                }
            }
        }
    }
    this.stop = function(){
        this.run = false;
    }
    this.start = function(){
        this.run = true;
        console.log(this);
        requestAnimationFrame(newFrame);
    }
    this.newFrame = function(ctxBattle, ctxAttackReserves, ctxDefReserves){
        this.drawBattle(ctxBattle);
        this.drawReserves(this.attackersCanvas.width, ctxAttackReserves, this.attackersReserves, this.attackersReservesState);
        this.drawReserves(this.defendersCanvas.width, ctxDefReserves, this.defendersReserves, this.defendersReservesState);
    }
    this.drawBattle = function(ctx){
        for(var index = 0; index < this.attackersFront.length; index++){
            ctx.save();
            this.drawUnits(ctx, index);
            ctx.restore();
        }
    }
    this.drawUnits = function(ctx, index){
        var scale = 50;
        var x = index * scale;
        var y = 0;
        var asset = this.assets.findUnit(this.attackersBack[index]);
        ctx.drawImage(asset.img, x, y, scale, scale);                       // draw image at current position
        y = y + 50;
        asset = this.assets.findUnit(this.attackersFront[index]);
        ctx.drawImage(asset.img, x, y, scale, scale);                       // draw image at current position
        y = y + 100;
        asset = this.assets.findUnit(this.defendersFront[index]);
        ctx.drawImage(asset.img, x, y, scale, scale);                       // draw image at current position
        y = y + 50;
        asset = this.assets.findUnit(this.defendersBack[index]);
        ctx.drawImage(asset.img, x, y, scale, scale);                       // draw image at current position
    }
    this.drawReserves = function(width, ctx, reserves, state){
        var scale = 20;
        for(var index = 0; index < reserves.length; index++){
            ctx.save();
            var x = (index % (width/scale)) * scale;
            var y = Math.floor(index / (width/scale) ) * scale;
            var asset = this.assets.findUnit(reserves[index]);
            ctx.drawImage(asset.img, x, y, scale, scale);                       // draw image at current position
            if(state[index] == false){
                ctx.drawImage(this.assets.marker, x, y, scale, scale);                       // draw image at current position
            }
            ctx.restore();
        }
    }
    this.nextStage = function(){
        if(this.currentStage + 1 < this.entries.stagesCount() || this.firstPhase == true){
            if(this.firstPhase == false){
                this.currentStage++;
            }
            this.firstPhase = !this.firstPhase;
            this.updateBattleState();
        }
    }
    this.previousStage = function(){
        if(this.currentStage > 0 || this.firstPhase == false){
            if(this.firstPhase == true){
                this.currentStage--;
            }
            this.firstPhase = !this.firstPhase;
            this.updateBattleState();
        }
    }
    this.isLastStage = function(){
        if(this.currentStage == this.entries.stagesCount() - 1 && this.firstPhase == false){
            return true;
        }
        else{
            return false;
        }
    }
    this.isRunning = function(){
        return this.run;
    }
}

function CombatEntries(entries){
    this.entries = entries;
    this.stagesIndexes = [];
    for(var i = 0; i < this.entries.length; i++){
        if(this.entries[i].entryCategory == "tick"){
            this.stagesIndexes.push(i);
        }
    }
    
    this.getStageEntries = function(stage){
        var resultEntries = [];
        var index = this.getStageEntryIndex(stage);
        var nextIndex = this.getStageEntryIndex(stage + 1);
        for(var i = index; i <= nextIndex; i++){
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

function Position(assetName){
    this.name = assetName;
    this.active = false;
}

function Assets(pattern){
    this.units = [];
    this.marker = new Image();
    this.marker.src = "/img/units/marker.png";
    
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
                result = this.units[i];
                break;
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
    var ctxBattle = simulator.battleCanvas.getContext("2d");
    ctxBattle.clearRect(0, 0, simulator.battleCanvas.width, simulator.battleCanvas.height);
    var ctxAttack = simulator.attackersCanvas.getContext("2d");
    ctxAttack.clearRect(0, 0, simulator.attackersCanvas.width, simulator.attackersCanvas.height);
    var ctxDef = simulator.defendersCanvas.getContext("2d");
    ctxDef.clearRect(0, 0, simulator.defendersCanvas.width, simulator.defendersCanvas.height);
    simulator.newFrame(ctxBattle, ctxAttack, ctxDef);
    if(simulator.run == true){
        requestAnimationFrame(newFrame);
    }
}