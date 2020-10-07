/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var unitsTables;

function UnitsTables(){
    this.unitsTable;
    this.abilitiesTable;
    
    this.init = function(unitsTableId, abilityTableId){
        this.unitsTable = this.newUnitTable(unitsTableId);
        this.abilitiesTable = this.newAbilitiesTable(abilityTableId);
        unitsTables = this;
    }
    
    this.newUnitTable = function(unitsTableId){
        var table = $('#' + unitsTableId).DataTable( {
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
                { "data": "name" },
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
                var unit = table.rows('.selected').data()[0];
                unitsTables.abilitiesTable.newData(unit.abilities.abilities);
            }
        } );
        table.refresh = function(){
            this.ajax.reload();
        }
        table.getSelected = function(){
            var unit = this.rows('.selected').data()[0];
            return unit;
        }
        table.deleteSelected = function(){
            var unit = this.getSelected();
                if(unit != undefined){
                    var id = unit.id;
                    $.ajax({
                        type: "GET",
                        url: "/unitEditor/removeUnit=" + id,
                        success: function () {
                            console.log("succ");
                            table.refresh();
                        },
                        error : function(e) {
                            console.log(e);
                            table.refresh();
                        }
                    });
                }
        }
        return table;
    }
    
    this.newAbilitiesTable = function(abilityTableId){
        var data = [];
        var table = $('#' + abilityTableId).DataTable( {
                "lengthChange": false,
                "info": false,
                "ordering": false,
                "select" : true,
                scrollResize: true,
                scrollY: 500,
                scrollCollapse: true,
                data: data,
                "columns": [
                { "data": "name" },
                { "data": "asset" },
                { "data": "group" },
                { "data": "charges" }
                ]
        } );
        table.on( 'click', 'tr', function () {
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            }
            else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
                var unit = table.rows('.selected').data()[0];
                console.log(unit);
            }
        } );
        table.getSelected = function(){
            var unit = this.rows('.selected').data()[0];
            return unit;
        }
        table.newData = function(data){
            this.clear();
            this.rows.add(data);
            this.draw();
        }
        return table;
    }
}







