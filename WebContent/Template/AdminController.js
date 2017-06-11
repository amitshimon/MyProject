var app = angular.module("Admin", [ "ngRoute" ]).config(
		function($routeProvider) {
			$routeProvider.when("/getCompany", {
				templateUrl : "Template/getCompany.html",
				controller : "getAll"
			}).when("/getAllCompanies", {
				templateUrl : "Template/getAllCompanies.html",
				controller : "getAll"
			}).when("/getCustomer", {
				templateUrl : "Template/getCustomer.html",
				controller : "customerController"
			}).when("/getAllCustomers", {
				templateUrl : "Template/getAllCustomers.html",
				controller : "customerController"
			}).when("/createCompany", {
				templateUrl : "Template/createCompany.html",
				controller : "create2"
			}).when("/removeCompany", {
				templateUrl : "Template/removeCompany.html",
				controller : "remove"
			}).when("/updateCompany", {
				templateUrl : "Template/updateCompany.html",
				controller : "createUpdate"
			}).when("/createCustomer", {
				templateUrl : "Template/createCustomer.html",
				controller : "create2"
			}).when("/removeCustomer", {
				templateUrl : "Template/removeCustomer.html",
				controller : "remove"
			}).when("/updateCustomer", {
				templateUrl : "Template/updateCustomer.html",
				controller : "createUpdate"
			})
		}).controller(
		"getAll",
		function($scope, $http) {
			$scope.companies = {};
			$scope.getCompanies = function() {
				$http.get("/CouponProject/rest/admin/getAllCompanies").then(
						function(response) {
							$scope.companies = response.data.webCompany;
						})
			}
			$scope.getCompanies();
			$scope.companies = {};
			$scope.setSelectedCompany = function(company) {
				$scope.selectedCompany = {};
				$http.get("/CouponProject/rest/admin/getCompany", {
					params : {
						id : company.id
					}
				}).then(function(response) {
					$scope.selectedCompany = response.data;
				})
				$scope.selectedCompany = {};
			}
			$scope.getCompany = function() {
				$scope.company = {};
				$http.get("/CouponProject/rest/admin/getCompany", {
					params : {
						id : $scope.id
					}
				}).then(function(response) {
					$scope.company = response.data;
				})
				$scope.company = {};
			}

		}).controller("createUpdate", function($scope, $http) {
	$scope.updateCompany = function() {
		$http.get("/CouponProject/rest/admin/updateCompany", {
			params : {
				id : $scope.id,
				password : $scope.password,
				email : $scope.email
			}
		}).then(function(response) {
			$scope.company = response.data;
		})
	}
	$scope.updateCustomer = function() {
		$http.get("/CouponProject/rest/admin/updateCustomer", {
			params : {
				id : $scope.id,
				password : $scope.password,
				email : $scope.email
			}
		}).then(function(response) {
			$scope.customer = response.data;
		})
	}
}).controller("remove", function($scope, $http) {
	$scope.removeCompany = function() {
		$http.get("/CouponProject/rest/admin/removeCompany", {
			params : {
				id : $scope.id
			}
		}).then(function(response) {
			$scope.company = response.data;
		})
	}
	$scope.removeCustomer = function() {
		$http.get("/CouponProject/rest/admin/removeCustomer", {
			params : {
				id : $scope.id
			}
		}).then(function(response) {
			$scope.customer = response.data;
		})
	}

}).controller(
		"customerController",
		function($scope, $http) {
			$scope.customers = {};
			$scope.getCustomers = function() {
				$http.get("/CouponProject/rest/admin/getAllCustomers").then(
						function(response) {
							$scope.customers = response.data.webCustomer;
						})
				$scope.customers = {};
			}
			$scope.getCustomers();
			$scope.setSelectedCustomer = function(customer) {
				$scope.selectedCustomer = {};
				$http.get("/CouponProject/rest/admin/getCustomer", {
					params : {
						id : customer.id
					}
				}).then(function(response) {
					$scope.selectedCustomer = response.data;
				})
				$scope.selectedCustomer = {};
			}
			$scope.getCustomer = function() {
				$scope.customer = {};
				$http.get("/CouponProject/rest/admin/getCustomer", {
					params : {
						id : $scope.id
					}
				}).then(function(response) {
					$scope.customer = response.data;
				})
				$scope.customer = {};
			}
		}).controller(
		'create2',
		function($scope, $http) {

			$scope.createCompany = function() {
				document.getElementById("reset").reset();
				var formData = new FormData();
				formData.append("companyName", $scope.companyName);
				formData.append("password", $scope.password);
				formData.append("email", $scope.email);
				return $http.post("/CouponProject/rest/admin/createCompany",
						formData, {
							headers : {
								'Content-Type' : undefined
							},
							transformRequest : angular.identity
						}).then(function(response) {
					$scope.company = response.data;
				})
			}
			$scope.createCustomer = function() {
				document.getElementById("reset").reset();
				var formData = new FormData();
				formData.append("name", $scope.name);
				formData.append("email", $scope.email);
				formData.append("password", $scope.password);
				return $http.post("/CouponProject/rest/admin/createCustomer",
						formData, {
							headers : {
								'Content-Type' : undefined
							},
							transformRequest : angular.identity
						}).then(function(response) {
							$scope.customer = response.data;
						})
			}

		})
