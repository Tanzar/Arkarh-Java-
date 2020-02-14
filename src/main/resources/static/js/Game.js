/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var game = new Game();
var editor;


function startGame(canvas, parameters, editorMenuDiv){
    game.stop();
    game.init(canvas);
    if(editorMenuDiv != undefined){
        if(editor == undefined){
            editor = new Editor(editorMenuDiv);
        }
        game.setEmptyMap(parameters.width, parameters.height, parameters.fieldsWidth, parameters.fieldsHeight);
        game.start();
    }
    else{
        
    }
}

function stopGame(){
    game.run = false;
}

function Editor(menuDiv){
    this.editorMenu = new EditorMenu(menuDiv);
    this.editorMenu.init();
    this.menuDiv = menuDiv;
}

function EditorMenu(menuDiv){
    this.menuDiv = menuDiv;
    this.menuTable;
    this.typesSelect;
    this.optionsSelect;
    this.types = [];
    var terrains = $.ajax({
        async: false,
        type: "GET",
        contentType:"application/json; charset=utf-8",
        dataType: "json",
        url: "/allTerrains",
        success: function (response) {
            return response.responseJSON;
            console.log();
        },
        error : function(response) {
            console.log(response);
            return undefined;
        }
    });
    terrains = terrains.responseJSON;
    this.types.push(terrains);
    var items = [];
    this.types.push(items);
    var obstacles = [];
    this.types.push(obstacles);
    var events = [];
    this.types.push(events);
    this.selectedListNumber;
    this.selectedListItem;
    this.init = function(){
        this.initAll();
    }
    this.initAll = function(){
        this.menuTable = document.createElement("table");
        var row = this.menuTable.insertRow(0);
        this.initTypes(row);
        this.initOptions(row);
        this.menuDiv.appendChild(this.menuTable);
    }
    this.newOption = function(text, value){
        var tmp = document.createElement("option");
        tmp.setAttribute("value", value);
        var textNode = document.createTextNode(text);
        tmp.appendChild(textNode);
        return tmp;
    }
    this.initTypes = function(row){
        var typesCell = row.insertCell(0);
        this.typesSelect = document.createElement("select");
        this.typesSelect.appendChild(this.newOption("Teren", 0));
        this.typesSelect.appendChild(this.newOption("Przedmioty", 1));
        this.typesSelect.appendChild(this.newOption("Przeszkody", 2));
        this.typesSelect.appendChild(this.newOption("Lokacje", 3));
        this.typesSelect.onchange = function(){
            var selected = this.value;
            editor.editorMenu.setOptions(selected);
            console.log(selected);
        }
        typesCell.appendChild(this.typesSelect);
    }
    this.initOptions = function(row){
        var optionsCell = row.insertCell(1);
        this.optionsSelect = document.createElement("select");
        this.setOptions(this.typesSelect.value);
        this.optionsSelect.onchange = function(){
            var itemNumber = this.value - 1;
            var listNumber = editor.editorMenu.selectedListNumber;
            editor.editorMenu.selectedListItem = editor.editorMenu.types[listNumber][itemNumber];
        }
        optionsCell.appendChild(this.optionsSelect);
    }
    this.setOptions = function(selectedListNumber){
        this.selectedListNumber = selectedListNumber;
        this.refillOptions(this.types[this.selectedListNumber]);
        this.selectedListItem = this.types[selectedListNumber][this.optionsSelect.value - 1];
    }
    this.refillOptions = function(optionsArray){
        for(i = this.optionsSelect.length - 1; i >= 0; i--){
            this.optionsSelect.remove(i);
        }
        for(i = 0; i < optionsArray.length; i++){
            var option = optionsArray[i];
            this.optionsSelect.appendChild(this.newOption(option.name, option.id));
        }
    }
}

