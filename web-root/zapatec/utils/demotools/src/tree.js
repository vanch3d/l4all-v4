/*
 *
 * Copyright (c) 2004-2005 by Zapatec, Inc.
 * http://www.zapatec.com
 * 1700 MLK Way, Berkeley, California,
 * 94709, U.S.A.
 * All rights reserved.
 *
 *
 */
Zapatec.treePath = Zapatec.getPath("Zapatec.Tree");

Zapatec.Tree=function(){var objArgs={};switch(arguments.length){case 1:objArgs=arguments[0];break;case 2:objArgs=arguments[1];objArgs.tree=arguments[0];break;}
Zapatec.Tree.SUPERconstructor.call(this,objArgs);};Zapatec.Tree.id="Zapatec.Tree"
Zapatec.Tree.all={};Zapatec.inherit(Zapatec.Tree,Zapatec.Widget);Zapatec.Tree.prototype.init=function(config){this.container=null;this.internalContainer=null;this.allNodes=[];this.id2Obj={};this.rootNode=null;this.expandToLevelNum=0;this.isClicked=false;this.isActive=false;this.editInline=null;if(config.tree){var tmp=Zapatec.Widget.getElementById(config.tree);if(tmp&&tmp.id){this.id=tmp.id;}}
Zapatec.Tree.SUPERclass.init.call(this,config);Zapatec.Tree.all[this.id]=this;this.container=Zapatec.Utils.createElement("div",null,this.config.selectable);this.container.className=this.getClassName({prefix:"zpTree",suffix:"Container"});this.container.id="zpTree"+this.id+"Container";this.internalContainer=Zapatec.Utils.createElement("div",null,this.config.selectable);this.internalContainer.className="tree tree-top";this.container.appendChild(this.internalContainer);Zapatec.Utils.createProperty(this.container,"zpTree",this);if(this.config.initLevel){this.expandToLevelNum=this.config.initLevel;}
if(this.config.tree){this.config.tree.parentNode.insertBefore(this.container,this.config.tree);Zapatec.Utils.destroy(this.config.tree)}else if(this.config.parent){this.config.parent.appendChild(this.container);}
this.rootNode=new Zapatec.Tree.Node({tree:this,parentNode:null,level:0,isRootNode:true,eventListeners:this.config.eventListeners});this.id2Obj[this.id]=this.rootNode;this.rootNode.isCreated=true;this.rootNode.childsContainer=this.internalContainer;this.prevSelected=null;this.loadData();if(this.prevSelected){this.sync(this.prevSelected.id);}
if(this.config.saveState){var txt=Zapatec.Utils.getCookie("Zapatec.Tree-"+this.config.saveId);if(txt){this.sync(txt);}}
if(this.config.editable){if(Zapatec.EditInline){var self=this;this.editInline=new Zapatec.EditInline({editAsText:this.config.editAsText,eventListeners:{showStart:function(){this.selectedNode=self.prevSelected;},saveContent:function(content){if(this.selectedNode){this.selectedNode.rename(content);}}}});}else{this.config.editable=false;Zapatec.Log({description:"Zapatec.EditInline class is not found. Please include 'utils/edit_inline.js' into page."});}}
this.attachNavigation();}
Zapatec.Tree.prototype.configure=function(objArgs){this.defineConfigOption('tree');this.defineConfigOption('parent');this.defineConfigOption('hiliteSelectedNode');this.defineConfigOption('highlightSelectedNode',true);this.defineConfigOption('defaultIcons');this.defineConfigOption('compact',false);this.defineConfigOption('dynamic',false);this.defineConfigOption('initLevel',1);this.defineConfigOption('deselectSelected',false);this.defineConfigOption('saveState',false);this.defineConfigOption('saveId');this.defineConfigOption('expandOnSignClick',true);this.defineConfigOption('expandOnSignDblclick',false);this.defineConfigOption('expandOnIconClick',true);this.defineConfigOption('expandOnIconDblclick',false);this.defineConfigOption('expandOnLabel');this.defineConfigOption('expandOnLabelClick',false);this.defineConfigOption('labelDblClick');this.defineConfigOption('expandOnLabelDblclick',false);this.defineConfigOption('selectMultiple',false);this.defineConfigOption('selectOnSignClick',true);this.defineConfigOption('selectOnSignDblclick',true);this.defineConfigOption('selectOnIconClick',true);this.defineConfigOption('selectOnIconDblclick',true);this.defineConfigOption('selectOnLabelClick',true);this.defineConfigOption('selectOnLabelDblclick',true);this.defineConfigOption('prevCompatible',false);this.defineConfigOption('quick',false);this.defineConfigOption('putBackReferences',false);this.defineConfigOption('createWholeDOM',false);this.defineConfigOption('jsonLoadCallback');this.defineConfigOption('keyboardNavigation',false);this.defineConfigOption('deselectOnLeave');this.defineConfigOption('selectable',false);this.defineConfigOption('editable',false);this.defineConfigOption('editAsText',true);this.defineConfigOption('editOnClick',false);this.defineConfigOption('editOnDblclick',true);Zapatec.Tree.SUPERclass.configure.call(this,objArgs);this.config.parent=Zapatec.Widget.getElementById(this.config.parent);this.config.tree=Zapatec.Widget.getElementById(this.config.tree);if(this.config.tree!=null){if(this.config.source==null){this.config.source=this.config.tree;}else{}}
if(typeof(this.config.hiliteSelectedNode)!='undefined'&&this.config.hiliteSelectedNode!=null){this.config.highlightSelectedNode=this.config.hiliteSelectedNode;}
if(this.config.parent==null&&this.config.tree==null){Zapatec.Log({description:"No 'parent' or 'tree' config options given. Unable to add tree."});throw("No 'parent' or 'tree' config options given. Unable to add tree.");}
if(this.config.labelDblClick!=null){this.config.expandOnLabelDblclick=this.config.labelDblClick;}
if(this.config.expandOnLabel!=null){this.config.expandOnLabelClick=this.config.expandOnLabel;}
if(this.config.initLevel==false){this.config.initLevel=1;}
if(this.config.initLevel<1){this.config.initLevel=1;}
if(this.config.saveState&&(!this.config.saveId||typeof(this.config.saveId)!='string'||this.config.saveId.length==0)){Zapatec.Log({description:"No 'saveId' is given. 'saveState' feature disabled."});this.config.saveState=false;}
if(this.config.createWholeDOM&&this.config.quick){this.config.quick=false;Zapatec.Log({description:"Config option 'createWholeDOM' overrides 'quick' config option"});}
if(this.config.selectMultiple){this.config.keyboardNavigation=false;}
if(this.config.keyboardNavigation&&typeof(this.config.deselectOnLeave)=='undefined'){this.config.deselectOnLeave=true;}}
Zapatec.Tree.prototype.reconfigure=function(objArgs){if(objArgs.theme){Zapatec.Utils.removeClass(this.container,this.getClassName({prefix:"zpTree",suffix:"Container"}));}
Zapatec.Tree.SUPERclass.reconfigure.call(this,objArgs);Zapatec.Utils.addClass(this.container,this.getClassName({prefix:"zpTree",suffix:"Container"}));}
Zapatec.Tree.prototype.find=function(findFunc){for(var ii=0;ii<this.allNodes.length;ii++){if(findFunc(this.allNodes[ii])){return this.allNodes[ii];}}}
Zapatec.Tree.prototype.findAll=function(findFunc){var result=[];for(var ii=0;ii<this.allNodes.length;ii++){if(findFunc(this.allNodes[ii])){result.push(this.allNodes[ii]);}}
return result;}
Zapatec.Tree.prototype.toggleAll=function(){for(var ii=0;ii<this.allNodes.length;ii++){this.allNodes[ii].toggle();}};Zapatec.Tree.prototype.getNode=function(id){var node=this.id2Obj[id];if(node==null){Zapatec.Log({description:"No node found for id '"+id+"'"});return;}
return node;}
Zapatec.Tree.prototype.sync=function(itemId){var node=this.getNode(itemId);if(node){node.sync();}}
Zapatec.Tree.prototype.toggleItem=function(nodeId,state){var node=this.getNode(nodeId);if(!node){return;}
if(state==true){node.expand();}else if(state==false){node.collapse();}else if(state==null){node.toggle();}}
Zapatec.Tree.prototype.appendChild=function(newChild,parent,atStart){if(this.config.prevCompatible){var tmp=parent;parent=newChild;newChild=tmp;}
if(parent==null){parent=this.rootNode;}else{parent=this.getNode(parent);}
if(parent==null){return null;}
return parent.appendChild(newChild,atStart);}
Zapatec.Tree.prototype.insertBefore=function(newChild,refChild){refChild=this.getNode(refChild);if(refChild==null){return null;}
return refChild.insertBefore(newChild);}
Zapatec.Tree.prototype.insertAfter=function(newChild,refChild){refChild=this.getNode(refChild);if(refChild==null){return null;}
return refChild.insertAfter(newChild);}
Zapatec.Tree.prototype.removeChild=function(oldChild){oldChild=this.getNode(oldChild);if(oldChild==null){return null;}
oldChild.destroy();}
Zapatec.Tree.prototype.collapseAll=function(){for(var ii=0;ii<this.allNodes.length;ii++){this.allNodes[ii].collapse()}};Zapatec.Tree.prototype.collapseToLevel=function(level){for(var ii=0;ii<this.allNodes.length;ii++){if(this.allNodes[ii].config.level>level){this.allNodes[ii].collapse();}}};Zapatec.Tree.prototype.expandAll=function(){for(var ii=0;ii<this.allNodes.length;ii++){this.allNodes[ii].expand();}};Zapatec.Tree.prototype.expandToLevel=function(level){this.expandToLevelNum=level;for(var ii=0;ii<this.allNodes.length;ii++){if(this.allNodes[ii].config.level<=level){this.allNodes[ii].expand();}}};Zapatec.Tree.prototype.loadDataJson=function(objResponse){if(objResponse==null){return null;}
if(this.config.jsonLoadCallback){objResponse=this.config.jsonLoadCallback(objResponse);}
this.rootNode.data={};this.rootNode.data.childs=objResponse;this.rootNode.createChilds();for(var ii=0;ii<this.rootNode.childs.length;ii++){this.rootNode.childs[ii].afterCreate();}}
Zapatec.Tree.prototype.loadDataXml=function(objSource){if(objSource==null||objSource.documentElement==null){return null;}
var result={};for(var jj=0;jj<objSource.documentElement.childNodes.length;jj++){var tmp=Zapatec.Tree.Utils.convertXml2Json(objSource.documentElement.childNodes[jj]);if(tmp!=null){result.push(tmp);}}
return this.loadDataJson(result);}
Zapatec.Tree.prototype.loadDataHtml=function(objSource){if(objSource==null){return null;}
var result=[];for(var jj=0;jj<objSource.childNodes.length;jj++){var tmp=Zapatec.Tree.Utils.convertLi2Json(objSource.childNodes[jj],this.config.prevCompatible);if(tmp!=null){result.push(tmp);}}
return this.loadDataJson(result);}
Zapatec.Tree.prototype.makeNode=function(html,type){if(!type){type="li";}
var node=Zapatec.Utils.createElement(type);if(html){node.innerHTML=html;}
return node;}
Zapatec.Tree.prototype.destroy=function(leaveDOM){this.rootNode.destroy(true);this.container.zpTree=null;Zapatec.Tree.all[this.id]=null;this.allNodes=null;this.rootNode=null;if(!leaveDOM){Zapatec.Utils.destroy(this.container);}
this.container=null;this.id2Obj=null;this.discard();}
Zapatec.Tree.prototype.onItemSelect=function(){};Zapatec.Tree.prototype.getState=function(){var result=[];for(var ii=0;ii<this.rootNode.childs.length;ii++){result.push(this.rootNode.childs[ii].getState());}
return result;}
Zapatec.Tree.prototype.getParent=function(id,mode){return id;}
Zapatec.Tree.prototype.attachNavigation=function(){var self=this;Zapatec.Utils.addEvent(this.container,"click",function(){self.isActive=true;self.isClicked=true;});Zapatec.Utils.addEvent(document,(Zapatec.is_ie?"keydown":"keypress"),function(evt){return self.keyEvent(evt)});Zapatec.Utils.addEvent(document,'click',function(){if(!self.isClicked){self.leave();}
self.isClicked=false;});}
Zapatec.Tree.prototype.keyEvent=function(evt){if(!this.prevSelected||!this.isActive){return true;}
if(!evt){evt=window.event;}
var res=Zapatec.Utils.getCharFromEvent(evt);if(!this.config.keyboardNavigation){return;}
this.fireEvent("keypressed",res.charCode,res.chr);if(res.charCode==27){this.leave();}
switch(res.charCode){case 13:case 32:this.prevSelected.toggle();break;case 63234:case 37:if(this.prevSelected.data.isExpanded){this.prevSelected.collapse();}else{if(!this.prevSelected.config.parentNode.isRootNode){this.prevSelected.config.parentNode.select();}}
break;case 63235:case 39:if(!this.prevSelected.data.isExpanded){this.prevSelected.expand();}else{if(this.prevSelected.childs!=null&&this.prevSelected.childs.length>0){this.prevSelected.childs[0].select();}}
break;case 63232:case 38:var index=Zapatec.Tree.Utils.getNodeIndex(this.prevSelected);if(index==null){return;}
var prevNode=null;if(index>0){prevNode=this.prevSelected.config.parentNode.childs[index-1];while(prevNode.hasSubtree()&&prevNode.data.isExpanded){prevNode=prevNode.childs[prevNode.childs.length-1];}}else if(!this.prevSelected.config.parentNode.config.isRootNode){prevNode=this.prevSelected.config.parentNode;}
if(prevNode){prevNode.select();Zapatec.Utils.stopEvent(evt);}
break;case 63233:case 40:var index=Zapatec.Tree.Utils.getNodeIndex(this.prevSelected);if(index==null){return;}
var nextNode=null;if(this.prevSelected.hasSubtree()&&this.prevSelected.data.isExpanded&&this.prevSelected.childs.length>0){nextNode=this.prevSelected.childs[0];}else if(index<this.prevSelected.config.parentNode.childs.length-1){nextNode=this.prevSelected.config.parentNode.childs[index+1];}else if(!this.prevSelected.config.parentNode.config.isRootNode){nextNode=this.prevSelected.config.parentNode;index=Zapatec.Tree.Utils.getNodeIndex(nextNode);while(index==nextNode.config.parentNode.childs.length-1){nextNode=nextNode.config.parentNode;if(nextNode.config.isRootNode){return;}
index=Zapatec.Tree.Utils.getNodeIndex(nextNode);}
nextNode=nextNode.config.parentNode.childs[index+1];}
if(nextNode){nextNode.select();Zapatec.Utils.stopEvent(evt);}
break;}}
Zapatec.Tree.prototype.leave=function(){if(this.prevSelected&&this.config.deselectOnLeave){this.prevSelected.deselect();}
this.isActive=false;this.fireEvent("leave");}
Zapatec.Tree.Node=function(objArgs){Zapatec.Tree.Node.SUPERconstructor.call(this,objArgs);}
Zapatec.Tree.Node.id="Zapatec.Tree.Node";Zapatec.inherit(Zapatec.Tree.Node,Zapatec.Widget);Zapatec.Tree.Node.prototype.configure=function(objArgs){this.defineConfigOption('theme',null);this.defineConfigOption('tree',null);this.defineConfigOption('parentNode',null);this.defineConfigOption('level');this.defineConfigOption('isRootNode',false);Zapatec.Tree.Node.SUPERclass.configure.call(this,objArgs);if(this.config.tree==null){Zapatec.Log({description:"No reference to parent Zapatec.Tree instance! Aborting."});throw("No reference to parent Zapatec.Tree instance! Aborting.");}}
Zapatec.Tree.Node.prototype.init=function(config){this.expandedIcon=null;this.collapsedIcon=null;this.fetchingIcon=null;this.elementIcon=null;this.isCreated=false;this.isChildsCreated=false;this.isFetching=false;this.data=null;this.childs=[];this.labelContainer=null;this.signElement=null;this.childsContainer=null;this.expandedIcon=null;this.collapsedIcon=null;this.oldSource=null;this.oldSourceType=null;Zapatec.Tree.Node.SUPERclass.init.call(this,config);if(!this.config.isRootNode){this.config.tree.allNodes.push(this);}
this.loadData();this.config.source=null;this.config.sourceType=null;if(!this.config.isRootNode){if(this.data.attributes&&this.data.attributes['class']){var md=null;if(md=this.data.attributes['class'].match(/zpLoad(JSON|HTML|XML)=([^ $]*)/)){this.data.source=md[2];if(md[1]=="JSON"){this.data.sourceType="json/url";}else if(md[1]=="HTML"){this.data.sourceType="html/url";}else if(md[1]=="XML"){this.data.sourceType="xml/url";}else{this.data.source=null;this.data.sourceType=null;}}}
if(this.data.source){if(this.data.childs==null){this.data.childs=[];}
this.config.source=this.data.source;this.config.sourceType=this.data.sourceType;}}}
Zapatec.Tree.Node.prototype.addStandardEventListeners=function(){Zapatec.Tree.Node.SUPERclass.addStandardEventListeners.call(this);this.addEventListener('fetchSourceStart',function(){this.isFetching=true;this.putIcons();});this.addEventListener("fetchSourceEnd",function(){this.isFetching=false;this.putIcons();});this.addEventListener('loadDataEnd',function(){this.oldSourceType=this.config.source;this.oldSourceType=this.config.sourceType;this.config.source=null;this.config.sourceType=null;if(!this.config.isRootNode&&this.data.isExpanded){this.expand();}});this.addEventListener("fetchSourceError",function(objError){this.config.source=this.oldSourceType;this.config.sourceType=this.oldSourceType;Zapatec.Log({description:"Error happend while retrieving branch content: "+objError.errorCode+" "+objError.errorDescription});});}
Zapatec.Tree.Node.prototype.create=function(){if(this.isCreated||this.data==null||this.config.isRootNode){return null;}
this.fireEvent("beforeCreate");var content=[];content.push("<div class='tree-item");content.push(this.hasSubtree()?" tree-item-more ":"");content.push("'");this.labelContainerId="zpTree"+this.config.tree.id+"Node"+this.id+"LabelContainer";content.push(" id='");content.push(this.labelContainerId);content.push("'>");content.push("<table class='tree-table' cellpadding='0' cellspacing='0'><tbody><tr>");if(this.hasSubtree()||this.data.elementIcon){content.push("<td id='");content.push("zpTree");content.push(this.config.tree.id);content.push("Node");content.push(this.id);content.push("SignElement");content.push("'");content.push(" onclick='Zapatec.Widget.callMethod(");content.push(this.id);content.push(", \"onSignClick\")'");content.push(" ondblclick='Zapatec.Widget.callMethod(");content.push(this.id);content.push(", \"onSignDblclick\")'");if(this.data.collapsedIcon||this.data.expandedIcon||this.data.fetchingIcon||this.data.elementIcon){content.push(">");if(this.hasSubtree()){if(this.data.expandedIcon){content.push(this.data.expandedIcon);}
if(this.data.collapsedIcon){content.push(this.data.collapsedIcon);}
if(this.data.fetchingIcon){content.push(this.data.fetchingIcon);}}else{content.push(this.data.elementIcon);}}else{content.push(" class='tgb ");content.push(this.data.isExpanded?"minus":"plus");content.push("'>");}
if(Zapatec.is_khtml){content.push("&nbsp;");}
content.push("</td>");}
if(this.config.tree.config.defaultIcons){content.push("<td");content.push(" id='zpTree");content.push(this.config.tree.id);content.push("Node");content.push(this.id);content.push("IconElement'");content.push(" onclick='Zapatec.Widget.callMethod(");content.push(this.id);content.push(", \"onIconClick\")'");content.push(" ondblclick='Zapatec.Widget.callMethod(");content.push(this.id);content.push(", \"onIconDblclick\")'");content.push(" class='tgb icon ");content.push(this.config.tree.config.defaultIcons);content.push("'>")
if(Zapatec.is_khtml){content.push("&nbsp;");}
content.push("</td>");}
var attributes=Zapatec.Utils.clone(this.data.attributes);if(!attributes){attributes={"id":"zpTree"+this.config.tree.id+"Node"+this.id+"LabelElement","onclick":"Zapatec.Widget.callMethod("+this.id+", \"onLabelClick\")","ondblclick":"Zapatec.Widget.callMethod("+this.id+", \"onLabelDblclick\")","class":"label "+(this.data.childs?"menutitle":"submenu")}}else{if(!attributes.id){attributes.id="zpTree"+this.config.tree.id+"Node"+this.id+"LabelElement";}
if(attributes['class']){attributes['class']+=" label "+(this.data.childs?"menutitle":"submenu");}else{attributes['class']="label "+(this.data.childs?"menutitle":"submenu");}
if(attributes.onclick){attributes.onclick="Zapatec.Widget.callMethod("+this.id+", \"onLabelClick\");"+this.data.attributes.onclick;}else{attributes.onclick="Zapatec.Widget.callMethod("+this.id+", \"onLabelClick\");";}
if(attributes.ondblclick){attributes.ondblclick="Zapatec.Widget.callMethod("+this.id+", \"onLabelDblclick\");"+this.data.attributes.ondblclick;}else{attributes.ondblclick="Zapatec.Widget.callMethod("+this.id+", \"onLabelDblclick\");";}}
content.push("<td");for(var attrName in attributes){var tmp=attributes[attrName];if(tmp){var attrVal=tmp.replace(/'/g,"\\'");content.push(" "+attrName+"='"+attrVal+"'")}}
content.push(">");if(this.config.tree.config.prevCompatible){content.push("<span class='label'>");}
if(this.data.label){content.push(this.data.label);}
if(this.config.tree.config.prevCompatible){content.push("</span>");}
content.push("</td></tr></tbody></table></div>");if(this.data.isSelected){this.select();}
if(this.hasSubtree()){content.push("<div class='tree' id='");content.push("zpTree");content.push(this.config.tree.id);content.push("Node");content.push(this.id);content.push("ChildsContainer");content.push("'");content.push("></div>");}
this.fireEvent("afterCreate");return content.join("");}
Zapatec.Tree.Node.prototype.hasSubtree=function(){return this.data.childs!=null;}
Zapatec.Tree.Node.prototype.afterCreate=function(){if(this.isCreated){return false;}
this.labelContainer=document.getElementById(this.labelContainerId);if(this.hasSubtree()){this.signElement=this.labelContainer.childNodes[0].childNodes[0].childNodes[0].childNodes[0];this.childsContainer=this.labelContainer.nextSibling;}
if(this.config.tree.config.putBackReferences){this.getLinkToLabelElement();Zapatec.Utils.createProperty(this.labelElement,"zpTreeNode",this);}
if(this.data.collapsedIcon||this.data.expandedIcon||this.data.fetchingIcon||this.data.elementIcon){if(this.signElement&&this.signElement.childNodes.length!=0){var tmp=[this.signElement.childNodes[0],this.signElement.childNodes[1],this.signElement.childNodes[2],];if(this.hasSubtree()){for(var ii=0;ii<2;ii++){var tmpIcon=tmp[ii];if(tmpIcon.className.indexOf("collapsedIcon")>=0){this.collapsedIcon=tmpIcon;}else if(tmpIcon.className.indexOf("expandedIcon")>=0){this.expandedIcon=tmpIcon;}else if(tmpIcon.className.indexOf("fetchingIcon")>=0){this.fetchingIcon=tmpIcon;}}}else{this.elementIcon=tmp[0];}
this.putIcons();}}
this.isCreated=true;this.putLines();if(this.data.isExpanded||this.config.tree.expandToLevelNum>this.config.level){this.expand();}else{this.collapse();}
if(this.data.isSelected){this.select();}
if(this.config.tree.config.createWholeDOM){this.createChilds();}}
Zapatec.Tree.Node.prototype.getLinkToLabelElement=function(){if(this.labelElement){return this.labelElement;}
this.labelElement=this.labelContainer.childNodes[0].childNodes[0].childNodes[0].lastChild;return this.labelElement;}
Zapatec.Tree.Node.prototype.createChilds=function(){if(!this.hasSubtree()||this.hasSubtree()&&this.isChildsCreated==true){return null;}
if(this.config.tree.config.quick||this.config.isRootNode){this.initChilds();}
var content=[];for(var ii=0;ii<this.childs.length;ii++){content.push(this.childs[ii].create());}
this.childsContainer.innerHTML=content.join("");this.isChildsCreated=true;if(this.config.tree.config.createWholeDOM){for(var ii=0;ii<this.childs.length;ii++){this.childs[ii].afterCreate();}}}
Zapatec.Tree.Node.prototype.initChilds=function(){if(!this.data.childs){return null;}
for(var ii=0;ii<this.data.childs.length;ii++){this.childs.push(new Zapatec.Tree.Node({tree:this.config.tree,parentNode:this,level:this.config.level+1,source:this.data.childs[ii],sourceType:"json",eventListeners:this.config.eventListeners}));}}
Zapatec.Tree.Node.prototype.isFirstNodeInBranch=function(){return this.config.isRootNode||this.labelContainer.parentNode.firstChild==this.labelContainer;}
Zapatec.Tree.Node.prototype.isLastNodeInBranch=function(){return this.config.isRootNode||this.labelContainer.parentNode.lastChild==this.labelContainer||this.labelContainer.parentNode.lastChild==this.childsContainer;}
Zapatec.Tree.Node.prototype.putLines=function(){if(this.config.isRootNode){return null;}
this.labelContainer.className=this.labelContainer.className.replace(/tree-lines-./,"");if(this.isFirstNodeInBranch()){if(this.isLastNodeInBranch()){if(this.config.level==1){this.labelContainer.className+=" tree-lines-s";}else{this.labelContainer.className+=" tree-lines-b";}}else{if(this.config.level==1){this.labelContainer.className+=" tree-lines-t";}else{this.labelContainer.className+=" tree-lines-c";}}}else if(this.isLastNodeInBranch()){this.labelContainer.className+=" tree-lines-b";}else{this.labelContainer.className+=" tree-lines-c";}
if(this.hasSubtree()){if(this.isLastNodeInBranch()){this.childsContainer.className=this.childsContainer.className.replace(/\btree-lined\b/,"");}else{this.childsContainer.className+=" tree-lined";}}}
Zapatec.Tree.Node.prototype.putIcons=function(){if(!this.isCreated){return null;}
if(this.expandedIcon||this.collapsedIcon||this.fetchingIcon||this.elementIcon){if(this.fetchingIcon){this.fetchingIcon.style.display=this.isFetching?"block":"none";}
if(this.expandedIcon){this.expandedIcon.style.display=!this.data.isExpanded||this.isFetching?"none":"block";}
if(this.collapsedIcon){this.collapsedIcon.style.display=this.data.isExpanded||this.isFetching?"none":"block";}}else if(this.signElement){if(this.isFetching){this.signElement.className=this.signElement.className.replace(/\b(plus|minus)\b/,"fetching");}else if(this.data.isExpanded){this.signElement.className=this.signElement.className.replace(/\b(plus|fetching)\b/,"minus");}else{this.signElement.className=this.signElement.className.replace(/\b(minus|fetching)\b/,"plus");}}}
Zapatec.Tree.Node.prototype.onIconClick=function(){this.fireEvent("iconClick");if(this.config.tree.config.selectOnIconClick){if(this.config.tree.config.deselectSelected&&this.data.isSelected){this.deselect();}else{this.select();}}
if(!this.config.tree.config.expandOnIconClick){return null;}
this.toggle();}
Zapatec.Tree.Node.prototype.onIconDblclick=function(){this.fireEvent("iconDblclick");if(this.config.tree.config.selectOnIconDblclick){if(this.config.tree.config.deselectSelected&&this.data.isSelected){this.deselect();}else{this.select();}}
if(!this.config.tree.config.expandOnIconDblclick){return null;}
this.toggle();}
Zapatec.Tree.Node.prototype.onSignClick=function(){this.fireEvent("signClick");if(this.config.tree.config.selectOnSignClick){if(this.config.tree.config.deselectSelected&&this.data.isSelected){this.deselect();}else{this.select();}}
if(!this.config.tree.config.expandOnSignClick){return null;}
this.toggle();}
Zapatec.Tree.Node.prototype.onSignDblclick=function(){this.fireEvent("signDblclick");if(this.config.tree.config.selectOnSignDblclick){if(this.config.tree.config.deselectSelected&&this.data.isSelected){this.deselect();}else{this.select();}}
if(!this.config.tree.config.expandOnSignDblclick){return null;}
this.toggle();}
Zapatec.Tree.Node.prototype.onLabelClick=function(){this.fireEvent("labelClick");if(this.config.tree.config.selectOnLabelClick){if(this.config.tree.config.deselectSelected&&this.data.isSelected){this.deselect();}else{this.select();}}
if(this.config.tree.config.editable&&this.config.tree.config.editOnClick){this.config.tree.editInline.show(this.getLinkToLabelElement());}
if(!this.config.tree.config.expandOnLabelClick){return null;}
this.toggle();}
Zapatec.Tree.Node.prototype.onLabelDblclick=function(){this.fireEvent("labelDblclick");if(this.config.tree.config.selectOnLabelDblclick){if(this.config.tree.config.deselectSelected&&this.data.isSelected){this.deselect();}else{this.select();}}
if(this.config.tree.config.editable&&this.config.tree.config.editOnDblclick){this.config.tree.editInline.show(this.getLinkToLabelElement());}
if(!this.config.tree.config.expandOnLabelDblclick){return null;}
this.toggle();}
Zapatec.Tree.Node.prototype.select=function(){if(this.config.isRootNode){return null;}
if(!this.config.tree.config.selectMultiple){if(this.config.tree.prevSelected){this.config.tree.prevSelected.deselect();}}
this.data.isSelected=true;this.config.tree.prevSelected=this;if(this.config.tree.config.saveState){Zapatec.Utils.writeCookie("Zapatec.Tree-"+this.config.tree.config.saveId,this.id,null,'/',7);}
if(this.isCreated&&this.config.tree.config.highlightSelectedNode){Zapatec.Utils.addClass(this.labelContainer,"tree-item-selected");}
this.fireEvent("select");if(this.config.tree.onItemSelect){this.config.tree.onItemSelect(this.data&&this.data.attributes&&this.data.attributes.id?this.data.attributes.id:this.id);}}
Zapatec.Tree.Node.prototype.deselect=function(){if(this.config.isRootNode||!this.data.isSelected){return null;}
if(this.isCreated){this.labelContainer.className=this.labelContainer.className.replace(/\btree-item-selected\b/,"");}
this.data.isSelected=false;this.config.tree.prevSelected=null;this.fireEvent("deselect");}
Zapatec.Tree.Node.prototype.collapse=function(){this.data.isExpanded=false;if(!this.isCreated||!this.hasSubtree()){return null;}
this.childsContainer.style.display='none';if(!this.config.isRootNode){this.labelContainer.className=this.labelContainer.className.replace(/\btree-item-expanded\b/,"");this.labelContainer.className+=" tree-item-collapsed";this.putIcons();}
this.fireEvent("collapse");}
Zapatec.Tree.Node.prototype.collapseBranch=function(){if(!this.childs){return null;}
for(var ii=0;ii<this.childs.length;ii++){this.childs[ii].collapseBranch();}
this.collapse();this.fireEvent("collapseBranch");}
Zapatec.Tree.Node.prototype.expand=function(){this.data.isExpanded=true;if(this.config.tree.config.compact){var parentNodes=[];var parentNode=this;while(parentNode!=null&&!parentNode.config.isRootNode){parentNodes.push(parentNode);parentNode=parentNode.config.parentNode;}
for(var ii=this.config.tree.allNodes.length-1;ii>=0;ii--){var node=this.config.tree.allNodes[ii];if(node.data&&node.data.isExpanded){var isParent=false;for(var jj=parentNodes.length-1;jj>=0;jj--){if(node==parentNodes[jj]){isParent=true;break;}}
if(!isParent){node.collapse();}}}}
if(!this.isCreated||!this.hasSubtree()){return null;}
if(!this.config.isRootNode){this.labelContainer.className=this.labelContainer.className.replace(/\btree-item-collapsed\b/,"");this.labelContainer.className+=" tree-item-expanded"
this.putIcons();}
if(this.config.source){if(!this.isFetching){this.loadData();}}else{this.childsContainer.style.display='block';this.createChilds();for(var ii=0;ii<this.childs.length;ii++){if(!this.childs[ii].isCreated){this.childs[ii].afterCreate();}}}
this.fireEvent("expand");}
Zapatec.Tree.Node.prototype.expandBranch=function(){if(!this.childs){return null;}
for(var ii=0;ii<this.childs.length;ii++){this.childs[ii].expandBranch();}
this.expand();this.fireEvent("expandBranch");}
Zapatec.Tree.Node.prototype.toggle=function(){this.fireEvent("toggle");if(this.data.isExpanded){return this.collapse();}else{return this.expand();}}
Zapatec.Tree.Node.prototype.loadDataJson=function(objResponse){if(objResponse==null){return null;}
if(this.isCreated){if(objResponse.childs){for(var ii=0;ii<objResponse.childs.length;ii++){this.appendChild(objResponse.childs[ii]);}}}else{if(this.data==null){this.data=objResponse;}else{if(objResponse.childs){this.data.childs=this.data.childs.concat(objResponse.childs);}}
if(!this.config.tree.config.quick||this.config.isRootNode){this.initChilds();}
if(this.data.attributes&&this.data.attributes.id){this.config.tree.id2Obj[this.data.attributes.id]=this;}else{this.config.tree.id2Obj[this.id]=this;}}}
Zapatec.Tree.Node.prototype.loadDataXml=function(objSource){if(objSource==null||objSource.documentElement==null){return null;}
if(objSource.documentElement.tagName.toLowerCase()=="list"){var arr=[];for(var jj=0;jj<objSource.documentElement.childNodes.length;jj++){try{var tmp=Zapatec.Tree.Utils.convertXml2Json(objSource.documentElement.childNodes[jj]);}catch(e){}
if(tmp!=null){arr.push(tmp);}}
this.loadDataJson({childs:arr});}else{this.loadDataJson(Zapatec.Tree.Utils.convertXml2Json(objSource.documentElement));}}
Zapatec.Tree.Node.prototype.loadDataHtml=function(objSource){if(objSource==null||!objSource.nodeName){return null;}
if(objSource.nodeName.toLowerCase()=='ul'){var arr=[];for(var ii=0;ii<objSource.childNodes.length;ii++){var tmp=Zapatec.Tree.Utils.convertLi2Json(objSource.childNodes[ii],this.config.tree.config.prevCompatible);if(tmp!=null){arr.push(tmp);}}
this.loadDataJson({childs:arr});}else{this.loadDataJson(Zapatec.Tree.Utils.convertLi2Json(objSource,this.config.tree.config.prevCompatible));}}
Zapatec.Tree.Node.prototype.showLoader=function(){}
Zapatec.Tree.Node.prototype.hideLoader=function(){}
Zapatec.Tree.Node.prototype.showContainer=function(){}
Zapatec.Tree.Node.prototype.hideContainer=function(){}
Zapatec.Tree.Node.prototype.expandHere=function(){if(this.config.isRootNode){return null;}
var parentNodes=[];var parentNode=this.config.parentNode;while(parentNode!=null){parentNodes.push(parentNode);parentNode=parentNode.config.parentNode;}
for(var ii=parentNodes.length-1;ii>=0;ii--){parentNodes[ii].expand();}}
Zapatec.Tree.Node.prototype.sync=function(){if(this.config.isRootNode){return null;}
this.expandHere();this.select();}
Zapatec.Tree.Node.prototype.destroy=function(quick){if(this.isCreated&&!quick){Zapatec.Utils.destroy(this.labelContainer);if(this.hasSubtree()){Zapatec.Utils.destroy(this.childsContainer);}}
if(this.childs){for(var ii=this.childs.length-1;ii>=0;ii--){this.childs[ii].destroy(true);}}
if(this.config.isRootNode){return;}
var childIndex=null;var childsArray=this.config.parentNode.childs;for(var ii=0;ii<childsArray.length;ii++){if(childsArray[ii]==this){childIndex=ii;break;}}
if(childIndex==null){}else{if(childIndex!=0&&childsArray[childIndex-1].isCreated){childsArray[childIndex-1].putLines();}
if(childIndex!=childsArray.length-1&&childsArray[childIndex+1].isCreated){childsArray[childIndex+1].putLines();}
childsArray=childsArray.slice(0,childIndex).concat(childsArray.slice(childIndex+1));this.config.parentNode.childs=childsArray;}
for(var ii=0;ii<this.config.tree.allNodes.length;ii++){if(this.config.tree.allNodes[ii]==this){childIndex=ii;break;}}
if(childIndex==null){}else{this.config.tree.allNodes=this.config.tree.allNodes.slice(0,childIndex).concat(this.config.tree.allNodes.slice(childIndex+1));}
if(this.data.attributes&&this.data.attributes.id){this.config.tree.id2Obj[this.data.attributes.id]=null;}else{this.config.tree.id2Obj[this.id]=null;}}
Zapatec.Tree.Node.prototype.addNode=function(newChild,insertPosition){if(newChild!=null&&newChild.nodeType&&newChild.nodeType==1&&newChild.nodeName.toLowerCase()=="li"){newChild=Zapatec.Tree.Utils.convertLi2Json(newChild,this.config.tree.config.prevCompatible);}
if(newChild==null){Zapatec.Log({description:"No data given!"});return null;}
var resultNode=newChild;this.data.childs.splice(insertPosition,0,newChild);if(this.isCreated||!this.config.tree.quick){resultNode=new Zapatec.Tree.Node({tree:this.config.tree,parentNode:this,level:this.config.level+1,source:newChild,sourceType:"json",eventListeners:this.config.eventListeners});if(this.isChildsCreated){var prevNode=null;var nextNode=null;var insertBeforeElement=null;if(insertPosition!=0){prevNode=this.childs[insertPosition-1];}
if(insertPosition!=this.childs.length){nextNode=this.childs[insertPosition];insertBeforeElement=nextNode.labelContainer;}
var tmp=document.createElement("SPAN");tmp.innerHTML=resultNode.create();var nodes=[];for(var ii=0;ii<tmp.childNodes.length;ii++){nodes.push(tmp.childNodes[ii]);}
if(insertBeforeElement){for(var ii=0;ii<nodes.length;ii++){this.childsContainer.insertBefore(nodes[ii],insertBeforeElement);}}else{for(var ii=0;ii<nodes.length;ii++){this.childsContainer.appendChild(nodes[ii]);}}
resultNode.afterCreate();if(prevNode){prevNode.putLines();}
if(nextNode){nextNode.putLines();}}
this.childs.splice(insertPosition,0,resultNode);}
return resultNode;}
Zapatec.Tree.Node.prototype.appendChild=function(newChild,atStart){if(atStart){return this.addNode(newChild,0);}else{return this.addNode(newChild,this.childs.length)}}
Zapatec.Tree.Node.prototype.insertBefore=function(newChild){for(var ii=this.config.parentNode.childs.length-1;ii>=0;ii--){if(this==this.config.parentNode.childs[ii]){return this.config.parentNode.addNode(newChild,ii);}}}
Zapatec.Tree.Node.prototype.insertAfter=function(newChild){for(var ii=this.config.parentNode.childs.length-1;ii>=0;ii--){if(this==this.config.parentNode.childs[ii]){return this.config.parentNode.addNode(newChild,ii+1);}}}
Zapatec.Tree.Node.prototype.getState=function(){var result={label:this.data.label,isSelected:this.data.isSelected,attributes:Zapatec.Utils.clone(this.data.attributes)};if(this.isCreated){result.label=this.getLinkToLabelElement().innerHTML;}
if(this.hasSubtree()){result.isExpanded=this.data.isExpanded;result.source=this.config.source;result.sourceType=this.config.sourceType;result.expandedIcon=this.data.expandedIcon;result.collapsedIcon=this.data.collapsedIcon;result.fetchingIcon=this.data.fetchingIcon;result.childs=[];for(var ii=0;ii<this.childs.length;ii++){result.childs.push(this.childs[ii].getState());}}else{result.elementIcon=this.data.elementIcon;}
return result;}
Zapatec.Tree.Node.prototype.rename=function(newLabel){this.fireEvent("rename",this.data.label,newLabel);this.data.label=newLabel;var labelElement=this.getLinkToLabelElement();labelElement.innerHTML=newLabel;}
Zapatec.Tree.Utils={}
Zapatec.Tree.Utils.convertLi2Json=function(liNode,compat){if(liNode==null||liNode.nodeType!=1||liNode.nodeName.toLowerCase()!='li'){return null;}
var struct={attributes:{}};var ul=null;var expandedIcon=null;var collapsedIcon=null;var fetchingIcon=null;var icon=null;var labelEl=document.createElement("span");for(var ii=liNode.childNodes.length-1;ii>=0;ii--){var node=liNode.childNodes[ii];if(node.nodeType==1){if(node.nodeName.toLowerCase()=='img'){if(compat){if(icon==null){icon=node;}
if(expandedIcon==null){expandedIcon=node;Zapatec.Utils.addClass(expandedIcon,"expandedIcon");continue;}else if(collapsedIcon==null){collapsedIcon=node;Zapatec.Utils.addClass(collapsedIcon,"collapsedIcon");continue;}
if(icon==node){continue;}}else{if(node.className.match("\belementIcon\b")){icon=node;continue;}else if(node.className.match("\bexpandedIcon\b")){expandedIcon=node;continue;}else if(node.className.match("\bcollapsedIcon\b")){collapsedIcon=node;continue;}else if(node.className.match("\bfetchingIcon\b")){fetchingIcon=node;continue;}}}
if(node.nodeName.toLowerCase()=='ul'){ul=node;continue;}}
labelEl.insertBefore(node.cloneNode(true),labelEl.firstChild);}
if(Zapatec.is_khtml){var allChilds=labelEl.all?labelEl.all:labelEl.getElementsByTagName("*");for(var ii=0;ii<allChilds.length;ii++){var child=allChilds[ii];for(var jj=0;jj<child.attributes.length;jj++){var attr=child.attributes[jj];child.setAttribute(attr.nodeName,attr.nodeValue.replace(/"/g,"'"));}}}
struct['label']=labelEl.innerHTML;if(Zapatec.is_opera){struct['label']=struct['label'].replace(/\\"/g,"'");}
struct['isSelected']=liNode.className.match(/\bselected\b/)!=null;struct['isExpanded']=liNode.className.match(/\bexpanded\b/)!=null;for(var ii=0;ii<liNode.attributes.length;ii++){var attr=liNode.attributes[ii];struct['attributes'][attr.nodeName.toLowerCase()]=attr.nodeValue;}
if(ul==null){if(icon){Zapatec.Utils.addClass(icon,"elementIcon");var tmpIcon=document.createElement("SPAN");tmpIcon.appendChild(icon);struct['elementIcon']=tmpIcon.innerHTML;}}else{if(expandedIcon){var tmpIcon=document.createElement("SPAN");tmpIcon.appendChild(expandedIcon);struct['expandedIcon']=tmpIcon.innerHTML;}
if(collapsedIcon){var tmpIcon=document.createElement("SPAN");tmpIcon.appendChild(collapsedIcon);struct['collapsedIcon']=tmpIcon.innerHTML;}
if(fetchingIcon){var tmpIcon=document.createElement("SPAN");tmpIcon.appendChild(fetchingIcon);struct['fetchingIcon']=tmpIcon.innerHTML;}
struct['childs']=[];for(var ii=0;ii<ul.childNodes.length;ii++){var tmp=Zapatec.Tree.Utils.convertLi2Json(ul.childNodes[ii],compat);if(tmp!=null){struct['childs'].push(tmp);}}}
return struct;}
Zapatec.Tree.Utils.xml2dom=function(node){if(node.nodeType==3){return document.createTextNode(node.nodeValue);}
if(node.nodeType!=1){return null;}
var el=Zapatec.Utils.createElement(node.nodeName);for(var ii=0;ii<node.attributes.length;ii++){var attr=node.attributes[ii];if(attr.name.toLowerCase()=="class"){el.className=node.getAttribute(attr.name);}else{el.setAttribute(attr.name,node.getAttribute(attr.name));}}
if(node.hasChildNodes()){for(var ii=0;ii<node.childNodes.length;ii++){var childNode=Zapatec.Tree.Utils.xml2dom(node.childNodes[ii]);if(childNode!=null){el.appendChild(childNode);}}}
return el;}
Zapatec.Tree.Utils.convertXml2Json=function(itemNode){if(itemNode==null||itemNode.nodeType!=1||itemNode.nodeName.toLowerCase()!='item'){return null;}
var struct={content:null,attributes:{}};var list=null;var labelEl=document.createElement("span");for(var ii=itemNode.childNodes.length-1;ii>=0;ii--){var node=itemNode.childNodes[ii];if(node.nodeType!=1){continue;}
if(node.nodeName.toLowerCase()=="attribute"&&node.hasAttribute("name")){struct.attributes[node.getAttribute("name")]=node.textContent;continue;}
if(node.nodeName.toLowerCase()=='list'){list=node;continue;}
if(node.nodeName.toLowerCase()=='label'){for(var jj=0;jj<node.childNodes.length;jj++){labelEl.insertBefore(Zapatec.Tree.Utils.xml2dom(node.childNodes[jj]),labelEl.firstChild);}
continue;}}
struct['label']=labelEl.innerHTML;struct['isSelected']=itemNode.getAttribute("isSelected")=="true";struct['isExpanded']=itemNode.getAttribute("isExpanded")=="true";struct['source']=itemNode.getAttribute("source");struct['sourceType']=itemNode.getAttribute("sourceType");if(list==null){struct['elementIcon']=itemNode.getAttribute("elementIcon");}else{struct['collapsedIcon']=itemNode.getAttribute("collapsedIcon");struct['expandedIcon']=itemNode.getAttribute("expandedIcon");struct['fetchingIcon']=itemNode.getAttribute("fetchingIcon");struct['childs']=[];for(var ii=0;ii<list.childNodes.length;ii++){var tmp=Zapatec.Tree.Utils.convertXml2Json(list.childNodes[ii]);if(tmp!=null){struct['childs'].push(tmp);}}}
return struct;}
Zapatec.Tree.Utils.getNodeIndex=function(node){if(!node||!node.config||!node.config.parentNode||!node.config.parentNode.childs){return null;}
for(var ii=0;ii<node.config.parentNode.childs.length;ii++){if(node.config.parentNode.childs[ii]==node){return ii;}}}