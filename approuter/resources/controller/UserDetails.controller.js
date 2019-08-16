sap.ui.define([
	"sap/ui/core/mvc/Controller",
    "teched/util/Config",
    "sap/m/MessageBox",
    "sap/ui/model/json/JSONModel"
], function(Controller, Config, MessageBox, JSONModel) {
	"use strict";

	return Controller.extend("teched.controller.UserDetails", {

		onBeforeRendering : function() {},
		
		onInit : function() {
			this.getView().setModel(new JSONModel(), "details");
		}
	});
});