function Game(){
    this.canvas;
    this.items = [];
    this.map;
    this.camera;
    this.run = true;
    this.keys = [];
    this.mousePressed = false;
    this.mouseX = 0;
    this.mouseY = 0;
    this.timeout;
    this.mousePreviousX = this.mouseX;
    this.mousePreviousY = this.mouseY;
    this.init = function(canvas){
        this.canvas = canvas;
        this.camera = new Camera(0, 0, canvas.width, canvas.height);
        this.setKeyBoardEvents();
        this.setMouseEvents();
    }
    this.stop = function(){
        this.run = false;
    }
    this.start = function(){
        this.run = true;
        requestAnimationFrame(newFrame);
    }
    this.setEmptyMap = function(width, height, fieldsWidth, fieldsHeight){
        var boardParameters = {
            width : width,
            height : height,
            fieldsWidth : fieldsWidth,
            fieldsHeight : fieldsHeight
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
        this.map = new Map(response.responseJSON);
    }
    this.drawFrame = function(contextFromCanvas){
        this.map.drawMap(contextFromCanvas, this.camera);
    }
    this.setKeyBoardEvents = function(){
        window.addEventListener('keydown', function (e) {
            e.preventDefault();
            game.keys[e.keyCode] = true;
        })
        window.addEventListener('keyup', function (e) {
            game.keys[e.keyCode] = false;
        })
    }
    this.setMouseEvents = function(){
        game.canvas.onmousemove = function(e){
            game.mousePreviousX = game.mouseX;
            game.mousePreviousY = game.mouseY;
            game.mouseX = e.clientX - game.canvas.getBoundingClientRect().left;
            game.mouseY = e.clientY - game.canvas.getBoundingClientRect().top;
            if(game.mousePressed){
                game.camera.x += game.mousePreviousX - game.mouseX;
                game.camera.y += game.mousePreviousY - game.mouseY;
            }
        };
        game.canvas.addEventListener("mousedown", function(e){
            game.mousePressed = true;
        });
        game.canvas.addEventListener("mouseup", function(e){
            game.mousePressed = false;
        });
        game.canvas.addEventListener("mouseout", function(e){
            game.mousePressed = false;
        });
        game.canvas.addEventListener("dblclick", function(e){
            var x = game.camera.x + e.x - game.canvas.getBoundingClientRect().left;
            var y = game.camera.y + e.y - game.canvas.getBoundingClientRect().top;
            x = Math.floor((x - 1)/game.map.fieldsWidth);
            y = Math.floor((y - 1)/game.map.fieldsHeight);
            
            game.map.changeGround(x, y, editor.editorMenu.selectedListItem);
        });
    }
}

function Map(pattern){
    this.fields = pattern.fields;
    this.fieldsWidth = pattern.fieldWidth;
    this.fieldsHeight = pattern.fieldHeight;
    this.width = pattern.width;
    this.height = pattern.height;
    for(x = 0; x < pattern.width; x++){
        for(y = 0; y < pattern.height; y++){
            this.fields[x][y].terrain.assetImg = new Image();
            this.fields[x][y].terrain.assetImg.src = this.fields[x][y].terrain.asset;
        }
    }
    this.drawMap = function(ctx, camera){
        for(y = 0; y < this.height; y++){
            for(x = 0; x < this.width; x++){
                var field = this.fields[x][y];
                var fieldX = (x * this.fieldsWidth) - camera.x;
                var fieldY = (y * this.fieldsHeight) - camera.y;
                ctx.save();
                ctx.translate(fieldX, fieldY);//position of img
                var asset = field.terrain.assetImg;
                ctx.drawImage(asset, 0, 0, this.fieldsWidth, this.fieldsHeight);                       // draw image at current position
                ctx.restore();
            }
        }
    }
    this.changeGround = function(x, y, item){
        this.fields[x][y].terrain.name = item.name;
        this.fields[x][y].terrain.asset = item.asset;
        this.fields[x][y].terrain.assetImg.src = item.asset;
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

function element(img, x, y, width, height){
    this.x = x;
    this.y = y;
    this.angle = 0;
    this.angleChange = 0;
    this.speed = 7;
    this.image = img;
    this.width = width;
    this.height = height;
    this.newPosition = function(){
        this.angle += this.angleChange;
        this.x += this.speed * Math.sin(this.angle);
        this.y -= this.speed * Math.cos(this.angle);
    }
    this.move = function(){
    }
    this.clearMove = function(){
        this.angleChange = 0;
        this.speed = 0;
    }
    this.animate = function(ctx){
        ctx.save();
        this.move();
        this.newPosition();
        this.clearMove();
        ctx.translate(this.x, this.y);//position of img
        ctx.rotate(this.angle);
        ctx.drawImage(this.image, -this.width/2,-this.height/2, this.width, this.height);                       // draw image at current position
        ctx.restore();
    }
}

function newFrame(){
    var ctx = game.canvas.getContext("2d");
    ctx.clearRect(0, 0, game.canvas.width, game.canvas.height);
    if (game.keys[37]) {game.camera.moveRight()}
    if (game.keys[39]) {game.camera.moveLeft()}
    if (game.keys[38]) {game.camera.moveUp()}
    if (game.keys[40]) {game.camera.moveDown()}
    game.camera.move(game.map);
    game.drawFrame(ctx);
    for(i = 0; i < game.items.length; i++){
        game.items[i].animate(ctx);
    }
    if(game.run == true){
        requestAnimationFrame(newFrame);
    }
}