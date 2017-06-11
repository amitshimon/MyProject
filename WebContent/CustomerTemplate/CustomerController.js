var app = angular
		.module("Customer", [ "ngRoute" ])
		.config(
				function($routeProvider) {
					$routeProvider
							.when(
									"/getAllPurchasedCouponByPrice",
									{
										templateUrl : "CustomerTemplate/getAllPurchasedCouponByPrice.html",
										controller : "getByPrice"
									})
							.when(
									"/getAllPurchasedCouponByType",
									{
										templateUrl : "CustomerTemplate/getAllPurchasedCouponByType.html",
										controller : "getByType"
									})
							.when(
									"/getAllPurchasedCoupons",
									{
										templateUrl : "CustomerTemplate/getAllPurchasedCoupons.html",
										controller : "getAll"
									})
							.when(
									"/PurchasedCoupons",
									{
										templateUrl : "CustomerTemplate/PurchasedCoupons.html",
										controller : "purchased"
									}).when("/image", {
								templateUrl : "CompanyTemplate/image.html",
								controller : ""
							})
				})
		.controller(
				"getByPrice",
				function($scope, $http) {
					$scope.webCustomers = {};
					$scope.purchasedByPrice = function() {
						$http
								.get(
										"/CouponProject/rest/customer/getAllPurchasedCouponByPrice",
										{
											params : {
												price : $scope.price,
												price2 : $scope.price2
											}
										}).then(function(response) {
									$scope.webCustomers = response.data;
								})
						$scope.webCustomers = {};
					}

				})
		.controller(
				"getByType",
				function($scope, $http) {
					$scope.webCustomers = {};
					$scope.purchasedByType = function() {
						$http
								.get(
										"/CouponProject/rest/customer/getAllPurchasedCouponByType",
										{
											params : {
												type : $scope.type
											}
										}).then(function(response) {
									$scope.webCustomers = response.data;
								})
						$scope.webCustomers = {};
					}
				})
		.controller(
				"getAll",
				function($scope, $http) {
					$scope.webCustomers = {};
					$scope.allPurchased = function() {
						$http
								.get(
										"/CouponProject/rest/customer/getAllPurchasedCoupons")
								.then(function(response) {
									$scope.webCustomers = response.data;
								})
						$scope.webCustomers = {};
					}
					$scope.allPurchased();
				})
		.controller(
				"purchased",
				function($scope, $http) {
					$scope.purchassedCoupon = function() {
						$http
								.get(
										"/CouponProject/rest/customer/purchasedCoupons",
										{
											params : {
												id : $scope.couponId
											}
										}).then(function(response) {
									$scope.webCustomer = response.data;
								})
					}
					$scope.webCustomers = {};
					$scope.getAllCoupons = function() {
						$http.get("/CouponProject/rest/customer/getAllCoupons")
								.then(function(response) {
									$scope.webCustomers = response.data;
								})
						$scope.webCustomers = {};
					}
					$scope.getAllCoupons();
				})