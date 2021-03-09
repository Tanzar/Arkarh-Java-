/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function BattleSimEditor(attackersDiv, defendersDiv, starterDiv){
    this.attackers = {
        leader: new Leader(),
        army : [],
        upkeep : 0,
        alliance : 'none'
    };
    this.defenders = {
        leader: new Leader(),
        army : [],
        upkeep : 0,
        alliance : 'none'
    };
    this.armies = {
        width : 20,
        attackers : this.attackers,
        defenders : this.defenders
    }
    this.URL = new URLConnector();
    this.artifacts = this.URL.getFromURL("/artifacts/getAllBySlot");
    this.units = {};
    this.options = this.URL.getFromURL("/unitEditor/getOptions");
    
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
    
    this.initLeaderStats = function(leaderDiv, side){
        leaderDiv.newLine('Statistics');
        var level = leaderDiv.addTextDisplay('Level: ', side.leader, 'level');
        leaderDiv.newLine();
        var upkeep = leaderDiv.addTextDisplay('Upkeep: ', side.leader, 'upkeep');
        leaderDiv.newLine();
        var input = leaderDiv.addNumber("Attack", side.leader, 'attack', 1, 100);
        input.onfocus = function(){
            this.previousValue = this.value;
        }
        input.onchange = function(){
            side.leader.attack = Number(this.value);
            side.leader.level = side.leader.level + (this.value - this.previousValue);
            side.leader.upkeep = side.leader.upkeep + 10*(this.value - this.previousValue);
            level.nodeValue = side.leader.level;
            upkeep.nodeValue = side.leader.upkeep;
            this.previousValue = this.value;
            
        }
        leaderDiv.newLine();
         input = leaderDiv.addNumber("Defense", side.leader, 'defense', 1, 100);
        input.onfocus = function(){
            this.previousValue = this.value;
        }
        input.onchange = function(){
            side.leader.defense = Number(this.value);
            side.leader.level = side.leader.level + (this.value - this.previousValue);
            side.leader.upkeep = side.leader.upkeep + 10*(this.value - this.previousValue);
            level.nodeValue = side.leader.level;
            upkeep.nodeValue = side.leader.upkeep;
            this.previousValue = this.value;
            
        }
        leaderDiv.newLine();
         input = leaderDiv.addNumber("Spell Power", side.leader, 'spellPower', 1, 100);
        input.onfocus = function(){
            this.previousValue = this.value;
        }
        input.onchange = function(){
            side.leader.spellPower = Number(this.value);
            side.leader.level = side.leader.level + (this.value - this.previousValue);
            side.leader.upkeep = side.leader.upkeep + 10*(this.value - this.previousValue);
            level.nodeValue = side.leader.level;
            upkeep.nodeValue = side.leader.upkeep;
            this.previousValue = this.value;
            
        }
    }
    
    this.initLeaderArtifacts = function(leaderDiv, side){
        leaderDiv.newLine('Artifacts');
        this.artifacts.head.push({id:0, name:'Empty'});
        leaderDiv.addSelectValueFromSelectedItem("Head", this.artifacts.head, 'id', 'name', side.leader.equipment, 0);
        leaderDiv.newLine();
        this.artifacts.neck.push({id:0, name:'Empty'});
        leaderDiv.addSelectValueFromSelectedItem("Neck", this.artifacts.neck, 'id', 'name', side.leader.equipment, 1);
        leaderDiv.newLine();
        this.artifacts.chest.push({id:0, name:'Empty'});
        leaderDiv.addSelectValueFromSelectedItem("Chest", this.artifacts.chest, 'id', 'name', side.leader.equipment, 2);
        leaderDiv.newLine();
        this.artifacts.hands.push({id:0, name:'Empty'});
        leaderDiv.addSelectValueFromSelectedItem("Hands", this.artifacts.hands, 'id', 'name', side.leader.equipment, 3);
        leaderDiv.newLine();
        this.artifacts.legs.push({id:0, name:'Empty'});
        leaderDiv.addSelectValueFromSelectedItem("Legs", this.artifacts.legs, 'id', 'name', side.leader.equipment, 4);
        leaderDiv.newLine();
        this.artifacts.feet.push({id:0, name:'Empty'});
        leaderDiv.addSelectValueFromSelectedItem("Feet", this.artifacts.feet, 'id', 'name', side.leader.equipment, 5);
        leaderDiv.newLine();
        this.artifacts.mainhand.push({id:0, name:'Empty'});
        leaderDiv.addSelectValueFromSelectedItem("Main Hand", this.artifacts.mainhand, 'id', 'name', side.leader.equipment, 6);
        leaderDiv.newLine();
        this.artifacts.offhand.push({id:0, name:'Empty'});
        leaderDiv.addSelectValueFromSelectedItem("Off Hand", this.artifacts.offhand, 'id', 'name', side.leader.equipment, 7);
        leaderDiv.newLine();
        this.artifacts.ring.push({id:0, name:'Empty'});
        leaderDiv.addSelectValueFromSelectedItem("Ring 1", this.artifacts.ring, 'id', 'name', side.leader.equipment, 8);
        leaderDiv.newLine();
        leaderDiv.addSelectValueFromSelectedItem("Ring 2", this.artifacts.ring, 'id', 'name', side.leader.equipment, 9);
        leaderDiv.newLine();
        this.artifacts.bonus.push({id:0, name:'Empty'});
        leaderDiv.addSelectValueFromSelectedItem("Bonus 1", this.artifacts.bonus, 'id', 'name', side.leader.equipment, 10);
        leaderDiv.newLine();
        leaderDiv.addSelectValueFromSelectedItem("Bonus 2", this.artifacts.bonus, 'id', 'name', side.leader.equipment, 11);
        leaderDiv.newLine();
        leaderDiv.addSelectValueFromSelectedItem("Bonus 3", this.artifacts.bonus, 'id', 'name', side.leader.equipment, 12);
        leaderDiv.newLine();
        leaderDiv.addSelectValueFromSelectedItem("Bonus 4", this.artifacts.bonus, 'id', 'name', side.leader.equipment, 13);
    }
    
    this.initUnitsSelection = function(unitsSelectionDiv, side){
        var select = unitsSelectionDiv.addSelect('Alliance: ', this.options.alliances, side, 'alliance');
        unitsSelectionDiv.newLine();
        var upkeepDisplay = unitsSelectionDiv.addTextDisplay('Upkeep: ', side, 'upkeep');
        var unitsDiv = new DivElement();
        var options = this.options;
        var units = this.units;
        select.onchange = function(){
            unitsDiv.clear();
            upkeepDisplay.nodeValue = 0;
            side.alliance = this.options[this.selectedIndex].value;
            side.upkeep = 0;
            side.army = [];
            var unitCount = 0;
            for(var i = 0; i < options.allianceRelations.length; i++){
                var relation = options.allianceRelations[i];
                if(relation.alliance == side.alliance){
                    var fractionDiv = new DivElement();
                    fractionDiv.newLine();
                    fractionDiv.newLine(relation.fraction);
                    for(var j = 0; j < units[relation.fraction].length; j++){
                        var unit = new Unit(units[relation.fraction][j].id);
                        unit.upkeepPerOne = units[relation.fraction][j].special.upkeep;
                        side.army.push(unit);
                        var countInput = fractionDiv.addNumber(units[relation.fraction][j].name + ': ', side.army[side.army.length - 1], 'count', 0);
                        countInput.previousValue = countInput.value;
                        countInput.unitIndex = unitCount;
                        countInput.onchange = function(){
                            side.army[this.unitIndex].count = Number(this.value);
                            side.upkeep = Number(side.upkeep) + (side.army[this.unitIndex].upkeepPerOne * (Number(this.value) - Number(this.previousValue)));
                            upkeepDisplay.nodeValue = Number(side.upkeep);
                            this.previousValue = Number(this.value);
                            if(side.leader.upkeep < side.upkeep){
                                upkeepDisplay.nodeValue += " err";
                            }
                        }
                        unitCount++;
                        fractionDiv.newLine();
                    }
                    fractionDiv.addThisAsChild(unitsDiv.getHTMLElement());
                }
            }
            unitsDiv.addThisAsChild(unitsSelectionDiv.getHTMLElement());
        }
        select.onchange();
    }
    
    this.initArmyEditor = function(div, side){
        var leaderDiv = new DivElement();
        this.initLeaderStats(leaderDiv, side);
        leaderDiv.newLine();
        this.initLeaderArtifacts(leaderDiv, side);
        leaderDiv.addThisAsChild(div);
        var army = side.army;
        var unitsDiv = new DivElement();
        this.initUnitsSelection(unitsDiv, side)
        
        unitsDiv.addThisAsChild(div);
        
    }
    this.initArmyEditor(attackersDiv, this.attackers);
    this.initArmyEditor(defendersDiv, this.defenders);
    
    
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
    this.experience = 1;
    this.attack = 1;
    this.defense = 1;
    this.spellPower = 1;
    this.equipment = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    this.upkeep = 100;
}

function Unit(id){
    this.id = id;
    this.count = 0;
    this.upkeepPerOne = 0;
}