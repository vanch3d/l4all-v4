/**
 * @fileoverview Tree Picker widget class derived from Zapatec Widget. 
 * @author Nicolas Van Labeke mailto:mbreese@fourspaces.com
 */

/**
* TreePicker object. Creates the element for selecting color
* Displays 216 predefined colors palette, three Sliders and three input fields
* for red, green and blue channels and input field for hex color value.
* @param config [object] - color picker config.
*
* Constructor recognizes the following properties of the config object
* \code
*	property name		| description
*------------------------------------------------------------------------------
*	button		| [string or object] Reference to DOM element.  Optional
*						| Click on this element will popup color picker.
*	inputValueField		| [string or object] Reference to DOM element. Optional
*						| If option is given - value will be written into this
*						| element.
*	color			| [string] Initial color, format: '#FFCCFF', Optional,
*						| default '#000000'
*	title 		| [string] Title of color picker. Optional, default 'Color Picker'
*	offset  	| [number] offset between button and picker. Optional,
*						| default 10 pixels
* \endcode
*/


/**
 * TreePicker class.
 *
 * @constructor
 * @extends Zapatec.Widget
 * @param {object} objArgs User configuration
 */
Zapatec.TreePicker = function(objArgs)
{
	if (arguments.length == 0) {objArgs = {};}
	Zapatec.TreePicker.SUPERconstructor.call(this, objArgs);
};

/**
 * Unique static id of the widget class. Gives ability for Zapatec#inherit to
 * determine and store path to this file correctly when it is included using
 * Zapatec#include. When this file is included using Zapatec#include or path
 * to this file is gotten using Zapatec#getPath, this value must be specified
 * as script id.
 * @private
 */
Zapatec.TreePicker.id = 'Zapatec.ColorPicker';

// Inherit Widget
Zapatec.inherit(Zapatec.TreePicker, Zapatec.Widget);

// Include Zapatec.Tree
Zapatec.include(Zapatec.zapatecPath+'../zptree/src/tree.js','Zapatec.Tree');

/**
 * Initializes object.
 *
 * @param {object} objArgs User configuration
 */
Zapatec.TreePicker.prototype.init = function(objArgs)
{
	var self = this;
	
	// Call init method of superclass
	Zapatec.TreePicker.SUPERclass.init.call(this, objArgs);
	
	// Generates HTML for TreePicker
	if (Zapatec.windowLoaded) 
	{
		this.create();
	} 
	else 
	{
		Zapatec.Utils.addEvent(window, "load", function()
			{
				self.create();
			});
	}
};

/**
 * Configures the widget. Gets called from init and reconfigure methods of
 * superclass.
 *
 * @private
 * @param {object} objArgs User configuration
 */
Zapatec.TreePicker.prototype.configure = function(objArgs) {
	this.defineConfigOption('source', null);
	this.defineConfigOption('button', null);
	this.defineConfigOption('handleButtonClick', true);
	this.defineConfigOption('inputValueField', null);
	this.defineConfigOption('inputTextField', null);
	this.defineConfigOption('itemID','0.0.0.0');
	this.defineConfigOption('title','Tree picker');
	this.defineConfigOption('offset',15);
	this.defineConfigOption('themePath', Zapatec.zapatecPath +
										 '../zpextra/themes/treepicker/');

	Zapatec.TreePicker.SUPERclass.configure.call(this, objArgs);

	// defines fields to protect configuration value from changes
	this.currentNode = this.config.itemID;
	this.currentText = "";
	this.searchResults = [];
	// Gets button and inputValueField if they are string ID values
	this.config.button = Zapatec.Widget.getElementById(this.config.button);
	this.config.inputValueField = Zapatec.Widget.getElementById(this.config.inputValueField);
	this.config.inputTextField = Zapatec.Widget.getElementById(this.config.inputTextField);
};


/**
 * Creates HTML for Color Picker
 * @private
 */
