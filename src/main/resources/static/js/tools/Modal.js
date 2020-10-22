/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Setup:
 * 
 * 1. Add these at top of page
 * <script type="text/javascript" src="js/tools/Modal.js"></script>
 * <link rel="stylesheet" type="text/css" href="css/modal.css"/>
 * 
 * 2. Call Modal with parameters:
 * - div (html div)
 * - headText (String)
 * - body (html element for example table or other div)
 * - !IMPORTANT! onClick (function called when button at foot of div is clicked)
 */
function Modal(div, headText, body, onClick){
    this.modal = div;
    this.modal.setAttribute('class', "modal");
    this.modal.style.display = "none";
    
    this.clearModal = function(){
        while (this.modal.firstChild) {
            this.modal.removeChild(this.modal.firstChild);
        }
    }
    this.clearModal();
    
    var content = document.createElement('div');
    content.setAttribute('class', "modal-content");
    this.modal.appendChild(content);
    this.makeHeader = function(content, modal, text){
        var header = document.createElement('div');
        header.setAttribute('class', "modal-header");
        var span = document.createElement("span");
        span.setAttribute('class', "close");
        span.innerHTML = "&times;";
        // When the user clicks on <span> (x), close the modal
        span.onclick = function() {
          modal.style.display = "none";
        }
        header.appendChild(span);
        var headerText = document.createElement("h2");
        headerText.innerHTML = text;
        header.appendChild(headerText);
        content.appendChild(header);
    }
    this.makeHeader(content, this.modal, headText);
    
    this.makeBody = function(content, body){
        var bodyDiv = document.createElement('div');
        bodyDiv.setAttribute('class', "modal-body");
        bodyDiv.appendChild(body);
        content.appendChild(bodyDiv);
    }
    this.makeBody(content, body);
    
    this.makeFooter = function(content, onClick, modal){
        var footer = document.createElement('div');
        footer.setAttribute('class', 'modal-footer');
        var footButton = document.createElement("button");
        footButton.innerHTML = "Done";
        footButton.onclick = function(){
            onClick();
            modal.close();
        }
        footer.appendChild(footButton);
        content.appendChild(footer);
    }
    this.makeFooter(content, onClick, this);
    
    
    this.open = function(){
        this.modal.style.display = "block";
    }
    
    this.close = function(){
        this.modal.style.display = "none";
    }
}