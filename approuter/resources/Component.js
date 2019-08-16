sap.ui.define([
	"sap/ui/core/UIComponent",
	"teched/util/Config",
	"sap/ui/model/json/JSONModel",
	"sap/ui/Device"
], function(UIComponent, Config, JSONModel, Device) {
	"use strict";

	return UIComponent.extend("teched.Component", {

		metadata : {
			manifest : "json"
		},

		init : function() {
			UIComponent.prototype.init.apply(this, arguments);
		}

	});

});
