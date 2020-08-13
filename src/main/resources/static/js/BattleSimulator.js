/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var simulator = new Simulator();

function startSimulation(attackersCanvas, defendersCanvas, combatlog){
    if(!simulator.auto){
        simulator.stop();
        simulator.init(attackersCanvas, defendersCanvas, combatlog);
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
    if(!simulator.isLastStage() && simulator.auto == false){
        window.setTimeout(autoStage, 2000);
    }
}

function stopAutoBattle(){
    simulator.auto = false;
}

function Simulator(){
    this.attackersCanvas;
    this.defendersCanvas;
    this.combatlog;
    this.assets;
    this.attackersFront = [];
    this.attackersBack = [];
    this.defendersFront = [];
    this.defendersBack = [];
    this.width;
    this.entries = [];
    this.stagesIndexes = [];
    this.currentStage = 0;
    this.run = true;
    this.auto = false;
    this.init = function(attackersCanvas, defendersCanvas, combatlog){
        this.attackersCanvas = attackersCanvas;
        this.defendersCanvas = defendersCanvas;
        this.combatlog = combatlog;
        this.setAssets();
        this.getSimulation();
        this.getStages();
        this.updateBattleState();
        console.log(this.stagesIndexes);
    }
    this.setAssets = function(){
        var response = $.ajax({
            async: false,
            type: "GET",
            contentType:"application/json; charset=utf-8",
            dataType: "json",
            url: "/getAssets",
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
        this.attackersFront = this.parseUnits(response.attackersFront);
        this.attackersBack = this.parseUnits(response.attackersBack);
        this.defendersFront = this.parseUnits(response.defendersFront);
        this.defendersBack = this.parseUnits(response.defendersBack);
        this.width = response.width;
        this.entries = response.entries;
        console.log(response);
    }
    this.parseUnits = function(line){
        var result = [];
        for(var i = 0; i < line.length; i++){
            var position = new Position(line[i]);
            result.push(position);
        }
        return result;
    }
    this.getStages = function(){
        for(i = 0; i < this.entries.length; i++){
            if(this.entries[i].action == "wave"){
                this.stagesIndexes.push(i);
            }
        }
    }
    this.clear = function(){
        this.combatlog.value = "";
    }
    this.stop = function(){
        this.run = false;
    }
    this.start = function(){
        this.run = true;
        console.log(this);
        requestAnimationFrame(newFrame);
    }
    this.newFrame = function(ctxAttack, ctxDef){
        this.drawAttackers(ctxAttack);
        this.drawDefenders(ctxDef);
    }
    this.isLastStage = function(){
        if(this.currentStage == this.stagesIndexes.length - 1){
            return true;
        }
        else{
            return false;
        }
    }
    this.isRunning = function(){
        return this.run;
    }
    this.drawAttackers = function(ctxAttack){
        var position = this.defendersCanvas.width/2 - this.width/2 * 50
        for(var x = 0; x < this.width; x++){
            ctxAttack.save();
            var horizontalPosition = position + x * 50;
            var front = this.assets.findUnit(this.attackersFront[x].name);
            ctxAttack.drawImage(front.img, horizontalPosition, 50, 50, 50);                       // draw image at current position
            if(this.attackersFront[x].active){
                ctxAttack.drawImage(this.assets.marker, horizontalPosition, 50, 50, 50);                       // draw image at current position
            }
            var back = this.assets.findUnit(this.attackersBack[x].name);
            ctxAttack.drawImage(back.img, horizontalPosition, 0, 50, 50);                       // draw image at current position
            if(this.attackersBack[x].active){
                ctxAttack.drawImage(this.assets.marker, horizontalPosition, 0, 50, 50);                       // draw image at current position
            }
            ctxAttack.restore();
        }
    }
    this.drawDefenders = function(ctxDef){
        var position = this.defendersCanvas.width/2 - this.width/2 * 50
        for(var x = 0; x < this.width; x++){
            ctxDef.save();
            var horizontalPosition = position + x * 50;
            var front = this.assets.findUnit(this.defendersFront[x].name);
            ctxDef.drawImage(front.img, horizontalPosition, 0, 50, 50);                       // draw image at current position
            if(this.defendersFront[x].active){
                ctxDef.drawImage(this.assets.marker, horizontalPosition, 0, 50, 50);                       // draw image at current position
            }
            var back = this.assets.findUnit(this.defendersBack[x].name);
            ctxDef.drawImage(back.img, horizontalPosition, 50, 50, 50);                       // draw image at current position
            if(this.defendersBack[x].active){
                ctxDef.drawImage(this.assets.marker, horizontalPosition, 50, 50, 50);                       // draw image at current position
            }
            ctxDef.restore();
        }
    }
    this.nextStage = function(){
        if(this.currentStage < this.stagesIndexes.length - 1){
            this.clearActive();
            this.currentStage++;
            this.updateBattleState();
        }
    }
    this.updateBattleState = function(){
        this.clear();
        var index = this.stagesIndexes[this.currentStage] + 1;
        while(index < this.entries.length){
            var entry = this.entries[index];
            if(entry.action == "wave"){
                break;
            }
            else{
                if(entry.action != "tick"){
                    if(entry.action != "none"){
                        this.markUnit(entry);
                        this.updateUnit(entry);
                    }
                    this.newLogLine(entry.stringFormat);
                }
            }
            index++;
        }
    }
    this.previousStage = function(){
        if(this.currentStage > 0){
            this.clearActive();
            this.revertCurrentState();
            this.currentStage--;
            this.updateBattleState();
        }
    }
    this.revertCurrentState = function(){
        var currentIndex = this.stagesIndexes[this.currentStage];
        var nextIndex = this.getNextStageIndexInLog();
        for(var i = nextIndex; i > currentIndex; i--){
            var entry = this.entries[i];
            if(entry.action == "tick" || entry.action == "wave"){
            }
            else{
                this.reverseUpdateUnit(entry);
            }
        }
    }
    this.getNextStageIndexInLog = function(){
        if(this.currentStage == this.stagesIndexes.length - 1){
            return this.entries.length - 1;
        }
        else{
            return this.stagesIndexes[this.currentStage + 1];
        }
    }
    this.clearActive = function(){
        for(var i = 0; i < this.width; i++){
            this.attackersFront[i].active = false;
            this.attackersBack[i].active = false;
            this.defendersFront[i].active = false;
            this.defendersBack[i].active = false;
        }
    }
    this.newLogLine = function(text){
        if(this.combatlog.value != ""){
            this.combatlog.value = this.combatlog.value + '\n' + text;
        }
        else{
            this.combatlog.value = text;
        }
    }
    this.markUnit = function(entry){
        if(entry.sourceSide == "attacker"){
            if(entry.isFront){
                this.attackersFront[entry.sourcePosition].active = true;
            }
            else{
                this.attackersBack[entry.sourcePosition].active = true;
            }
        }
        else{
            if(entry.isFront){
                this.defendersFront[entry.sourcePosition].active = true;
            }
            else{
                this.defendersBack[entry.sourcePosition].active = true;
            }
        }
    }
    this.updateUnit = function(entry){
        if(entry.action == "death"){
            this.removeUnit(entry);
        }
        if(entry.action == "retreat"){
            this.removeUnit(entry);
        }
        if(entry.action == "reinforce"){
            this.replaceUnit(entry);
        }
    }
    this.reverseUpdateUnit = function(entry){
        if(entry.action == "death" || entry.action == "retreat"){
            this.replaceUnit(entry);
        }
        if(entry.action == "reinforce"){
            this.removeUnit(entry);
        }
    }
    this.removeUnit = function(entry){
        if(entry.sourceSide == "attacker"){
            if(entry.isFront){
                this.attackersFront[entry.sourcePosition].name = "none";
            }
            else{
                this.attackersBack[entry.sourcePosition].name = "none";
            }
        }
        else{
            if(entry.isFront){
                this.defendersFront[entry.sourcePosition].name = "none";
            }
            else{
                this.defendersBack[entry.sourcePosition].name = "none";
            }
        }
    }
    this.replaceUnit = function(entry){
        if(entry.sourceSide == "attacker"){
            if(entry.isFront){
                this.attackersFront[entry.sourcePosition].name = entry.sourceName;
            }
            else{
                this.attackersBack[entry.sourcePosition].name = entry.sourceName;
            }
        }
        else{
            if(entry.isFront){
                this.defendersFront[entry.sourcePosition].name = entry.sourceName;
            }
            else{
                this.defendersBack[entry.sourcePosition].name = entry.sourceName;
            }
        }
    }
}

function Position(name){
    this.name = name;
    this.active = false;
}

function Assets(pattern){
    this.units = [];
    this.marker = new Image();
    this.marker.src = "/img/units/marker.png";
    
    for(i = 0; i < pattern.units.length; i++){
        asset = new Asset();
        asset.name = pattern.units[i].name;
        asset.img.src = pattern.units[i].path;
        this.units.push(asset);
    };
    this.findUnit = function(name){
        var result;
        for(i = 0; i < this.units.length; i++){
            if(this.units[i].name == name){
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
    var ctxAttack = simulator.attackersCanvas.getContext("2d");
    ctxAttack.clearRect(0, 0, simulator.attackersCanvas.width, simulator.attackersCanvas.height);
    var ctxDef = simulator.defendersCanvas.getContext("2d");
    ctxDef.clearRect(0, 0, simulator.defendersCanvas.width, simulator.defendersCanvas.height);
    simulator.newFrame(ctxAttack, ctxDef);
    if(simulator.run == true){
        requestAnimationFrame(newFrame);
    }
}