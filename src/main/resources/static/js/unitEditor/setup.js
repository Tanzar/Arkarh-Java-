/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var unitsTable;
var parametersTable;

function setup(unitsTableId){
    setupUnits(unitsTableId);
}

function setupUnits(tableId){
        $(document).ready(function() {
                var table = $('#' + tableId).DataTable( {
                        "lengthChange": false,
                        "info": false,
                        "ordering": false,
                        "select" : true,
                        scrollResize: true,
                        scrollY: 500,
                        scrollCollapse: true,
                        ajax: {
                            url: "/unitEditor/getAllUnits",
                            dataSrc: "",
                            mDataProp: ""
                        },
                        "columns": [
                            { "data": "unitName" },
                            { "data": "assetName" },
                            { "data": "fraction" },
                            { "data": "role" },
                            { "data": "tier" }
                        ]
                    } );
                table.on( 'click', 'tr', function () {
                    if ( $(this).hasClass('selected') ) {
                        $(this).removeClass('selected');
                    }
                    else {
                        table.$('tr.selected').removeClass('selected');
                        $(this).addClass('selected');
                        var unit = unitsTable.rows('.selected').data()[0];
                        setUnitParameters(unit);
                    }
                } );
                unitsTable = table;
            } );
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
    return response.responseJSON;
}

function addOptionText(select, text, value){
    var option = document.createElement('option');
    var textNode = document.createTextNode(text);
    option.appendChild(textNode);
    option.value = value;
    select.appendChild(option);
}


function getValueFromTable(table, row){
    var input = table.rows[row].cells[1].children[0];
    return input.value;
}

function setValueInTable(table, row, value){
    var input = table.rows[row].cells[1].children[0];
    input.value = value;
    if(input.tagName == 'SELECT'){
        input.dispatchEvent(new Event('change'));
    }
}

function refreshUnitsTable(){
    unitsTable.ajax.reload();
}




