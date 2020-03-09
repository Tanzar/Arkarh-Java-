/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var editor;

function start(canvas, div){
    
}

function Editor(canvas, div){
    this.map = this.setupMap(20, 10);
    this.screen = new Screen(canvas, this.map);
    this.menu = new Menu(div, this.map);
    this.run = true;
    this.getCanvasContext = function(){
        return this.screen.getContext();
    }
    this.start = function(width, height){
        
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
    this.stop = function(){
        this.run = false;
    }
    this.frame = function(){
        
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
    for(x = 0; x < pattern.width; x++){
        for(y = 0; y < pattern.height; y++){
            this.fields[x][y].terrain.assetImg = new Image();
            this.fields[x][y].terrain.assetImg.src = this.fields[x][y].terrain.asset;
            this.fields[x][y].obstacle.assetImg = new Image();
            this.fields[x][y].obstacle.assetImg.src = this.fields[x][y].obstacle.asset;
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
                var terrain = field.terrain.assetImg;//draw terrain
                ctx.drawImage(terrain, 0, 0, this.fieldsWidth, this.fieldsHeight);                       // draw image at current position
                var obstacle = field.obstacle.assetImg;//draw terrain
                ctx.drawImage(obstacle, 0, 0, this.fieldsWidth, this.fieldsHeight);                       // draw image at current position
                if(this.marker.x == x && this.marker.y == y){
                    ctx.drawImage(this.marker.asset, 0, 0, this.fieldsWidth, this.fieldsHeight);
                }
                ctx.restore();
                
            }
        }
    }
    this.changeMarkedGround = function(item){
        var x = this.marker.x;
        var y = this.marker.y;
        this.fields[x][y].terrain.name = item.name;
        this.fields[x][y].terrain.asset = item.asset;
        this.fields[x][y].terrain.assetImg.src = item.asset;
    }
    this.changeMarkedLocation = function(item){
        var x = this.marker.x;
        var y = this.marker.y;
        this.fields[x][y].obstacle.name = item.name;
        this.fields[x][y].obstacle.asset = item.asset;
        this.fields[x][y].obstacle.assetImg.src = item.asset;
    }
    this.markField = function(x, y){
        this.marker.x = x;
        this.marker.y = y;
    }
}

function Screen(canvas, map){
    this.canvas = canvas;
    this.map = map;
    this.getContext = function(){
        return this.canvas.getContext("2d");
    }
}

function Menu(div, map){
    
}

function Controls(screen, menu){
    
}

function newFrame(){
}