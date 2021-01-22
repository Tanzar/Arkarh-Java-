/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function ArtifactModalBody(artifact, options, assets){
    this.artifact = artifact;
    this.table = new Table();
    
    this.table.addTextRow("Basic");
    this.table.addTextInput("Unit name", 'name', this.artifact);
    this.table.addAssetsSelect(this.artifact, 'asset', assets);
    this.table.addSelect("Slot", options.slots, 'slot', this.artifact);
    this.table.addSelect("Effect", options.effects, 'effect', this.artifact);
    this.table.addNumberNoMax("Value", 'value', this.artifact, -20);
    this.table.addSelect("School", options.schools, 'school', this.artifact);
    
    this.getHTMLElement = function(){
        return this.table.getHtmlElement();
    }
}
