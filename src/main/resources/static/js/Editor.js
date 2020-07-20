/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var editor;

function start(canvas, div){
    if(editor == undefined){
        editor = new Editor(canvas, div);
        editor.init();
        editor.start();
    }
}

function Editor(canvas, div){
    this.canvas = canvas;
    this.map;
    this.assets;
    this.camera;
    this.menu;
    this.run = true;
    this.keys = [];
    this.mousePressed = false;
    this.mouseX = 0;
    this.mouseY = 0;
    this.mousePreviousX = this.mouseX;
    this.mousePreviousY = this.mouseY;
    this.marking = false;
    this.init = function(){
        this.map = this.setupMap(20, 10);
        this.assets = this.setupAssets();
        this.camera = new Camera(0, 0, this.canvas.width, this.canvas.height)
        this.menu = new Menu(div, this.assets);
        this.menu.init();
        this.setKeyBoardEvents();
        this.setMouseEvents();
    }
    this.getCanvasContext = function(){
        return this.canvas.getContext("2d");
    }
    this.setupMap = function(width, height){
        var boardParameters = {
            width : width,
            height : height
        }
        var response = $.ajax({
            async: false,
            type: "POST",
            contentType:"application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(boardParameters),
            url: "/newBoard",
            success: function (response) {
                return response;
                console.log();
            },
            error : function(response) {
                console.log(response);
                return undefined;
            }
        });
        return new Map(response.responseJSON);
    }
    this.setupAssets = function(){
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
        return new Assets(response.responseJSON);
    }
    this.start = function(){
        this.run = true;
        requestAnimationFrame(newFrame);
    }
    this.stop = function(){
        this.run = false;
    }
    
    this.frame = function(){
        ctx = this.getCanvasContext();
        this.camera.move(this.map);
        this.map.drawMap(ctx, this.camera, this.assets);
        
    }
    this.setKeyBoardEvents = function(){
        window.addEventListener('keydown', function (e) {
            e.preventDefault();
            editor.keys[e.keyCode] = true;
        })
        window.addEventListener('keyup', function (e) {
            editor.keys[e.keyCode] = false;
        })
    }
    this.setMouseEvents = function(){
        editor.canvas.onmousemove = function(e){
            editor.marking = false;
            editor.mousePreviousX = editor.mouseX;
            editor.mousePreviousY = editor.mouseY;
            editor.mouseX = e.clientX - editor.canvas.getBoundingClientRect().left;
            editor.mouseY = e.clientY - editor.canvas.getBoundingClientRect().top;
            if(editor.mousePressed){
                editor.camera.x += editor.mousePreviousX - editor.mouseX;
                editor.camera.y += editor.mousePreviousY - editor.mouseY;
            }
        };
        editor.canvas.addEventListener("mousedown", function(e){
            editor.mousePressed = true;
            editor.marking = true;
        });
        editor.canvas.addEventListener("mouseup", function(e){
            editor.mousePressed = false;
            if(editor.marking == true){
                var x = editor.camera.x + e.x - editor.canvas.getBoundingClientRect().left;
                var y = editor.camera.y + e.y - editor.canvas.getBoundingClientRect().top;
                x = Math.floor((x - 1)/editor.map.fieldsWidth);
                y = Math.floor((y - 1)/editor.map.fieldsHeight);
                editor.map.markField(x, y);
                editor.menu.setOption(editor.menu.terrains, editor.map.fields[x][y].terrainIndex);
            }
        });
        editor.canvas.addEventListener("mouseout", function(e){
            editor.mousePressed = false;
        });
    }
}

function Map(pattern){
    this.fields = pattern.fields;
    this.fieldsWidth = pattern.fieldWidth;
    this.fieldsHeight = pattern.fieldHeight;
    this.width = pattern.width;
    this.height = pattern.height;
    img = new Image();
    img.src = "img/marker.png";
    this.marker = {
        asset: img,
        x: 0,
        y: 0
    }
    this.drawMap = function(ctx, camera, assets){
        for(y = 0; y < this.height; y++){
            for(x = 0; x < this.width; x++){
                var field = this.fields[x][y];
                var fieldX = (x * this.fieldsWidth) - camera.x;
                var fieldY = (y * this.fieldsHeight) - camera.y;
                ctx.save();
                ctx.translate(fieldX, fieldY);//position of img
                var terrain = assets.getAsset("terrain", field.terrainIndex);//draw terrain
                ctx.drawImage(terrain, 0, 0, this.fieldsWidth, this.fieldsHeight);
                var asset = assets.getAsset(field.eventType, field.assetIndex);
                ctx.drawImage(asset, 0, 0, this.fieldsWidth, this.fieldsHeight);
                if(this.marker.x == x && this.marker.y == y){
                    ctx.drawImage(this.marker.asset, 0, 0, this.fieldsWidth, this.fieldsHeight);
                }
                ctx.restore();
                
            }
        }
    }
    this.markField = function(x, y){
        this.marker.x = x;
        this.marker.y = y;
    }
}

function Assets(assets){
    this.terrains = assets.terrains;
    this.enviroments = assets.enviroments;
    
    this.refactorAssets = function(array){
        for(i = 0; i < array.length; i++){
            var path = array[i].path;
            array[i].img = new Image();
            array[i].img.src = path;
        }
    }
    this.refactorAssets(this.terrains);
    this.refactorAssets(this.enviroments);
    
    this.getAsset = function(type, index){
        switch(type){
            case "terrain":
                return this.terrains[index].img;
                break;
            case "enviroment":
                return this.enviroments[index].img;
                break;
            default:
                return new Image();
        }
    }
}

