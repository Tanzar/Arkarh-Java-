/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function EditorSetup(unitsDiv, abilitiesDiv){
    this.URL = new URLConnector();
    this.options = this.URL.getFromURL("unitEditor/getOptions");
    this.unitsAssets = this.URL.getFromURL("/getUnitsAssets");
    this.abilitiesAssets = [];
    this.addUnitButton;
    this.editUnitButton;
    this.deleteUnitButton;
    this.unitsTable;
    this.abilitiesGroups = this.URL.getFromURL("unitEditor/getAbilitiesForms");
    this.addAbilityButton;
    this.editAbilityButton;
    this.deleteAbilityButton;
    this.abilitiesTable;
    this.abilitiesGroupsSelect;
    
    this.newColumn = function(text){
        var column = document.createElement("th");
        column.innerHTML = text;
        return column;
    }
    
    this.newTable = function(columns){
        var table = document.createElement("table");
        var thead = document.createElement("thead");
        var tr = document.createElement("tr");
        for(var i = 0; i < columns.length; i++){
            var column = columns[i];
            var th = this.newColumn(column);
            tr.appendChild(th);
        }
        thead.appendChild(tr);
        table.appendChild(thead);
        return table;
    }
    
    this.newButton = function(text){
        var button = document.createElement("button");
        button.innerHTML = text;
        return button;
    }
    
    this.newUnitsTable = function(div){
        var tableHTML = this.newTable(['Name', 'Asset Name', 'Fraction', 'Role', 'Tier']);
        div.appendChild(tableHTML);
        var table = $(tableHTML).DataTable( {
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
        table.onSelect = function(selected){
            console.log(selected);
        }
        table.on( 'click', 'tr', function () {
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            }
            else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
            var selected = table.getSelected();
            table.onSelect(selected);
        } );
        table.refresh = function(){
            this.ajax.reload();
        }
        table.getSelected = function(){
            var unit = this.rows('.selected').data()[0];
            return unit;
        }
        return table;
    }
    
    this.setupUnitButtons = function(div){
        this.addUnitButton = this.newButton("New");
        this.editUnitButton = this.newButton("Edit");
        this.deleteUnitButton = this.newButton("Delete");
        var br = document.createElement('br');
        div.appendChild(this.addUnitButton);
        div.appendChild(this.editUnitButton);
        div.appendChild(this.deleteUnitButton);
        div.appendChild(br);
    }
    
    this.initUnits = function(div){
        this.setupUnitButtons(div);
        var unitsTable = this.newUnitsTable(div);
        this.unitsTable = unitsTable;
        var modalDiv = document.createElement("div");
        div.appendChild(modalDiv);
        var assets = this.unitsAssets;
        var options = this.options;
        var url = this.URL;
        this.addUnitButton.onclick = function(){
            var unit = url.getFromURL("unitEditor/getUnitForm");
            var modalBody = new UnitModalBody(unit, options, assets);
            var modal = new Modal(modalDiv, 'New Unit', modalBody.getHTMLElement(), function(){
                url.sendRequestPOST("unitEditor/addUnit", unit);
                unitsTable.refresh();
            });
            modal.open();
        };
        this.editUnitButton.onclick = function(){
            var unit = unitsTable.getSelected();
            if(unit != undefined){
                var modalBody = new UnitModalBody(unit, options, assets);
                var modal = new Modal(modalDiv, 'Edit Unit', modalBody.getHTMLElement(), function(){
                    url.sendRequestPOST("unitEditor/updateUnit", unit);
                    unitsTable.refresh();
                });
                modal.open();
            }
        };
    }
    this.initUnits(unitsDiv);
    
    this.setupAbilityButtons = function(div){
        this.addAbilityButton = this.newButton("New");
        this.editAbilityButton = this.newButton("Edit");
        this.deleteAbilityButton = this.newButton("Delete");
        var br = document.createElement('br');
        div.appendChild(this.addAbilityButton);
        div.appendChild(this.editAbilityButton);
        div.appendChild(this.deleteAbilityButton);
        div.appendChild(br);
    }
    
    this.newAbilitiesTable = function(div, unitId){
        var tableHTML = this.newTable(['Name', 'Group', 'Asset', 'Charges']);
        div.appendChild(tableHTML);
        var table = new Object();
        table.unitId = unitId;
        table = $(tableHTML).DataTable( {
                "lengthChange": false,
                "info": false,
                "ordering": false,
                "select" : true,
                scrollResize: true,
                scrollY: 500,
                scrollCollapse: true,
                ajax: {
                    url: "/unitEditor/getUnitAbilities=" + table.unitId,
                    dataSrc: "",
                    mDataProp: ""
                },
                "columns": [
                { "data": "name" },
                { "data": "group" },
                { "data": "asset" },
                { "data": "charges" }
                ]
        } );
        table.unitId = unitId;
        table.on( 'click', 'tr', function () {
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            }
            else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
        } );
        table.getSelected = function(){
            var unit = this.rows('.selected').data()[0];
            return unit;
        }
        table.newData = function(){
            this.ajax.url("/unitEditor/getUnitAbilities=" + this.unitId).load();
        }
        table.refresh = function(){
            this.ajax.reload();
        }
        return table;
    }
    
    this.groupsSelect = function(){
        var select = document.createElement("select");
        var availableAbilitiesGroups = Object.keys(this.abilitiesGroups);
        for(var i = 0; i < availableAbilitiesGroups.length; i++){
            var option = document.createElement('option');
            option.value = availableAbilitiesGroups[i];
            var textNode = document.createTextNode(option.value);
            option.appendChild(textNode);
            select.appendChild(option);
        }
        this.abilitiesGroupsSelect = select;
        return select;
    }
    
    this.initUnitAbilities = function(div, unitsTable){
        var select = this.groupsSelect();
        div.appendChild(select);
        this.setupAbilityButtons(div);
        this.abilitiesTable = this.newAbilitiesTable(div, -1);
        var abilitiesTable = this.abilitiesTable;
        unitsTable.onSelect = function(selected){
            abilitiesTable.unitId = selected.id;
            abilitiesTable.newData();
            
        }
        var modalDiv = document.createElement("div");
        div.appendChild(modalDiv);
        var assets = this.unitsAssets;
        var options = this.options;
        var url = this.URL;
        var groupSelect = this.abilitiesGroupsSelect;
        this.addAbilityButton.onclick = function(){
            if(unitsTable.getSelected() != undefined){
                var group = groupSelect.options[groupSelect.selectedIndex].value;
                var forms = url.getFromURL("unitEditor/getAbilitiesForms");
                var form = forms[group];
                var modalBody = new AbilityModalBody(form, group, options, assets, url);
                var modal = new Modal(modalDiv, 'New Ability', modalBody.getHTMLElement(), function(){
                    form.unitId = abilitiesTable.unitId;
                    url.sendRequestPOST("unitEditor/addAbility", form);
                    abilitiesTable.refresh();
                    console.log(form);
                });
                modal.open();
            }
        }
        this.editAbilityButton.onclick = function(){
            var selected = abilitiesTable.getSelected();
            if(selected != undefined && selected.group != "attack"){
                var group = selected.group;
                var modalBody = new AbilityModalBody(selected, group, options, assets, url);
                var modal = new Modal(modalDiv, 'Edit Ability', modalBody.getHTMLElement(), function(){
                    url.sendRequestPOST("unitEditor/updateAbility", selected);
                    abilitiesTable.refresh();
                    console.log(selected);
                });
                modal.open();
            }
        }
        this.deleteAbilityButton.onclick = function(){
            var selected = abilitiesTable.getSelected();
            if(selected != undefined && selected.group != "attack"){
                var selected = abilitiesTable.getSelected();
                var id = selected.id;
                url.sendRequestGET("unitEditor/removeAbility=" + id);
                abilitiesTable.refresh();
            }
        }
        this.deleteUnitButton.onclick = function(){
            var unit = unitsTable.getSelected();
            if(unit != undefined){
                var id = unit.id;
                url.sendRequestGET("/unitEditor/removeUnit=" + id);
                unitsTable.refresh();
                abilitiesTable.refresh();
            }
        };
    }
    this.initUnitAbilities(abilitiesDiv, this.unitsTable);
}
