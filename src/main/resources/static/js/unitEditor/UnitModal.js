/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var unitModal;

function createNewUnit(modal, dataTable){
    unitModal = new UnitModal(modal, dataTable);
    unitModal.start();
}

function updateUnit(modal, dataTable, unit){
    unitModal = new UnitModal(modal, dataTable);
    unitModal.start(unit);
}

function UnitModal(div, dataTable){
    this.modal = div;
    this.dataTable = dataTable;
    this.unit;
    this.table = new Table();
    
    this.start = function(unit){
        var edit = false;
        if(unit != undefined){
            edit = true;
        }
        this.unit = this.loadUnit(unit);
        var options = this.getFromUrl("unitEditor/getOptions");
        var assets = this.getFromUrl("getUnitsAssets");
        this.clearModal();
        var content  = this.newDiv("modal-content");
        this.modal.appendChild(content);
        this.makeHeader(content, this.modal, "New Unit");
        this.makeBody(content, options, assets);
        this.makeFooter(content, this.modal, edit);
        this.modal.style.display = "block";//open modal
    }
    
    this.loadUnit = function(unit){
        if(unit == undefined){
            return this.getFromUrl("unitEditor/getUnitForm");
        }
        else{
            return unit;
        }
    }
    
    this.clearModal = function(){
        while (this.modal.firstChild) {
            this.modal.removeChild(this.modal.firstChild);
        }
    }
    
    this.getFromUrl = function(url){
        var response = $.ajax({
            async: false,
            type: "GET",
            contentType:"application/json; charset=utf-8",
            dataType: "json",
            url: url,
            success: function (response) {
                return response;
            },
            error : function(response) {
                console.log(response);
                return undefined;
            }
        });
        return response.responseJSON;
    }
    
    this.makeHeader = function(content, modal, text){
        var header  = this.newDiv("modal-header");
        var span = document.createElement("span");
        span.setAttribute('class', "close");
        span.innerHTML = "&times;";
        // When the user clicks on <span> (x), close the modal
        span.onclick = function() {
          modal.style.display = "none";
        }
        header.appendChild(span);
        var headerText = document.createElement("h2");
        headerText.innerHTML = text;
        header.appendChild(headerText);
        content.appendChild(header);
    }
    
    this.makeBody = function(content, options, assets){
        var body  = this.newDiv("modal-body");
        this.table = this.makeNewUnitTable(options, assets);
        body.appendChild(this.table.table);
        content.appendChild(body);
    }
    
    this.makeNewUnitTable = function(options, assets){
        var table = new Table();
        this.setupBasic(table, options, assets);
        this.setupOffensive(table, options);
        this.setupDefensive(table);
        this.setupSpecial(table);
        return table;
    }

    this.setupBasic = function(table, options, assets){
        table.addTextRow("Preview:");
        table.addTextRow("Basic");
        table.addTextAndTextInput("Unit name", this.unit.name, 'name', this.unit);
        table.addAssetsRow(assets);
        table.addTextAndSelect("Fraction", options.fractions, 'fraction', this.unit);
        table.setValue("Fraction", this.unit.fraction);
        table.addTextAndSelect("Role", options.roles, 'role', this.unit);
        table.setValue("Role", this.unit.role);
        table.addTextAndSelect("Tier", options.tiers, 'tier', this.unit);
        table.setValue("Tier", this.unit.tier);
        table.addTextAndSelect("Category", options.categories, 'category', this.unit);
        table.setValue("Category", this.unit.category);

    }

    this.setupOffensive = function(table, options){
        table.addTextRow("Offensive");
        table.addTextAndNumberNoMax("Attack", 1, 'attack', this.unit.offensive);
        table.setValue("Attack", this.unit.offensive.attack);
        table.addTextAndNumberNoMax("Spell Power", 0, 'spellPower', this.unit.offensive);
        table.setValue("Spell Power", this.unit.offensive.spellPower);
        table.addTextAndNumberNoMax("Damage", 1, 'damage', this.unit.offensive);
        table.setValue("Damage", this.unit.offensive.damage);
        table.addTextAndSelect("Attack Style", options.attacks, 'attackStyle', this.unit.abilities.abilities[0]);
        table.setValue("Attack Style", this.unit.abilities.abilities[0].attackStyle);
        table.addTextAndSelect("Attack School", options.schools, 'school', this.unit.abilities.abilities[0]);
        table.setValue("Attack School", this.unit.abilities.abilities[0].school);
        table.addTextAndNumberNoMax("Range", 1, 'range', this.unit.abilities.abilities[0]);
        table.setValue("Range", this.unit.abilities.abilities[0].range);
        table.addTextAndNumberNoMax("Cleave range", 1, 'areaSize', this.unit.abilities.abilities[0]);
        table.setValue("Cleave range", this.unit.abilities.abilities[0].areaSize);
    }

    this.setupDefensive = function(table){
        table.addTextRow("Defensive");
        table.addTextAndNumberNoMax("Defense", 1, 'defense', this.unit.defensive);
        table.setValue("Defense", this.unit.defensive.defense);
        table.addTextAndNumberNoMax("Health", 1, 'baseHealth', this.unit.defensive);
        table.setValue("Health", this.unit.defensive.baseHealth);
        table.addTextAndNumber("Armor", 0, 75, 'armor', this.unit.defensive);
        table.setValue("Armor", this.unit.defensive.armor);
        table.addTextAndNumber("Ward", 0, 75, 'ward', this.unit.defensive);
        table.setValue("Ward", this.unit.defensive.ward);
    }

    this.setupSpecial = function(table){
        table.addTextRow("Special");
        table.addTextAndNumberNoMax("Upkeep", 1, 'upkeep', this.unit.special);
        table.setValue("Upkeep", this.unit.special.upkeep);
        table.addTextAndNumberNoMax("Speed", 1, 'speed', this.unit.special);
        table.setValue("Speed", this.unit.special.speed);
        table.addTextAndNumberNoMax("Base Morale", 1000, 'baseMorale', this.unit.special);
        table.setValue("Base Morale", this.unit.special.baseMorale);
    }
    
    this.makeFooter = function(content, modal, edit){
        var footer  = this.newDiv("modal-footer");
        var saveButton = document.createElement("Button");
        saveButton.innerHTML = "Save";
        if(edit){
            saveButton.onclick = function(){
                console.log(unitModal.unit);
                unitModal.updateUnit(unitModal.unit);
                unitModal.dataTable.refresh();
                modal.style.display = "none";
            }
        }
        else{
            saveButton.onclick = function(){
                console.log(unitModal.unit);
                unitModal.addNewUnit(unitModal.unit);
                unitModal.dataTable.refresh();
                modal.style.display = "none";
            }
        }
        footer.appendChild(saveButton);
        content.appendChild(footer);
    }
    
    this.newDiv = function(className){
        var div = document.createElement('div');
        div.setAttribute('class', className);
        return div;
    }
    
    this.addNewUnit = function(unitForm){
        var response = $.ajax({
            async: false,
            type: "POST",
            contentType:"application/json",
            data: JSON.stringify(unitForm),
            dataType: "json",
            url: "unitEditor/addUnit",
            success: function (response) {
                return response;
            },
            error : function(response) {
                console.log(response);
                return undefined;
            }
        });
        return response.responseJSON;
    }
    
    this.updateUnit = function(unitForm){
        var response = $.ajax({
            async: false,
            type: "POST",
            contentType:"application/json",
            data: JSON.stringify(unitForm),
            dataType: "json",
            url: "unitEditor/updateUnit",
            success: function (response) {
                return response;
            },
            error : function(response) {
                console.log(response);
                return undefined;
            }
        });
        return response.responseJSON;
    }
    
}

