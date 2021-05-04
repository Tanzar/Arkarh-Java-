/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function BattleSimEditor(attackersDiv, defendersDiv, starterDiv){
    this.attackers = new Army();
    this.defenders = new Army();
    this.armies = {
        width : 20,
        attackers : this.attackers,
        defenders : this.defenders
    }
    this.URL = new URLConnector();
    this.artifacts = this.URL.getFromURL("/artifacts/getAllBySlot");
    this.addEmptySlots = function(){
        this.addEmptySlot(this.artifacts.head);
        this.addEmptySlot(this.artifacts.neck);
        this.addEmptySlot(this.artifacts.chest);
        this.addEmptySlot(this.artifacts.hands);
        this.addEmptySlot(this.artifacts.feet);
        this.addEmptySlot(this.artifacts.mainhand);
        this.addEmptySlot(this.artifacts.offhand);
        this.addEmptySlot(this.artifacts.ring);
        this.addEmptySlot(this.artifacts.bonus);
    }
    this.addEmptySlot = function(array){
        var item = {
            id : 0,
            name : "empty",
            asset : "none.png",
            effect : "none",
            value : 0
        }
        array.unshift(item);
    }
    this.addEmptySlots();
    this.units = {};
    this.options = this.URL.getFromURL("/unitEditor/getOptions");
    this.artifactsAssets = this.URL.getFromURL("asset/category=items");
    this.unitsAssets = this.URL.getFromURL("/getUnitsAssets");
    
    this.loadUnits = function(){
        var allUnits = this.URL.getFromURL("/unitEditor/getAllUnits");
        var fractions = this.options.fractions;
        for(var i = 0; i < fractions.length; i++){
            this.units[fractions[i]] = [];
        }
        for(var i = 0; i < allUnits.length; i++){
            var fraction = allUnits[i].fraction;
            this.units[fraction].push(allUnits[i]);
        }
    }
    this.loadUnits();
    
    this.initLeader = function(div, army){
        var table = new Table();
        table.addTextRow("Statistics");
        var level = this.newLevelDiv(army);
        table.addCustom('Level: ', level);
        var attack = this.newStatDiv('attack', army);
        table.addCustom('Attack: ', attack);
        var defense = this.newStatDiv('defense', army);
        table.addCustom('Defense: ', defense);
        var spellPower = this.newStatDiv('spellPower', army);
        table.addCustom('Spell Power: ', spellPower);
        div.appendChild(table.getHtmlElement());
        table.addTextRow("Artifacts");
        var head = this.newArtifactDiv(this.artifacts.head, 0, army);
        table.addCustom("Head: ", head);
        var neck = this.newArtifactDiv(this.artifacts.neck, 1, army);
        table.addCustom("Neck: ", neck);
        var chest = this.newArtifactDiv(this.artifacts.chest, 2, army);
        table.addCustom("Chest: ", chest);
        var hands = this.newArtifactDiv(this.artifacts.hands, 3, army);
        table.addCustom("Hands: ", hands);
        var feet = this.newArtifactDiv(this.artifacts.feet, 4, army);
        table.addCustom("Feet: ", feet);
        var mainHand = this.newArtifactDiv(this.artifacts.mainhand, 5, army);
        table.addCustom("Main Hand: ", mainHand);
        var offHand = this.newArtifactDiv(this.artifacts.offhand, 6, army);
        table.addCustom("Off Hand: ", offHand);
        var ringOne = this.newArtifactDiv(this.artifacts.ring, 7, army);
        table.addCustom("Ring 1: ", ringOne);
        var ringTwo = this.newArtifactDiv(this.artifacts.ring, 8, army);
        table.addCustom("Ring 2: ", ringTwo);
        var bonusOne = this.newArtifactDiv(this.artifacts.bonus, 9, army);
        table.addCustom("Bonus 1: ", bonusOne);
        var bonusTwo = this.newArtifactDiv(this.artifacts.bonus, 10, army);
        table.addCustom("Bonus 2: ", bonusTwo);
        var upkeepDiv = document.createElement("div");
        var leaderUpkeepDisplay = document.createTextNode(army.leader.upkeep);
        army.leader.upkeepDisplay = leaderUpkeepDisplay;
        var spaceTextNode = document.createTextNode(" / ");
        var armyUpkeepDisplay = document.createTextNode(army.upkeep);
        army.upkeepDisplay = armyUpkeepDisplay;
        upkeepDiv.appendChild(armyUpkeepDisplay);
        upkeepDiv.appendChild(spaceTextNode);
        upkeepDiv.appendChild(leaderUpkeepDisplay);
        table.addCustom("Upkeep: ", upkeepDiv);
    }
    this.newLevelDiv = function(army){
        var div = document.createElement("div");
        var numberDisplay = document.createTextNode(army.leader.level);
        var spaceNode = document.createTextNode("   ");
        var increaseButton = document.createElement("button");
        increaseButton.innerHTML = "+";
        increaseButton.onclick = function(){
            if(army.leader.level < army.leader.maxLevel){
                army.leader.levelUp();
                numberDisplay.nodeValue = army.leader.level;
                
            }
        }
        var decreaseButton = document.createElement("button");
        decreaseButton.innerHTML = "-";
        decreaseButton.onclick = function(){
            if(army.leader.level > 1 && army.leader.pointsToSpend > 0){
                army.leader.levelDown();
                numberDisplay.nodeValue = army.leader.level;
            }
        }
        div.appendChild(numberDisplay);
        div.appendChild(spaceNode);
        div.appendChild(increaseButton);
        div.appendChild(decreaseButton);
        return div;
    }
    this.newStatDiv = function(statName, army){
        var div = document.createElement("div");
        var numberDisplay = document.createTextNode(army.leader[statName]);
        var spaceNode = document.createTextNode("   ");
        var increaseButton = document.createElement("button");
        increaseButton.innerHTML = "+";
        increaseButton.onclick = function(){
            if(army.leader.pointsToSpend > 0){
                army.leader[statName]++;
                army.leader.pointsToSpend--;
                numberDisplay.nodeValue = army.leader[statName];
            }
        }
        var decreaseButton = document.createElement("button");
        decreaseButton.innerHTML = "-";
        decreaseButton.onclick = function(){
            if(army.leader[statName] > 0){
                army.leader[statName]--;
                army.leader.pointsToSpend++;
                numberDisplay.nodeValue = army.leader[statName];
            }
        }
        div.appendChild(numberDisplay);
        div.appendChild(spaceNode);
        div.appendChild(increaseButton);
        div.appendChild(decreaseButton);
        return div;
    }
    this.newArtifactDiv = function(itemsArray, equipmentIndex, army){
        var leader = army.leader;
        var div = document.createElement("div");
        var image = document.createElement("img");
        image.width = 25;
        image.height = 25;
        var select = document.createElement("select");
        for(var i = 0; i < itemsArray.length; i++){
            var item = itemsArray[i];
            var option = document.createElement('option');
            var textNode = document.createTextNode(item.name);
            option.appendChild(textNode);
            option.value = item.id;
            option.asset = item.asset;
            option.effect = item.effect;
            if(item.effect == "upkeep"){
                var k = 0;
            }
            option.effectValue = item.value;
            select.appendChild(option);
            if(leader.equipment[equipmentIndex] == item.id){
                select.selectedIndex = i;
            }
        }
        select.previousIndex = select.selectedIndex;
        var assets = this.artifactsAssets;
        select.onchange = function(){
            var item = select.options[select.selectedIndex];
            for(var i = 0; i < assets.length; i++){
                if(item.asset == assets[i].name){
                    image.src = assets[i].path;
                }
            }
            leader.equipment[equipmentIndex] = Number(item.value);
            var previousItem = select.options[select.previousIndex];
            if(previousItem.effect == "upkeep"){
                leader.decreaseUpkeep(previousItem.effectValue);
            }
            if(item.effect == "upkeep"){
                leader.increaseUpkeep(item.effectValue);
            }
            select.previousIndex = select.selectedIndex;
        }
        select.onchange();
        div.appendChild(image);
        div.appendChild(select);
        return div;
    }
    this.initUnits = function(div, army){
        var table = new Table();
        table.addTextRow("Units");
        var select = document.createElement("select");
        for(var i = 0; i < this.options.alliances.length; i++){
            var item = this.options.alliances[i];
            var option = document.createElement('option');
            var textNode = document.createTextNode(this.uncodeName(item));
            option.appendChild(textNode);
            option.value = item;
            select.appendChild(option);
        }
        var unitsDiv = this.initUnitsDiv(army);
        select.onchange = function(){
            var alliance = select.options[select.selectedIndex];
            unitsDiv.changeUnits(alliance.value);
        }
        select.onchange();
        table.addCustom('Alliance: ', select);
        div.appendChild(table.getHtmlElement());
        div.appendChild(unitsDiv);
    }
    this.uncodeName = function(text){
        var uncoded = "";
        for(var i = 0; i < text.length; i++){
            if(i == 0){
                var letter = text.charAt(0);
                uncoded += letter.toUpperCase();
            }
            else{
                var letter = text.charAt(i);
                if(this.isUpperCase(letter)){
                    uncoded += " " + letter;
                }
                else{
                    uncoded += letter;
                }
            }
        }
        return uncoded;
    }
    this.isUpperCase = function(letter){
        if(letter == letter.toUpperCase()){
            return true;
        }
        else{
            return false;
        }
    }
    this.initUnitsDiv = function(army){
        var editor = this;
        var unitsDiv = document.createElement("div");
        unitsDiv.clearChildren = function(){
            while(unitsDiv.firstChild){
                unitsDiv.removeChild(unitsDiv.lastChild);
            }
        }
        unitsDiv.changeUnits = function(alliance){
            if(army.changeAlliance(alliance)){
                unitsDiv.clearChildren();
                var fractions = editor.getFractions(alliance);
                var table = new Table();
                for(var i = 0; i < fractions.length; i++){
                    table.addTextRow(editor.uncodeName(fractions[i]))
                    var units = editor.getUnitsSplitedByTier(fractions[i]);
                    var tiers = editor.options.tiers;
                    for(var t = 0; t < tiers.length; t++){
                        if(units[t].length > 0){
                            table.addTextRow(editor.uncodeName(tiers[t]));
                            for(var u = 0; u < units[t].length; u++){
                                var div = document.createElement("div");
                                var image = document.createElement("img");
                                image.src = editor.getUnitAssetPath(units[t][u].assetName);
                                image.width = 25;
                                image.height = 25;
                                div.appendChild(image);
                                var unit = new Unit();
                                unit.id = units[t][u].id;
                                unit.upkeepPerOne = units[t][u].special.upkeep;
                                var numberInput = document.createElement('input');
                                numberInput.setAttribute("type", "number");
                                numberInput.min = 0;
                                numberInput.value = unit.count;
                                numberInput.unitIndex = army.army.length;
                                numberInput.style.width = "50px";
                                numberInput.onchange = function(){
                                    var previousCount = army.army[this.unitIndex].count;
                                    var currentCount = Number(this.value);
                                    var upkeepChange = Number(army.army[this.unitIndex].upkeepPerOne * Number(currentCount - previousCount));
                                    if(upkeepChange >= 0){
                                        army.increaseUpkeep(upkeepChange);
                                    }
                                    else{
                                        upkeepChange = Math.abs(upkeepChange);
                                        army.decreaseUpkeep(upkeepChange);
                                    }
                                    army.army[this.unitIndex].count = Number(this.value);
                                }
                                army.army.push(unit);
                                div.appendChild(numberInput);
                                table.addCustom(units[t][u].name, div);
                            }
                        }
                    }
                }
                unitsDiv.appendChild(table.getHtmlElement());
            }
        }
        return unitsDiv;
    }
    this.getFractions = function(alliance){
        var fractions = [];
        for(var i = 0; i < this.options.allianceRelations.length; i++){
            var relation = this.options.allianceRelations[i];
            if(relation.alliance == alliance){
                fractions.push(relation.fraction);
            }
        }
        return fractions;
    }
    this.getUnitsSplitedByTier = function(fraction){
        var units = this.units[fraction];
        var tiers = this.options.tiers;
        var result = [];
        for(var t = 0; t < tiers.length; t++){
            var unitsAtTier = [];
            for(var u = 0; u < units.length; u++){
                if(units[u].tier == tiers[t]){
                    unitsAtTier.push(units[u]);
                }
            }
            result.push(unitsAtTier);
        }
        return result;
    }
    this.getUnitAssetPath = function(assetName){
        for(var i = 0; i < this.unitsAssets.length; i++){
            if(this.unitsAssets[i].name == assetName){
                return this.unitsAssets[i].path;
            }
        }
    }
    this.init = function(div, army){
        this.initLeader(div, army);
        this.initUnits(div, army);
    }
    this.init(attackersDiv, this.attackers);
    this.init(defendersDiv, this.defenders);
    
    
    this.initStarter = function(starterDiv){
        var element = new DivElement();
        var armies = this.armies;
        element.addNumber('Battlefield Width', armies, 'width', 0, 20);
        element.newLine();
        element.newLine();
        element.addButton('Start', function(){
            console.log(armies);
            if(armies.attackers.upkeep <= armies.attackers.leader.upkeep && armies.defenders.upkeep <= armies.defenders.leader.upkeep){
                openInNewTabWithData('/battleSim');
            }
        });
        element.addThisAsChild(starterDiv);
    }
    this.initStarter(starterDiv);
}

