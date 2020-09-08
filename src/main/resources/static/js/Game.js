/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var game = new Game();
var editor;


function startGame(canvas, seed){
    game.stop();
    game.init(canvas);
    game.setEmptyMap(seed, 100, 100);
    game.start();
}

function stopGame(){
    game.run = false;
}


function Game(){
    this.canvas;
    this.items = [];
    this.map;
    this.assets;
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
    this.setEmptyMap = function(seed, width, height){
        var boardParameters = {
            seed : seed,
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
        this.map = new Map(response.responseJSON);
        this.map.init();
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
            console.log(game.map.fields[x][y].biome);
        });
        game.canvas.addEventListener("wheel", function(e){
            if (event.wheelDelta){
            	delta = event.wheelDelta;
            }else{
            	delta = -1 *event.deltaY;
            }
            if (delta < 0){
            	console.log("DOWN");
                game.map.zoomIn()
            }else if (delta > 0){
                console.log("UP");
                game.map.zoomOut();
            }
        });
    }
    
}

function Map(pattern){
    this.assets;
    this.fields = pattern.fields;
    this.maxSize = 50;
    this.minSize = 20;
    this.zoomStep = 5;
    this.fieldsWidth = this.maxSize;
    this.fieldsHeight = this.maxSize;
    this.width = pattern.width;
    this.height = pattern.height;
    
    this.init = function(){
        this.assets = this.getAssets();
        for(y = 0; y < this.height; y++){
            for(x = 0; x < this.width; x++){
                var terrainName = this.fields[x][y].terrain;
                var index = this.assets.findTerrainIndex(terrainName);
                this.fields[x][y].terrainAssetIndex = index;
                var structureName = this.fields[x][y].structure;
                index = this.assets.findStructureIndex(structureName);
                this.fields[x][y].structureAssetIndex = index;
            }
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
                var terrain = this.assets.terrains[field.terrainAssetIndex].img;//draw terrain
                ctx.drawImage(terrain, 0, 0, this.fieldsWidth, this.fieldsHeight);                       // draw image at current position
                if(field.structureAssetIndex >= 0){
                    var structure = this.assets.structures[field.structureAssetIndex].img;//draw terrain
                    ctx.drawImage(structure, 0, 0, this.fieldsWidth, this.fieldsHeight);                       // draw image at current position
                }
                ctx.restore();
                
            }
        }
    }
    this.getAssets = function(){
        var response = $.ajax({
            async: false,
            type: "GET",
            contentType:"application/json; charset=utf-8",
            dataType: "json",
            url: "/getAllAssets",
            success: function (response) {
                return response;
                console.log();
            },
            error : function(response) {
                console.log(response);
                return undefined;
            }
        });
        var assets = new Assets();
        assets.init(response.responseJSON);
        return assets;
    }
    
    this.zoomIn = function(){
        if(this.fieldsWidth > this.minSize && this.fieldsHeight > this.minSize){
            this.fieldsWidth -= this.zoomStep;
            this.fieldsHeight -= this.zoomStep;
        }
    }
    
    this.zoomOut = function(){
        if(this.fieldsWidth < this.maxSize && this.fieldsHeight < this.maxSize){
            this.fieldsWidth += this.zoomStep;
            this.fieldsHeight += this.zoomStep;
        }
    }
}

function Assets(){
    this.terrains = [];
    this.items = [];
    this.structures = [];
    this.enemies = [];
    this.locations = [];
    this.players = [];
    
    this.init = function(pattern){
        var assetsSet = this.getSet(pattern, "terrain");

        for(var i = 0; i < assetsSet.length; i++){
            var terrain = new Asset();
            terrain.name = assetsSet[i].name;
            terrain.img.src = assetsSet[i].path;
            this.terrains.push(terrain);
        };
        assetsSet = this.getSet(pattern, "structures");
        for(var i = 0; i < assetsSet.length; i++){
            var structure = new Asset();
            structure.name = assetsSet[i].name;
            structure.img.src = assetsSet[i].path;
            this.structures.push(structure);
        };
    }
    this.findTerrainIndex = function(name){
        var index = -1;
        for(var i = 0; i < this.terrains.length; i++){
            if(this.terrains[i].name.includes(name)){
                index = i;
            }
        }
        return index;
    };
    
    this.findStructureIndex = function(name){
        var index = -1;
        for(var i = 0; i < this.structures.length; i++){
            if(this.structures[i].name.includes(name)){
                index = i;
            }
        }
        return index;
    };
    
    this.getSet = function(pattern, name){
        for(var i = 0; i < pattern.assets.length; i++){
            if(pattern.assets[i].category.includes(name)){
                return pattern.assets[i].list;
            }
        }
    }
}

function Asset(){
    this.type;
    this.img = new Image();
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