Zapatec.TreePicker.prototype.create = function() {
	var self = this;
	// is used to process document onClick
	this.isShown = false;

	this.container = Zapatec.Utils.createElement("div",null,true);
	document.body.insertBefore(this.container,document.body.firstChild);
	this.container.className = this.getClassName({
		prefix: "zpColorPicker", suffix: "Container"});
	this.container.onclick = function () {
		self.isShown = true;
	}

	// Header DIV
	this.header = Zapatec.Utils.createElement("div", this.container);
	this.header.className = "header";

	this.titleDiv = Zapatec.Utils.createElement("div", this.header);
	this.titleDiv.className = 'title';
	this.titleDiv.innerHTML = this.config.title;
	this.titleDiv.id = "ColorPicker"+this.id+"title";

	// make picker window draggable
	new Zapatec.Utils.Draggable(this.container,{
		handler: this.titleDiv,
		//dragCSS:'dragging', 
		onDragInit : function () {
			self.isShown = true;
		},
		onDragMove: function () {
			if (self.WCH) {
				self.WCH.style.left = self.container.style.left;
				self.WCH.style.top = self.container.style.top;
			}
		}
	});

	this.closeDiv = Zapatec.Utils.createElement("div", this.header);
	this.closeDiv.className = 'close';
	this.closeDiv.id = "ColorPicker"+this.id+"close";
	this.closeDiv.onclick = function (){
		self.hide();
	}


	// Slider containers
	var searchContainer = Zapatec.Utils.createElement("div", this.container, true);
	searchContainer.className = "searchContainer";
	
	searchContainer.appendChild(document.createTextNode('Search for :'));
	this.searchInput = Zapatec.Utils.createElement("input", searchContainer, true);
	this.searchInput.size = 3;
	this.searchInput.value = "";
	this.searchInput.className = "fields";
	//this.searchInput.id = "ColorPicker"+this.id+"inputGreenField";
	this.searchInput.onchange = function()
	{
		self.searchButton.disabled = true;
		if (!self.searchInput.value || self.searchInput.value== "")
			alert("You need to type something");
		else
		{	var val= self.searchInput.value;
			self.displayResult(
				self.myTree.findAll(function(node)
					{return node.data.label.match(eval("/"+val+"/i"))}
					));
		}
	};
	
	this.searchButton = Zapatec.Utils.createElement("button", searchContainer);
	this.searchButton.value = "Next >";
	this.searchButton.textContent = "Next >";
	this.searchButton.className = "buttons";
	this.searchButton.disabled = true;
	this.searchButton.onclick = function () 
	{
		self.searchNext();
	}

	
	var slidersContainer = Zapatec.Utils.createElement("div", this.container, true);
	slidersContainer.className = "slidersContainer";

	this.myTreeUL = Zapatec.Utils.createElement("ul", slidersContainer, true);
	this.myTreeUL.id="mytree";

	var statusContainer = Zapatec.Utils.createElement("div", this.container, true);
	statusContainer.className = "statusContainer";
	this.statusText = document.createTextNode('help!');
	statusContainer.appendChild(this.statusText);
	
  if (this.config.handleButtonClick) {
    // Show/hide picker event
    this.config.button.onclick = function(){
      self.isShown = true;
      self.show();
    };
  }

	// Hide picker on Esc key and on click at other document element
	Zapatec.Utils.addEvent(document, 'keypress', function(e) {
		if (!e) {
			e = window.event;
		}
		if (e.keyCode == 27) {
			self.hide()
		}
	});
	Zapatec.Utils.addEvent(document, 'click', function() {
		if (!self.isShown) {
			self.hide();
		}
		self.isShown = false;
	});

	// Creating the tree (must be here because sliders needs parent elements
	// to be already created)
	this.createTree();
}

/**
 * Creates sliders and attaches handlers to them
 * @private
 */
