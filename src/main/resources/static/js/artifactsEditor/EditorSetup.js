/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function EditorSetup(div){
    this.URL = new URLConnector();
    this.options = this.URL.getFromURL("artifacts/getOptions");
    this.assets = this.URL.getFromURL("asset/category=items");
    this.addButton;
    this.editButton;
    this.deleteButton;
    
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
    
    this.newDataTable = function(div){
        var tableHTML = this.newTable(['Name', 'Asset Name', 'Slot', 'Effect', 'Value', 'School']);
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
                    url: "/artifacts/getAll",
                    dataSrc: "",
                    mDataProp: ""
                },
                "columns": [
                { "data": "name" },
                { "data": "asset" },
                { "data": "slot" },
                { "data": "effect" },
                { "data": "value" },
                { "data": "school" }
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
    
    this.setupButtons = function(div){
        this.addButton = this.newButton("New");
        this.editButton = this.newButton("Edit");
        this.deleteButton = this.newButton("Delete");
        var br = document.createElement('br');
        div.appendChild(this.addButton);
        div.appendChild(this.editButton);
        div.appendChild(this.deleteButton);
        div.appendChild(br);
    }
    
    this.init = function(div){
        this.setupButtons(div);
        var dataTable = this.newDataTable(div);
        var modalDiv = document.createElement("div");
        div.appendChild(modalDiv);
        var assets = this.assets;
        var options = this.options;
        var url = this.URL;
        this.addButton.onclick = function(){
            var artifact = url.getFromURL("artifacts/getForm");
            var modalBody = new ArtifactModalBody(artifact, options, assets);
            var modal = new Modal(modalDiv, 'New Artifact', modalBody.getHTMLElement(), function(){
                url.sendRequestPOST("artifacts/add", artifact);
                dataTable.refresh();
            });
            modal.open();
        }
        this.editButton.onclick = function(){
            var artifact = dataTable.getSelected();
            if(artifact != undefined){
                var modalBody = new ArtifactModalBody(artifact, options, assets);
                var modal = new Modal(modalDiv, 'Edit Artifact', modalBody.getHTMLElement(), function(){
                    url.sendRequestPOST("artifacts/update", artifact);
                    dataTable.refresh();
                });
                modal.open();
            }
        }
        this.deleteButton.onclick = function(){
            var artifact = dataTable.getSelected();
            if(artifact != undefined){
                var id = artifact.id;
                url.sendRequestGET("/artifacts/remove=" + id);
                dataTable.refresh();
            }
        }
    }
    this.init(div);
}