function Leader(){
    this.level = 1;
    this.maxLevel = 40;
    this.pointsToSpend = 1;
    this.experience = 1;
    this.attack = 0;
    this.defense = 0;
    this.spellPower = 0;
    this.equipment = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    this.upkeep = 160;
    this.upkeepIncreasePerLevel = 10;
    this.upkeepDisplay;
    this.levelUp = function(){
        if(this.level < this.maxLevel){
            this.level++;
            this.pointsToSpend++;
            this.upkeep += this.upkeepIncreasePerLevel;
            this.upkeepChange();
        }
    }
    this.levelDown = function(){
        if(this.level > 1 && this.pointsToSpend > 0){
            this.level--;
            this.pointsToSpend--;
            this.upkeep -= this.upkeepIncreasePerLevel;
            this.upkeepChange();
        }
    }
    this.increaseUpkeep = function(value){
        this.upkeep += value;
        this.upkeepChange();
    }
    this.decreaseUpkeep = function(value){
        this.upkeep -= value;
        this.upkeepChange();
    }
    this.upkeepChange = function(){
        if(this.upkeepDisplay != undefined){
            this.upkeepDisplay.nodeValue = this.upkeep;
        }
    }
}

function Army(){
    this.leader = new Leader();
    this.army = [];
    this.upkeep = 0;
    this.alliance = "";
    this.upkeepDisplay;
    this.upkeepChange = function(){
        if(this.upkeepDisplay != undefined){
            this.upkeepDisplay.nodeValue = this.upkeep;
        }
    }
    this.increaseUpkeep = function(value){
        if(!isNaN(value)){ //if not value is not a number = if value is number
            this.upkeep += Number(value);
            this.upkeepChange();
        }
    }
    this.decreaseUpkeep = function(value){
        if(!isNaN(value) && this.upkeep >= Number(value)){
            this.upkeep -= Number(value);
            this.upkeepChange();
        }
    }
    this.changeAlliance = function(alliance){
        if(this.alliance != alliance){
            this.alliance = alliance;
            this.upkeep = 0;
            this.army = [];
            return true;
        }
        else{
            return false;
        }
    }
}

function Unit(id){
    this.id = id;
    this.count = 0;
    this.upkeepPerOne = 0;
}