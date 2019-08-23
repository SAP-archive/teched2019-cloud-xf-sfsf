sap.ui.define([
	"sap/ui/core/mvc/Controller",
    "teched/util/Config",
    "sap/m/MessageBox",
    "sap/ui/model/json/JSONModel"
], function(Controller, Config, MessageBox, JSONModel) {
	"use strict";

	return Controller.extend("teched.controller.App", {

		onBeforeRendering : function() {},
		
		onInit : function() {
			
			function setUserModel(user) {
				this.getView().setModel(new JSONModel(user), "user");
			};
			
			function setRequestsModel(newRequests) {
				this.getView().setModel(new JSONModel({newEmployees : newRequests}), "requests");
				
			};
			
			function showError(jqXHR, textStatus, errorThrown) {
				MessageBox.error(jqXHR.responseText);
			};
			
			jQuery.ajax({
				method: "GET",
				url: Config.serviceUrl + "/requests",
				context: this
			}).done(setRequestsModel)
			  .fail(showError);
			
			jQuery.ajax({
				method: "GET",
				url: Config.serviceUrl + "/currentUser",
				context: this
			}).done(setUserModel)
			  .fail(showError);
		},
		
		showDetails : function(evt) {
			var listItem = evt.getSource();
			var bindingContext = listItem.getBindingContext("requests");
			
			var detailsView = this.byId('detailsView');
			
			detailsView.getModel("details").setData({
				employeePhoto: "data:image/jpeg;base64," + bindingContext.getProperty("user").photo,
				employeeName: bindingContext.getProperty("user").defaultFullName,
				employeeTitle: bindingContext.getProperty("user").title,
				employeeEmail: bindingContext.getProperty("user").email,
				employeePhone: bindingContext.getProperty("user").businessPhone,
				employeeCountry: bindingContext.getProperty("user").country,
				employeeLocation: bindingContext.getProperty("user").location,
				employeeDepartment: bindingContext.getProperty("user").department
			});
			
			detailsView.byId('detailsPopover').openBy(evt.getSource());
		},
		
		completeRequest : function(evt) {
			var listItem = evt.getSource();
			var bindingContext = listItem.getBindingContext("requests");
			var id = bindingContext.getProperty("todo").todoEntryId;

			function removeRequestFromModel(data) {
				var requestsModel = bindingContext.getModel();
				var requests = requestsModel.getProperty("/newEmployees");
				var newRequests = requests.filter(function(req) {
					return req.todo.todoEntryId !== id;
				});
				requestsModel.setProperty("/newEmployees", newRequests);
			}
			
			jQuery.ajax({
				method: "DELETE",
				url: Config.serviceUrl + "/requests/" + id,
				context: this,
				data: {
					status: "COMPLETED"
				}
			}).done(removeRequestFromModel);
		},
		
	});

});
