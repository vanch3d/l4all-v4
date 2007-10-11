// $Id: alert.js 6472 2007-02-27 09:39:16Z slip $
/**
 * Alert window constructor. Creates and shows the DHTML "alert".
 *
 * @param message [string] - message to be shown.
 * @param config [object] - config options for Window.
 */ 
Zapatec.AlertWindow = function(message, config) {
	//for backward compability we create this refference
	//to this window object
	this.win = this;
	//saving message
	this.message = message;
	//calling super constructor
	Zapatec.AlertWindow.SUPERconstructor.call(this, config);
	this.alert(message);
};

Zapatec.AlertWindow.id = "Zapatec.AlertWindow";

//Inheriting Zapatec.Window class
Zapatec.inherit(Zapatec.AlertWindow, Zapatec.Window);

/**
 * Inits the object with config.
 */
Zapatec.AlertWindow.prototype.init = function(config) {
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
	// processing Widget functionality
	Zapatec.AlertWindow.SUPERclass.init.call(this, config);
};

/**
 * Creates the content of alert window.
 * @return [HTML element] - content element.
 */
Zapatec.AlertWindow.prototype.createContent = function() {
	var self = this;
	var content = document.createElement("div");
	Zapatec.Utils.addClass(content, "zpWinAlertInnerContent");
	if (this.content && this.content.getContentElement) {
		Zapatec.Utils.addClass(this.content.getContentElement(), 'zpWinAlertContent');
	} else if (this.content) {
		Zapatec.Utils.addClass(this.content, 'zpWinAlertContent');
	} else {
		this.fireOnState("ready", function() {
			Zapatec.Utils.addClass(self.content.getContentElement(), 'zpWinAlertContent');
		});
	}
	this.messageArea = document.createElement("div");
	this.messageArea.className = "zpWinAlertMessage";
	content.appendChild(this.messageArea);
	this.okButton = new Zapatec.Button({
		theme : "default",
		clickAction : function () {
			self.close();
		},
		text : "Ok"
	});
	content.appendChild(this.okButton.getContainer());
	return content;
};

/**
 * This methods actually shows the alert.
 * It is called from constructor.
 * @param message [string] - message for alert.
 */
Zapatec.AlertWindow.prototype.alert = function(message) {
	if (!this.getContainer() || !this.getContainer().parentNode != document.body) {
		this.create(null, null, this.config.width, this.config.height);
	}
	this.setScreenPosition(this.config.left, this.config.top);
	this.setTitle(this.config.title);
	this.show();
	if (!this.messageArea) {
		this.setDivContent(this.createContent());
	}
	this.messageArea.innerHTML = message || this.message;
};