Zapatec.TreePicker.prototype.createTree = function() {
	var self = this;
	this.myTree = new Zapatec.Tree({
		tree: "mytree",
		expandOnLabelClick: true,
		expandOnLabelDblclick: false,
		selectOnLabelDblclick: false,
		selectOnSignClick: false,
		selectOnSignDblclick: false,
		highlightSelectedNode: true,
		createWholeDOM: true,
		compact: true,
		eventListeners: {
           'select': function() {self.onSelect(this.data); return false;},
		   'labelDblclick': function() {self.onDblClick(this.data); return false;},
		   'afterCreate': function(){self.onLoaded(this); return false;}
			}
	});
	this.myTree.rootNode.config.source = this.config.source;
	this.myTree.rootNode.config.sourceType="xml/url"; 
	this.myTree.rootNode.loadData();
	
/*	this.redSlider = new Zapatec.Slider({
		div : this.redSliderSliderCol,
		length : 255,
		start: Zapatec.TreePicker.convertHexToColorObject(this.currentNode).red,
		step: 1,
		orientation : 'H',
		onChange : function (pos) {
			self.inputRed.value = pos;
			self.previewColor();
		},
		newPosition: function(){
			self.isShown = true;
		}
	});

	this.greenSlider = new Zapatec.Slider({
		div : this.greenSliderSliderCol,
		length : 255,
		step: 1,
		start: Zapatec.TreePicker.convertHexToColorObject(this.currentNode).green,
		orientation : 'H',
		onChange : function (pos) {
			self.inputGreen.value = pos;
			self.previewColor();
		},
		newPosition: function(){
			self.isShown = true;
		}
	});

	this.blueSlider = new Zapatec.Slider({
		div : this.blueSliderSliderCol,
		length : 255,
		start: Zapatec.TreePicker.convertHexToColorObject(this.currentNode).blue,
		step: 1,
		orientation : 'H',
		onChange : function (pos) {
			self.inputBlue.value = pos;
			self.previewColor();
		},
		newPosition: function(){
			self.isShown = true;
		}
	});*/
}



/**
 * Calculates top position for picker container based on position of button.
 * @private
 * @type number
 * @return Top position in pixels
 */
Zapatec.TreePicker.prototype.calculateTopPos = function() {
	var elementOffset = Zapatec.Utils.getElementOffset(this.config.button);
	var top = elementOffset.top - this.container.clientHeight -
			  this.config.offset;
	if (top <= 0) {
		top = elementOffset.top + this.config.button.clientHeight +
			  this.config.offset+5;
	}
	return top;
}

/**
 * Calculates left position for picker container based on position of button.
 * @private
 * @type number
 * @return Left position in pixels
 */
Zapatec.TreePicker.prototype.calculateLeftPos = function() {
	var elementOffset = Zapatec.Utils.getElementOffset(this.config.button);
	var left = elementOffset.left/* + this.config.button.clientWidth +
			   this.config.offset*/;
	if ((left + this.container.clientWidth) >= document.body.clientWidth) {
//		left =
//		elementOffset.left - this.container.clientWidth - this.config.offset;
	}
	if (left <= 0) {
		left = elementOffset.left + this.config.button.clientWidth +
			   this.config.offset;
	}

	return left;
}


/**
 * Shows Color Picker
 */
Zapatec.TreePicker.prototype.show = function () {
	var self = this;
	this.container.style.visibility = 'visible';
	this.container.style.left = this.calculateLeftPos() + "px";
	this.container.style.top =  this.calculateTopPos() + "px";
	// Windowed controls hider
	this.WCH = Zapatec.Utils.createWCH();
	if (this.WCH) {
		this.WCH.style.zIndex = this.container.style.zIndex;
		Zapatec.Utils.setupWCH_el(this.WCH,this.container);
	}
  if (this.config.handleButtonClick) {
    // Attaches hide method to button
    this.config.button.onclick = function(){
      self.isShown = false;
      self.hide();
    };
  }
  this.highlightCell();
}


/**
 * Hides TreePicker
 */
Zapatec.TreePicker.prototype.hide = function () {
	var self = this;
	this.container.style.visibility = 'hidden';
	this.container.style.left = '-1000px';
	this.container.style.top = '-1000px';
	if (this.WCH){
		Zapatec.ScrollWithWindow.unregister(this.WCH);
		if (this.WCH.outerHTML) {
			this.WCH.outerHTML = "";
		} else {
			Zapatec.Utils.destroy(this.WCH);
		}
	}
//	Zapatec.Utils.hideWCH(this.WCH);
  if (this.config.handleButtonClick) {
    // Attaches show method to button
    this.config.button.onclick = function() {
      self.isShown = true;
      self.show();
    };
  }
}

