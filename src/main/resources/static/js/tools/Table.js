/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function Table(){
    this.table = document.createElement("table");
    
    this.addAssetsSelect = function(item, properity, assets){
        var textNode = document.createTextNode("Asset");
        var select = this.assetsSelect(item, properity, assets);
        var path = select.options[select.selectedIndex].path;
        var preview = this.assetsPreview(path);
        select.onchange = function(){
            var option = this.options[this.selectedIndex];
            preview.src = option.path;
            item[properity] = option.value;
        }
        var element = document.createElement("div");
        element.appendChild(select);
        var br = document.createElement("br");
        element.appendChild(br);
        element.appendChild(preview);
        this.addRow(textNode, element);
        return select;
    }
    
    this.assetsSelect = function(item, properity, assets){
        var select = document.createElement("select");
        for(var i = 0; i < assets.length; i++){
            this.newOption(select, assets[i].name, assets[i].name, assets[i].path);
            if(item[properity] == assets[i].name){
                select.selectedIndex = i;
            }
        }
        return select;
    }
    
    this.assetsPreview = function(path){
        var preview = document.createElement("img");
        preview.setAttribute("width", 50);
        preview.setAttribute("height", 50);
        preview.setAttribute("src", path);
        return preview;
    }
    
    this.addSelect = function(text, optionsArray, properity, item){
        var textNode = document.createTextNode(text);
        var select = document.createElement("select");
        for(var i = 0; i < optionsArray.length; i++){
            this.newOption(select, optionsArray[i], optionsArray[i]);
            if(item[properity] == optionsArray[i]){
                select.selectedIndex = i;
            }
        }
        select.value = item[properity];
        this.addOnChange(properity, item, select);
        this.addRow(textNode, select);
        return select;
    }
    
    this.addSelectDifferentValues = function(text, textArray, valuesArray, properity, item){
        var textNode = document.createTextNode(text);
        var select = document.createElement("select");
        if(textArray.length == valuesArray.length){
            for(var i = 0; i < textArray.length; i++){
                this.newOption(select, textArray[i], valuesArray[i]);
                if(item[properity] == valuesArray[i]){
                    select.selectedIndex = i;
                }
            }
        }
        select.value = item[properity];
        this.addOnChange(properity, item, select);
        this.addRow(textNode, select);
        return select;
    }
    
    this.addCustom = function(text, div){
        var textNode = document.createTextNode(text);
        this.addRow(textNode, div);
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
    
    this.addTextInput = function(text, properity, item){
        var textNode = document.createTextNode(text);
        var textInput = document.createElement("input");
        textInput.setAttribute("type", "text");
        textInput.setAttribute("value", item[properity]);
        textInput.value = item[properity];
        this.addOnChange(properity, item, textInput);
        this.addRow(textNode, textInput);
        return textInput;
    }
    
    this.addNumber = function(text, properity, item, min, max){
        var textNode = document.createTextNode(text);
        var number = document.createElement("input");
        number.setAttribute("type", "number");
        if(min != undefined){
            number.setAttribute("min", min);
        }
        if(max != undefined){
            number.setAttribute("max", max);
        }
        number.value = item[properity];
        this.addOnChange(properity, item, number);
        this.addRow(textNode, number);
        return number;
    }
    
    this.addNumberNoMax = function(text, properity, item, min){
        var textNode = document.createTextNode(text);
        var number = document.createElement("input");
        number.setAttribute("type", "number");
        if(min != undefined){
            number.setAttribute("min", min);
        }
        number.value = item[properity];
        this.addOnChange(properity, item, number);
        this.addRow(textNode, number);
        return number;
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
        var tdTwo = document.createElement("td");
        tdTwo.appendChild(secondColumn);
        tr.appendChild(tdTwo);
        this.table.appendChild(tr);
    }
    
    this.getHtmlElement = function(){
        return this.table;
    }
}