/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function openInNewTab(url) {
  var win = window.open(url, '_blank');
  win.focus();
}

function getFromUrl(url){
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
    console.log(response.responseJSON);
}
