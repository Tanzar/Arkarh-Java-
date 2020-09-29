/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var unitsTable;
var parametersTable;

function setup(unitsTableId, fractionsId, assetsId, rolesId, tiersId, categoriesId, effectsId, attacksId, parametersTableId){
    setupUnits(unitsTableId);
    setupSelects(fractionsId, assetsId, rolesId, tiersId, categoriesId, effectsId, attacksId);
    parametersTable = document.getElementById(parametersTableId);
    var tes = getFromUrl("/unitEditor/getOptions");
    console.log(tes);
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

function setupSelects(fractionsId, assetsId, rolesId, tiersId, categoriesId, effectsId, attacksId){
    var fractionSelect = document.getElementById(fractionsId);
    setupFractions(fractionSelect);
    var assetSelect = document.getElementById(assetsId);
    setupUnitAssets(assetSelect);
    var roleSelect = document.getElementById(rolesId);
    setupRoles(roleSelect);
    var tierSelect = document.getElementById(tiersId);
    setupTiers(tierSelect);
    var categorySelect = document.getElementById(categoriesId);
    setupUnitCategories(categorySelect);
    var effectSelect = document.getElementById(effectsId);
    setupEffectTypes(effectSelect);
    var attackSelect = document.getElementById(attacksId);
    setupAttackTypes(attackSelect);
}

function setupFractions(fractionSelect){
    var fractions = getFromUrl("/unitEditor/getFractions");
    for(var i = 0; i < fractions.length; i++){
        addOptionText(fractionSelect, fractions[i], fractions[i]);
    }
}

function setupUnitAssets(unitsSelect){
    var units = getFromUrl("/getUnitsAssets");
    for(var i = 0; i < units.length; i++){
        addOptionText(unitsSelect, units[i].path, units[i].name);
    }
    unitsSelect.onchange = function(){
        var path = this.options[this.selectedIndex].text;
        var preview = document.getElementById('asset_preview');
        preview.src = path;
    }
}

function setupRoles(rolesSelect){
    var roles = getFromUrl("/unitEditor/getRoles");
    for(var i = 0; i < roles.length; i++){
        addOptionText(rolesSelect, roles[i], roles[i]);
    }
}

function setupTiers(tierSelect){
    var tiers = getFromUrl("/unitEditor/getTiers");
    for(var i = 0; i < tiers.length; i++){
        addOptionText(tierSelect, tiers[i], tiers[i]);
    }
}

function setupUnitCategories(categorySelect){
    var categories = getFromUrl("/unitEditor/getUnitsCategories");
    for(var i = 0; i < categories.length; i++){
        addOptionText(categorySelect, categories[i], categories[i]);
    }
}

function setupEffectTypes(effectSelect){
    var effects = getFromUrl("/unitEditor/getEffectTypes");
    for(var i = 0; i < effects.length; i++){
        addOptionText(effectSelect, effects[i], effects[i]);
    }
}

function setupAttackTypes(attackSelect){
    var attacks = getFromUrl("/unitEditor/getAttackTypes");
    for(var i = 0; i < attacks.length; i++){
        addOptionText(attackSelect, attacks[i], attacks[i]);
    }
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


function setupButtons(saveButton, updateButton, deleteButton){
    saveButton.onclick = function(){addUnit();};
    updateButton.onclick = function(){updateUnit();};
    deleteButton.onclick = function(){deleteUnit();};
}

function getUnit(table){
    var unit = getFromUrl('/getUnitForm');
    unit.name = getValueFromTable(table, 1);
    unit.assetName = getValueFromTable(table, 2);
    unit.fraction = getValueFromTable(table, 3);
    unit.role = getValueFromTable(table, 4);
    unit.tier = getValueFromTable(table, 5);
    unit.category = getValueFromTable(table, 6);
    unit.attack = getValueFromTable(table, 7);
    unit.spellpower = getValueFromTable(table, 8);
    unit.effectType = getValueFromTable(table, 9);
    unit.damage = getValueFromTable(table, 10);
    unit.baseHealingValue = getValueFromTable(table, 11);
    unit.attackType = getValueFromTable(table, 12);
    unit.defense = getValueFromTable(table, 13);
    unit.armor = getValueFromTable(table, 14);
    unit.ward = getValueFromTable(table, 15);
    unit.baseHealth = getValueFromTable(table, 16);
    unit.health = unit.baseHealth;
    unit.upkeep = getValueFromTable(table, 17);
    unit.speed = getValueFromTable(table, 18);
    unit.range = getValueFromTable(table, 19);
    unit.baseMorale = getValueFromTable(table, 20);
    unit.morale = unit.baseMorale;
    unit.position = getValueFromTable(table, 21);
    unit.moraleLoss = 10;
    return unit;
}

function getValueFromTable(table, row){
    var input = table.rows[row].cells[1].children[0];
    return input.value;
}

function setUnitParameters(unit){
    setValueInTable(parametersTable, 1, unit.name);
    setValueInTable(parametersTable, 2, unit.assetName);
    setValueInTable(parametersTable, 3, unit.fraction);
    setValueInTable(parametersTable, 4, unit.role);
    setValueInTable(parametersTable, 5, unit.tier);
    setValueInTable(parametersTable, 6, unit.category);
    setValueInTable(parametersTable, 7, unit.attack);
    setValueInTable(parametersTable, 8, unit.spellPower);
    setValueInTable(parametersTable, 9, unit.effectType);
    setValueInTable(parametersTable, 10, unit.damage);
    setValueInTable(parametersTable, 11, unit.baseHealingValue);
    setValueInTable(parametersTable, 12, unit.attackType);
    setValueInTable(parametersTable, 13, unit.defense);
    setValueInTable(parametersTable, 14, unit.armor);
    setValueInTable(parametersTable, 15, unit.ward);
    setValueInTable(parametersTable, 16, unit.baseHealth);
    setValueInTable(parametersTable, 17, unit.upkeep);
    setValueInTable(parametersTable, 18, unit.speed);
    setValueInTable(parametersTable, 19, unit.range);
    setValueInTable(parametersTable, 20, unit.baseMorale);
    setValueInTable(parametersTable, 21, unit.position);
}

function setValueInTable(table, row, value){
    var input = table.rows[row].cells[1].children[0];
    input.value = value;
    if(input.tagName == 'SELECT'){
        input.dispatchEvent(new Event('change'));
    }
}

function addUnit(){
    var unit = getUnit(parametersTable);
    $.ajax({
        type: "POST",
        contentType:"application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(unit),
        url: "/unitEditor/addUnit",
        success: function (response) {
            console.log(response);
            refreshUnitsTable();
            setValueInTable(parametersTable, 21, 0);
        },
        error : function(response) {
            alert(response.responseText);
            console.log(response);
            refreshUnitsTable();
            setValueInTable(parametersTable, 21, 0);
        }
    });
}

function updateUnit(){
    var unit = getUnit(parametersTable);
    $.ajax({
        type: "POST",
        contentType:"application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(unit),
        url: "/unitEditor/updateUnit",
        success: function (response) {
            console.log(response);
            refreshUnitsTable();
            setValueInTable(parametersTable, 21, 0);
        },
        error : function(response) {
            alert(response.responseText);
            console.log(response);
            refreshUnitsTable();
            setValueInTable(parametersTable, 21, 0);
        }
    });
}

function deleteUnit(){
    var unit = unitsTable.rows('.selected').data()[0];
    $.ajax({
        type: "POST",
        contentType:"application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(unit),
        url: "/unitEditor/deleteUnit",
        success: function (response) {
            console.log(response);
            refreshUnitsTable();
            setValueInTable(parametersTable, 21, 0);
        },
        error : function(response) {
            alert(response.responseText);
            console.log(response);
            refreshUnitsTable();
            setValueInTable(parametersTable, 21, 0);
        }
    });
}

function refreshUnitsTable(){
    unitsTable.ajax.reload();
}




