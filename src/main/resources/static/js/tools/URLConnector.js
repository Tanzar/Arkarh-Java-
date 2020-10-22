/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function URLConnector(){
    
    this.getFromURL = function(url){
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
    
    this.sendRequestPOST = function(url, data){
        var response = $.ajax({
            async: false,
            type: "POST",
            contentType:"application/json",
            data: JSON.stringify(data),
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
    
    this.sendRequestGET = function(url){
        $.ajax({
            async: false,
            type: "GET",
            url: url,
            success: function () {
                console.log("succ");
            },
            error : function(e) {
                console.log(e);
            }
        });
    }
}