function Camera(x, y, width, height){
    this.x = x;
    this.y = y;
    this.speed = 5;
    this.width = width;
    this.height = height;
    this.changeX = 0;
    this.changeY = 0;
    this.move = function(map){
        this.x += this.changeX * this.speed;
        this.y += this.changeY * this.speed;
        if(this.x > map.fieldsWidth * map.width  - this.width){
            this.x = map.fieldsWidth * map.width - this.width;
        }
        if(this.x < 0){
            this.x = 0;
        }
        if(this.y > map.fieldsHeight * map.height - this.height){
            this.y = map.fieldsHeight * map.height - this.height;
        }
        if(this.y < 0){
            this.y = 0;
        }
        this.changeX = 0;
        this.changeY = 0;
    }
    this.moveLeft = function(){
        this.changeX = 1;
    }
    this.moveRight = function(){
        this.changeX = -1;
    }
    this.moveUp = function(){
        this.changeY = -1;
    }
    this.moveDown = function(){
        this.changeY = 1;
    }
}

function Menu(div, assets){
    this.menuTable;
    this.div = div;
    this.assets = assets;
    this.eventTypes;
    this.parametersText;
    this.parametersSelect;
    this.terrains;
    this.enviroments;
    this.events;
    this.init = function(){
        this.menuTable = document.createElement("table");
        var row = this.menuTable.insertRow(0);
        this.setupTerrains(row);
        row = this.menuTable.insertRow(1);
        this.setupEvents(row);
        row = this.menuTable.insertRow(2);
        this.setupParameters(row);
        this.div.appendChild(this.menuTable);
        this.setupTerrainChange();
        this.setupEventTypesChange();
        this.setupParametersChange();
    }
    this.setupTerrains = function(row){
        var textCell = row.insertCell(0);
        var tekst = document.createTextNode("Teren: ");
        textCell.appendChild(tekst);
        var optionsCell = row.insertCell(1);
        this.terrains = document.createElement("select");
        this.terrains = this.setOptions(this.terrains, this.assets.terrains)
        optionsCell.appendChild(this.terrains);
    }
    this.setupEvents = function(row){
        var textCell = row.insertCell(0);
        var tekst = document.createTextNode("Typ zdarzenia: ");
        textCell.appendChild(tekst);
        var optionsCell = row.insertCell(1);
        this.eventTypes = document.createElement("select");
        var option = this.newOption("Brak", "none");
        this.eventTypes.appendChild(option);
        var option = this.newOption("Środowisko", "enviroment");
        this.eventTypes.appendChild(option);
        var option = this.newOption("Przedmioty", "item");
        this.eventTypes.appendChild(option);
        var option = this.newOption("Wrogowie", "enemy");
        this.eventTypes.appendChild(option);
        var option = this.newOption("Lokacje", "location");
        this.eventTypes.appendChild(option);
        var option = this.newOption("Gracze", "player");
        this.eventTypes.appendChild(option);
        var option = this.newOption("Wydarzenia", "event");
        this.eventTypes.appendChild(option);
        optionsCell.appendChild(this.eventTypes);
    }
    this.setupParameters = function(row){
        var textCell = row.insertCell(0);
        this.parametersText = document.createTextNode("Brak");
        textCell.appendChild(this.parametersText);
        var optionsCell = row.insertCell(1);
        this.parametersSelect = document.createElement("select");
        this.parametersSelect = this.setOptions(this.parametersSelect, []);
        optionsCell.appendChild(this.parametersSelect);
    }
    this.changeParametersSetup = function(eventType){
        switch(eventType){
            case "enviroment":
                this.parametersText.nodeValue = "Środowisko: ";
                this.parametersSelect = this.setOptions(this.parametersSelect, this.assets.enviroments);
                break;
            default:
                this.parametersText.nodeValue = "Brak";
                this.parametersSelect = this.setOptions(this.parametersSelect, []);
                
        }
    }
    this.newOption = function(text, value){
        var tmp = document.createElement("option");
        tmp.setAttribute("value", value);
        var textNode = document.createTextNode(text);
        tmp.appendChild(textNode);
        return tmp;
    }
    this.setOptions = function(select, optionsArray){
        for(i = select.length - 1; i >= 0; i--){
            select.remove(i);
        }
        for(i = 0; i < optionsArray.length; i++){
            var option = optionsArray[i];
            select.appendChild(this.newOption(option.name, option.id));
        }
        return select;
    }
    this.setOption = function(select, optionIndex){
        select.selectedIndex = optionIndex;
    }
    this.setupTerrainChange = function(){
        this.terrains.onchange = function(){
            index = this.selectedIndex;
            x = editor.map.marker.x;
            y = editor.map.marker.y;
            editor.map.fields[x][y].terrainIndex = index;
        }
    }
    this.setupEventTypesChange = function(){
        this.eventTypes.onchange = function(){
            eventType = this.value;
            x = editor.map.marker.x;
            y = editor.map.marker.y;
            editor.map.fields[x][y].eventType = eventType;
            if(eventType == "none"){
                editor.map.fields[x][y].assetIndex = 0;
            }
            editor.menu.changeParametersSetup(eventType);
        }
    }
    this.setupParametersChange = function(){
        this.parametersSelect.onchange = function(){
            index = this.selectedIndex;
            x = editor.map.marker.x;
            y = editor.map.marker.y;
            editor.map.fields[x][y].assetIndex = index;
        }
    }
}

function newFrame(){
    editor.frame();
    if(editor.run == true){
        requestAnimationFrame(newFrame);
    }
}