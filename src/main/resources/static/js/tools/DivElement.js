/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function DivElement(){
    this.element = document.createElement('div');
    
    this.newLine = function(text){
        if(text != undefined){
            var txtNode = document.createTextNode(text);
            this.element.appendChild(txtNode);
        }
        var br = document.createElement('br');
        this.element.appendChild(br);
    }
    
    this.addSelect = function(text, options, item, properity){
        var title = document.createTextNode(text);
        var select = document.createElement('select');
        for(var i = 0; i < options.length; i++){
            var option = document.createElement('option');
            option.value = options[i];
            var textNode = document.createTextNode(options[i]);
            option.appendChild(textNode);
            select.appendChild(option);
        }
        select.onchange = function(){
            item[properity] = this.options[this.selectedIndex].value;
        }
        select.value = item[properity];
        select.style.width = "100px";
        this.element.appendChild(title);
        this.element.appendChild(select);
        return select;
    }
    
    this.addSelectDiffrentValueAndText = function(text, optionsValues, optionsText, item, properity){
        var title = document.createTextNode(text);
        var select = document.createElement('select');
        for(var i = 0; i < optionsValues.length; i++){
            var option = document.createElement('option');
            option.value = optionsValues[i];
            var textNode = document.createTextNode(optionsText[i]);
            option.appendChild(textNode);
            select.appendChild(option);
        }
        select.onchange = function(){
            item[properity] = Number(this.options[this.selectedIndex].value);
        }
        select.value = item[properity];
        select.style.width = "100px";
        this.element.appendChild(title);
        this.element.appendChild(select);
        return select;
    }
    
    this.addSelectValueFromSelectedItem = function(text, options, optionValueProperity, optionTextProperity, item, properity){
        var title = document.createTextNode(text);
        var select = document.createElement('select');
        for(var i = 0; i < options.length; i++){
            var option = document.createElement('option');
            option.value = options[i][optionValueProperity];
            var textNode = document.createTextNode(options[i][optionTextProperity]);
            option.appendChild(textNode);
            select.appendChild(option);
        }
        select.onchange = function(){
            if(!isNaN(item[properity])){
                item[properity] = Number(this.options[this.selectedIndex].value);
            }
            else{
                item[properity] = this.options[this.selectedIndex].value;
            }
        }
        select.value = item[properity];
        select.style.width = "100px";
        this.element.appendChild(title);
        this.element.appendChild(select);
        return select;
    }
    
    this.addText = function(text, item, properity){
        var title = document.createTextNode(text);
        var input = document.createElement('input');
        input.type = 'text';
        input.value = item[properity];
        input.style.width = "50px";
        input.onchange = function(){
            item[properity] = input.value;
        }
        input.value = item[properity];
        this.element.appendChild(title);
        this.element.appendChild(input);
    }
    
    this.addTextDisplay = function(text, item, properity){
        var title = document.createTextNode(text);
        var input = document.createTextNode(item[properity]);
        this.element.appendChild(title);
        this.element.appendChild(input);
        return input;
    }
    
    this.addNumber = function(text, item, properity, min, max){
        var title = document.createTextNode(text);
        var input = document.createElement('input');
        input.type = 'number';
        input.value = item[properity];
        input.style.width = "50px";
        if(min != undefined){
            input.min = min;
        }
        if(max != undefined){
            input.max = max;
        }
        input.onchange = function(){
            item[properity] = Number(input.value);
        }
        input.value = item[properity];
        this.element.appendChild(title);
        this.element.appendChild(input);
        return input;
    }
    
    this.addButton = function(text, onClick){
        var button = document.createElement('button');
        button.innerHTML = text;
        button.onclick = onClick;
        this.element.appendChild(button);
    }
    
    this.getHTMLElement = function(){
        return this.element;
    }
    
    this.addThisAsChild = function(div){
        div.appendChild(this.element);
    }
    
    this.clear = function(){
        this.element.innerHTML = "";
    }
}
