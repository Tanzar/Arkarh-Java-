/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function BattleSimEditor(attackersDiv, defendersDiv, starterDiv){
    this.attackers = {
        leader: new Leader(),
        army : []
    };
    this.defenders = {
        leader: new Leader(),
        army : []
    };
    this.armies = {
        width : 20,
        attackers : this.attackers,
        defenders : this.defenders
    }
    this.URL = new URLConnector();
    
    this.initArmyEditor = function(div, side){
        var leaderDiv = new DivElement();
        leaderDiv.addNumber("Attack", side.leader, 'attack', 1, 100);
        leaderDiv.newLine();
        leaderDiv.addNumber("Defense", side.leader, 'defense', 1, 100);
        leaderDiv.newLine();
        leaderDiv.addNumber("Spell Power", side.leader, 'spellPower', 1, 100);
        leaderDiv.newLine();
        leaderDiv.addThisAsChild(div);
        var army = side.army;
        var unitsDiv = new DivElement();
        var units = this.URL.getFromURL("/unitEditor/getAllUnits");
        var unitsIds = [];
        var unitsNames = [];
        for(var i = 0; i < units.length; i++){
            unitsIds.push(units[i].id);
            unitsNames.push(units[i].name);
        }
        unitsDiv.addButton('Add', function(){
            var unitDiv = new DivElement();
            var unit = {
                id: unitsIds[0],
                count: 1
            }
            army.push(unit);
            unitDiv.addSelectDiffrentValueAndText('Unit: ', unitsIds, unitsNames, unit, 'id');
            unitDiv.addNumber(' Count: ', unit, 'count', 1);
            unitDiv.addThisAsChild(unitsDiv.element);
        });
        unitsDiv.addButton('Remove last', function(){
            var elements = unitsDiv.getHTMLElement().children;
            if(elements.length > 2){
                army.pop();
                unitsDiv.element.removeChild(elements[elements.length - 1]);
            }
        });
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
            openInNewTabWithData('/battleSim');
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
    this.equipment = [];
    this.inventory = [];
}