function Table(){
    this.table = document.createElement("table");
    this.firstColumn = [];
    this.secondColumn = [];
    this.preview;
    
    this.addAssetsRow = function(optionsArray){
        var textNode = document.createTextNode("Asset");
        this.firstColumn.push("Asset");
        var select = document.createElement("select");
        for(var i = 0; i < optionsArray.length; i++){
            this.newOption(select, optionsArray[i].name, optionsArray[i].name, optionsArray[i].path);
        }
        this.preview.setAttribute("src", select.options[select.selectedIndex].path)
        select.onchange = function(){
            var path = this.options[this.selectedIndex].path;
            unitModal.table.preview.src = path;
        }
        this.addRow(textNode, select);
    }
    
    this.addTextAndSelect = function(text, optionsArray, properityName, unit){
        var textNode = document.createTextNode(text);
        this.firstColumn.push(text);
        var select = document.createElement("select");
        for(var i = 0; i < optionsArray.length; i++){
            this.newOption(select, optionsArray[i], optionsArray[i]);
        }
        this.addOnChange(properityName, unit, select);
        this.addRow(textNode, select);
    }
    
    this.newOption = function(select, text, value, path){
        var option = document.createElement('option');
        var textNode = document.createTextNode(text);
        option.appendChild(textNode);
        option.value = value;
        if(path != undefined){
            option.path = path;
        }
        select.appendChild(option);
    }
    
    this.addTextAndTextInput = function(text, inputText, properityName, unit){
        var textNode = document.createTextNode(text);
        this.firstColumn.push(text);
        var textInput = document.createElement("input");
        textInput.setAttribute("type", "text");
        textInput.setAttribute("value", inputText);
        this.addOnChange(properityName, unit, textInput);
        this.addRow(textNode, textInput);
    }
    
    this.addTextAndNumber = function(text, min, max, properityName, unit){
        var textNode = document.createTextNode(text);
        this.firstColumn.push(text);
        var number = document.createElement("input");
        number.setAttribute("type", "number");
        if(min != undefined){
            number.setAttribute("min", min);
        }
        if(max != undefined){
            number.setAttribute("max", max);
        }
        this.addOnChange(properityName, unit, number);
        this.addRow(textNode, number);
    }
    
    this.addTextAndNumberNoMax = function(text, min, properityName, unit){
        var textNode = document.createTextNode(text);
        this.firstColumn.push(text);
        var number = document.createElement("input");
        number.setAttribute("type", "number");
        if(min != undefined){
            number.setAttribute("min", min);
        }
        this.addOnChange(properityName, unit, number);
        this.addRow(textNode, number);
    }
    
    this.addOnChange = function(properityName, item, inputField){
        inputField.properityName = properityName;
        inputField.onchange = function(){
            var previous = item[this.properityName];
            var changed = this.value;
            console.log("Changing: " + this.properityName + " from " + previous + " to " + changed);
            if(this.getAttribute("type") == "number"){
                item[this.properityName] = Number(this.value);
            }
            else{
                item[this.properityName] = this.value;
            }
        }
    }
    
    this.addTextRow = function(text){
        var textNode = document.createTextNode(text);
        var emptyNode = document.createTextNode("");
        this.addRow(textNode, emptyNode);
    }
    
    this.addRow = function(firstColumn, secondColumn){
        var tr = document.createElement("tr");
        var tdOne = document.createElement("td");
        tdOne.appendChild(firstColumn);
        tr.appendChild(tdOne);
        if(secondColumn != undefined && secondColumn.value != undefined){
            var tdTwo = document.createElement("td");
            tdTwo.appendChild(secondColumn);
            tr.appendChild(tdTwo);
            this.secondColumn.push(secondColumn);
        }
        if(this.preview == undefined){
            this.initPrewiew(tr);
        }
        this.table.appendChild(tr);
    }
    
    this.initPrewiew = function(tr){
        var td = document.createElement("td");
        this.preview = document.createElement("img");
        this.preview.setAttribute("width", 50);
        this.preview.setAttribute("height", 50);
        td.appendChild(this.preview);
        tr.appendChild(td);
    }
    
    this.getValue = function(parameter){
        var index = this.getIndex(parameter);
        if(index > -1 && index < this.secondColumn.length){
            return this.secondColumn[index];
        }
        else{
            return "none";
        }
    }
    
    this.setValue = function(parameter, value){
        var index = this.getIndex(parameter);
        if(index > -1 && index < this.secondColumn.length){
            var field = this.secondColumn[index];
            field.value = value;
        }
    }
    
    this.getIndex = function(parameter){
        var index = -1;
        for(var i = 0; i < this.firstColumn.length; i++){
            if(this.firstColumn[i] == parameter){
                index = i;
                break;
            }
        }
        return index;
    }
}

function addNewUnit(unitForm){
    var response = $.ajax({
        async: false,
        type: "POST",
        contentType:"application/json",
        data: JSON.stringify(unitForm),
        dataType: "json",
        url: "unitEditor/addUnit",
        success: function (response) {
            return response;
        },
        error : function(response) {
            console.log(response);
            return undefined;
        }
    });
    return response.responseJSON;
}