/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function UnitModalBody(unit, options, assets){
    this.unit = unit;
    this.table = new Table();
    
    this.table.addTextRow("Basic");
    this.table.addTextInput("Unit name", 'name', this.unit);
    this.table.addAssetsSelect(this.unit, 'assetName', assets);
    this.table.addSelect("Fraction", options.fractions, 'fraction', this.unit);
    this.table.addSelect("Role", options.roles, 'role', this.unit);
    this.table.addSelect("Tier", options.tiers, 'tier', this.unit);
    this.table.addSelect("Category", options.categories, 'category', this.unit);

    this.table.addTextRow("Offensive");
    this.table.addNumberNoMax("Attack", 'attack', this.unit.offensive, 1);
    this.table.addNumberNoMax("Spell Power", 'spellPower', this.unit.offensive, 0);
    this.table.addNumberNoMax("Damage", 'damage', this.unit.offensive, 1);
    this.table.addSelect("Attack Style", options.attacks, 'attackStyle', this.unit.abilities.abilities[0]);
    this.table.addSelect("Attack School", options.schools, 'school', this.unit.abilities.abilities[0]);
    this.table.addNumberNoMax("Range", 'range', this.unit.abilities.abilities[0], 1);
    this.table.addNumberNoMax("Cleave range", 'areaSize', this.unit.abilities.abilities[0], 0);
    
    this.table.addTextRow("Defensive");
    this.table.addNumberNoMax("Defense", 'defense', this.unit.defensive, 1);
    this.table.addNumberNoMax("Health", 'baseHealth', this.unit.defensive, 1);
    this.table.addNumber("Armor", 'armor', this.unit.defensive, 0, 75);
    this.table.addNumber("Ward", 'ward', this.unit.defensive, 0, 75);
      
    this.table.addTextRow("Special");
    this.table.addNumberNoMax("Upkeep", 'upkeep', this.unit.special, 1);
    this.table.addNumberNoMax("Speed", 'speed', this.unit.special, 1);
    this.table.addNumberNoMax("Base Morale", 'baseMorale', this.unit.special, 1000);
    
    this.getHTMLElement = function(){
        return this.table.getHtmlElement();
    }
}