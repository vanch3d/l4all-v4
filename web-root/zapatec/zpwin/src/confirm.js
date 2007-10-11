// $Id: confirm.js 6472 2007-02-27 09:39:16Z slip $
/**
 * Confirm window constructor. Creates and shows the DHTML "confirm".
 *
 * @param message [string] - message to be shown.
 * @param config [object] - config options for Window.
 */ 
Zapatec.ConfirmWindow = function(message, config) {
	//for backward compability we create this refference
	//to this window object
	this.win = this;
	//confirm action
	this.onConfirm = function() {};
	//saving message
	this.message = message;
	var self = this;
	//calling super constructor
	Zapatec.ConfirmWindow.SUPERconstructor.call(this, config);
	//adding close event listener
	this.addEventListener("onClose", function() {self.onConfirm(self.result);});
};

Zapatec.ConfirmWindow.id = "Zapatec.ConfirmWindow";

//Inheriting Zapatec.Window class
Zapatec.inherit(Zapatec.ConfirmWindow, Zapatec.Window);

/**
 * Inits the object with config.
 */
Zapatec.ConfirmWindow.prototype.init = function(config) {
	//width of the Window
	this.defineConfigOption("width", "auto");
	//height of the Window
	this.defineConfigOption("height", "auto");
	//left coordinate
	this.defineConfigOption("left", "center");
	//top coordinate
	this.defineConfigOption("top", "center");
	//title of the Window
	this.defineConfigOption("title", "");
	//disabling needed buttons
	config.showMaxButton = false;
	config.showMinButton = false;
	config.canResize = false;
	config.showStatus = false;
	//setting hideOnClose true as default
	config.hideOnClose = (config.hideOnClose === false) ? config.hideOnClose : true;
	// processing Widget functionality
	Zapatec.ConfirmWindow.SUPERclass.init.call(this, config);
};

/**
 * Creates the content of confirm window.
 * @return [HTML element] - content element.
 */
Zapatec.ConfirmWindow.prototype.createContent = function() {
	var self = this;
	var content = document.createElement("div");
	Zapatec.Utils.addClass(content, "zpWinConfirmInnerContent");
	if (this.content && this.content.getContentElement) {
		Zapatec.Utils.addClass(this.content.getContentElement(), 'zpWinConfirmContent');
	} else if (this.content) {
		Zapatec.Utils.addClass(this.content, 'zpWinConfirmContent');
	} else {
		this.fireOnState("ready", function() {
			Zapatec.Utils.addClass(self.content.getContentElement(), 'zpWinConfirmContent');
		});
	}
	this.messageArea = document.createElement("div");
	this.messageArea.className = "zpWinConfirmMessage";
	content.appendChild(this.messageArea);
	this.okButton = new Zapatec.Button({
		theme : "default",
		clickAction : function () {
			self.result = true;
			self.close();
		},
		text : "Ok"
	});
	content.appendChild(this.okButton.getContainer());
	content.appendChild(document.createTextNode(" "));
	this.cancelButton = new Zapatec.Button({
		theme : "default",
		clickAction : function () {
			self.result = false;
			self.close();
		},
		text : "Cancel"
	});
	content.appendChild(this.cancelButton.getContainer());
	return content;
};

/**
 * This methods actually gets the response,
 * which means show window and add onConfirm action.
 * onConfirm action is called onClose.
 * @param action [function] - custom confirm action.
 * @param message [string] - message for confirm, optional.
 */
Zapatec.ConfirmWindow.prototype.getResponse = function(action, message) {
	this.result = false;
	if (!this.getContainer() || !this.getContainer().parentNode != document.body) {
		this.create(null, null, this.config.width, this.config.height);
	}
	this.setScreenPosition(this.config.left, this.config.top);
	this.setTitle(this.config.title);
	if (typeof action == "function") {
		this.onConfirm = action;
	}
	this.show();
	if (!this.messageArea) {
		this.setDivContent(this.createContent());
	}
	this.messageArea.innerHTML = message || this.message;
};
