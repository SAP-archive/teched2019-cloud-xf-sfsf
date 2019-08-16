sap.ui.define([], function() {
	"use strict";

	return {
		serviceUrl : "v1",
		highContrastTheme : "sap_belize_hcb",
		standardTheme : sap.ui.getCore().getConfiguration().getTheme(),

		initApp : function(elementId) {

			new sap.m.Shell({
				showLogout : false,
				app : new sap.ui.core.ComponentContainer({
					name : 'teched'
				})
			}).placeAt(elementId);
		}
	
	};
});