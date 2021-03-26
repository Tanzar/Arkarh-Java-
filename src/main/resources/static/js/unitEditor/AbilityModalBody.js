/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function AbilityModalBody(form, group, options, assets, url){
    this.item = form;
    this.table = new Table();
    
    this.table.addTextRow('Basic');
    this.table.addTextInput('Ability Name', 'name', this.item);
    this.table.addAssetsSelect(this.item, "asset", assets);
    this.table.addNumberNoMax('Charges (-1 - unlimited)', 'charges', this.item, -1);
    this.table.addNumber('Cooldown (0 - no cooldown)', 'cooldown', this.item, 0, 50);
    this.table.addNumber('Initial Cooldown (0 - no cooldown)', 'currentCooldown', this.item, 0, 50);
    
    this.table.addTextRow('Specific for ability group');
    switch(group){
        case 'heal':
            this.table.addNumberNoMax('Base Healing', 'baseHealing', this.item, 1);
            this.table.addNumberNoMax('Range', 'range', this.item, 1);
            break;
        case 'buff':
            this.table.addSelect('Targets group', options.targetsGroup, 'targetsGroup', this.item);
            this.table.addSelect('Buff trigger', options.triggers, 'trigger', this.item);
            var div = document.createElement("div");
            var addButton = document.createElement('button');
            addButton.innerHTML = "Add";
            div.appendChild(addButton);
            addButton.onclick = function(){
                var passiveDiv = new DivElement();
                passiveDiv.newLine();
                var passive = url.getFromURL("/unitEditor/getPassiveForm");
                form.passives.passives.push(passive);
                passiveDiv.addNumber('Stacks', passive, 'stacks', 1);
                passiveDiv.addNumber('Max Stacks', passive, 'stacksLimit', 1);
                passiveDiv.addNumber('Value per stack', passive, 'value');
                passiveDiv.addSelect('Effect', options.passiveEffects, passive, 'effect');
                passiveDiv.addSelect('School', options.schools, passive, 'school');
                passiveDiv.addThisAsChild(div);
            }
            var deleteButton = document.createElement('button');
            deleteButton.innerHTML = 'Remove last';
            div.appendChild(deleteButton);
            deleteButton.onclick = function(){
                var elements = div.children;
                if(elements.length > 2){
                    form.details.passives.passives.pop();
                    div.removeChild(elements[elements.length - 1]);
                }
            }
            for(var i = 0; i < this.item.passives.passives.length; i++){
                var passiveDiv = new DivElement();
                passiveDiv.newLine();
                var passive = this.item.passives.passives[i];
                passiveDiv.addNumber('Stacks', passive, 'stacks', 1);
                passiveDiv.addNumber('Max Stacks', passive, 'stacksLimit', 1);
                passiveDiv.addNumber('Value per stack', passive, 'value', 0);
                passiveDiv.addSelect('Effect', options.passiveEffects, passive, 'effect');
                passiveDiv.addSelect('School', options.schools, passive, 'school');
                passiveDiv.addThisAsChild(div);
            }
            this.table.addCustom('Passives', div);
            break;
        case 'reincarnate':
            this.table.addNumber('Health percentage', 'healthPercentage', this.item, 1, 100);
            break;
        case 'ressurect':
            this.table.addNumberNoMax('Number of targets', 'ressurected', this.item, 1);
            this.table.addNumber('Health percentage', 'healthPercentage', this.item, 1, 100);
            break;
        case 'demoralize':
            this.table.addNumberNoMax('Range around unit', 'range', this.item, 1);
            this.table.addNumberNoMax('Multiplier', 'multiplier', this.item, 1);
            break;
        case 'summon':
            this.table.addSelect('Trigger', options.triggers, 'trigger', this.item);
            this.table.addNumberNoMax('Number of summoned', 'count', this.item, 1);
            var units = url.getFromURL('/unitEditor/getAllUnits');
            var names = [];
            var ids = [];
            for(var i = 0; i < units.length; i++){
                names.push(units[i].name);
                ids.push(units[i].id);
            }
            this.table.addSelectDifferentValues('Summoned unit', names, ids, 'summonedId', this.item);
            break;
        case 'necromancy':
            this.table.addNumberNoMax('Number of risen units', 'count', this.item, 1);
            var units = url.getFromURL('/unitEditor/getAllUnits');
            var names = [];
            var ids = [];
            for(var i = 0; i < units.length; i++){
                if(units[i].category == "undead"){
                    names.push(units[i].name);
                    ids.push(units[i].id);
                }
            }
            this.table.addSelectDifferentValues('Summoned unit', names, ids, 'summonedId', this.item);
            break;
        case 'regeneration':
            this.table.addNumber('Base Healing percentage', 'baseHealing', this.item, 1, 100);
            break;
    }
    
    this.getHTMLElement = function(){
        return this.table.getHtmlElement();
    }
}