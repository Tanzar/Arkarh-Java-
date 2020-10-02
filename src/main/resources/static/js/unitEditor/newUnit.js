/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var unit;
var table;

function newUnit(modal){
    unit = getNewUnitForm();
    while (modal.firstChild) {
        modal.removeChild(modal.firstChild);
    }
    var content  = newDiv("modal-content");
    modal.appendChild(content);
    var header  = makeHeader(modal, "New Unit");
    content.appendChild(header);
    var body  = makeBody(modal, unit);
    content.appendChild(body);
    var footer  = makeFooter(modal);
    content.appendChild(footer);
    

    modal.style.display = "block";//open modal

};

function makeHeader(modal, text){
    var header  = newDiv("modal-header");
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
    return header;
}

function makeBody(modal, unitForm){
    var body  = newDiv("modal-body");
    table = makeNewUnitTable(unitForm);
    body.appendChild(table.table);
    return body;
}

function makeNewUnitTable(unitForm){
    var table = new Table();
    var options = getOptions();
    table.addTextRow("Basic");
    table.addTextAndTextInput("Unit name");
    var assets = getUnitsAssets();
    table.addAssetsRow(assets);
    table.addTextAndSelect("Fraction", options.fractions);
    table.setValue("Fraction", unitForm.fraction);
    table.addTextAndSelect("Role", options.roles);
    table.setValue("Role", unitForm.role);
    table.addTextAndSelect("Category", options.categories);
    
    
    return table;
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
            table.preview.src = path;
        }
        this.addRow(textNode, select);
    }
    
    this.addTextAndSelect = function(text, optionsArray){
        var textNode = document.createTextNode(text);
        this.firstColumn.push(text);
        var select = document.createElement("select");
        for(var i = 0; i < optionsArray.length; i++){
            this.newOption(select, optionsArray[i], optionsArray[i]);
        }
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
    
    this.addTextAndTextInput = function(text){
        var textNode = document.createTextNode(text);
        this.firstColumn.push(text);
        var textInput = document.createElement("input");
        this.addRow(textNode, textInput);
    }
    
    this.addTextAndNumber = function(text, min, max){
        var textNode = document.createTextNode(text);
        this.firstColumn.push(text);
        var number = document.createElement("input");
        number.setAttribute("type", "number");
        number.setAttribute("min", min);
        number.setAttribute("max", max);
        this.addRow(textNode, number);
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
        if(secondColumn != undefined && secondColumn.value != ""){
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
        var textNode = document.createTextNode("Preview:");
        var br = document.createElement("br");
        this.preview = document.createElement("img");
        this.preview.setAttribute("width", 50);
        this.preview.setAttribute("height", 50);
        td.appendChild(textNode);
        td.appendChild(br);
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

function makeFooter(modal, newUnit){
    var footer  = newDiv("modal-footer");
    var saveButton = document.createElement("Button");
    saveButton.innerHTML = "Save";
    saveButton.onclick = function(){
        modal.style.display = "none";
    }
    footer.appendChild(saveButton);
    return footer;
}

function newDiv(className){
    var div = document.createElement('div');
    div.setAttribute('class', className);
    return div;
}

function getNewUnitForm(){
    var response = $.ajax({
        async: false,
        type: "GET",
        contentType:"application/json; charset=utf-8",
        dataType: "json",
        url: "unitEditor/getUnitForm",
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

function getOptions(){
    var response = $.ajax({
        async: false,
        type: "GET",
        contentType:"application/json; charset=utf-8",
        dataType: "json",
        url: "unitEditor/getOptions",
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

function getUnitsAssets(){
    var response = $.ajax({
        async: false,
        type: "GET",
        contentType:"application/json; charset=utf-8",
        dataType: "json",
        url: "getUnitsAssets",
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