/**
 * Returns current color from picker.
 * @type string
 * @return Current color, format '#FFFFFF'
 */

Zapatec.TreePicker.prototype.getColor = function () {
	return this.currentNode;
}

/**
 * Sets current color. Used to handle changes in Hex input field
 * Sets color channels values to sliders positions
 * @private
 * @param [string] Color, format '#FFFFFF' or 'FFFFFF'
 */
Zapatec.TreePicker.prototype.setColor = function (hexcolor) {
}

/**
 * Displays color preview based on RGB input fields value
 * Sets hex field value and current color
 * @private
 * @param [string] Color, format '#FFFFFF' or 'FFFFFF'
 */
Zapatec.TreePicker.prototype.previewColor = function () {
}

/**
 * Highlights palette cell if current color is in predefined colors collection
 * @private
 */
Zapatec.TreePicker.prototype.highlightCell = function () {
}

/**
 * Sends selected color value to inputValueField element
 * If inputValueField is an INPUT or TEXTAREA - current color is set as its value
 * If inputValueField is a SELECT - options that corresponds to current color is
 * selected (if present)
 * Otherwise - current color is put into element's innerHtml
 * @private
 */
Zapatec.TreePicker.prototype.sendValueToinputField = function () {
	if (this.config.inputValueField != null) {
		var tagName = this.config.inputValueField.tagName.toUpperCase();
		switch (tagName) {
			case "INPUT":
				this.config.inputValueField.value = this.currentNode;
				break;
			case "TEXTAREA":
				this.config.inputValueField.value = this.currentNode;
				break;
			default:
				this.config.inputValueField.innerHTML = this.currentNode;
				break;
		}
	}
	if (this.config.inputTextField != null) {
		var tagName = this.config.inputTextField.tagName.toUpperCase();
		switch (tagName) {
			case "INPUT":
				this.config.inputTextField.value = this.currentText;
				break;
			case "TEXTAREA":
				this.config.inputTextField.value = this.currentText;
				break;
			default:
				this.config.inputTextField.innerHTML = this.currentText;
				break;
		}
	}
}

/**
 * Fires select event
 * Sends current color value to inputValueField element
 * Hides color picker
 * @private
 */
Zapatec.TreePicker.prototype.select = function () {
	this.fireEvent('select', this.currentNode);
	this.hide();
}

Zapatec.TreePicker.prototype.displayResult = function(result)
{
		if(result == null || result.length == 0){
			alert("No item found");
			this.searchResults = [];
			if (this.searchButton!=null) this.searchButton.disabled=true;
		} else {
			this.searchResults = result;
			var tt = this.searchResults.shift();
			if (tt!=null)
				tt.sync();
			if (this.searchButton!=null) this.searchButton.disabled=false;
		}
}

Zapatec.TreePicker.prototype.searchNext = function()
{
		if(this.searchResults == null || this.searchResults.length == 0){
			alert("No item found");
			if (this.searchButton!=null) this.searchButton.disabled=true;
		} else {
			tt = this.searchResults.shift();
			if (tt!=null)
				tt.sync();
			if (this.searchButton!=null) this.searchButton.disabled=false;
		}
}

Zapatec.TreePicker.prototype.onLoaded = function(node)
{
	var id = node.data.attributes['id'];
	var txt = node.data.label;
	if (id == this.config.itemID)
	{
		node.data.isSelected = true;
		node.data.isExpanded = true;
		this.currentNode = id;
		this.currentText = txt;
		this.sendValueToinputField();
	}

}

Zapatec.TreePicker.prototype.onDblClick = function(node)
{
	var id = node.attributes['id'];
	var txt = node.label;
	this.currentNode = id;
	this.currentText = txt;
	this.sendValueToinputField();
	this.hide();
}
/**
 * Displays color preview based on RGB input fields value
 * Sets hex field value and current color
 * @private
 * @param [string] Color, format '#FFFFFF' or 'FFFFFF'
 */
Zapatec.TreePicker.prototype.onSelect = function (node)
{
	var id = node.attributes['id'];
	var txt = node.label;
	if (this && this.statusText) this.statusText.data = id + " - " + txt;
}