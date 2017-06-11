var app = angular.module("Company", [ "ngRoute" ]).config(
		function($routeProvider) {
			$routeProvider.when("/createCoupon", {
				templateUrl : "CompanyTemplate/createCoupon.html",
				controller : "fileUploader"
			}).when("/updateCoupon", {
				templateUrl : "CompanyTemplate/updateCoupon.html",
				controller : "create"
			}).when("/removeCoupon", {
				templateUrl : "CompanyTemplate/removeCoupon.html",
				controller : "remove"
			}).when("/getCoupon", {
				templateUrl : "CompanyTemplate/getCoupon.html",
				controller : "getCoupon"
			}).when("/getAllCoupons", {
				templateUrl : "CompanyTemplate/getAllCoupons.html",
				controller : "getAll"
			}).when("/getCouponByType", {
				templateUrl : "CompanyTemplate/getCouponByType.html",
				controller : "byType"
			}).when("/image", {
				templateUrl : "CompanyTemplate/image.html",
				controller : ""
			})

		}).controller("create", function($scope, $http) {
	document.getElementById("reset").reset();
	$scope.company = {};
	$scope.updateCoupon = function() {
		$http.get("/CouponProject/rest/company/updateCoupon", {
			params : {
				id : $scope.id,
				price : $scope.price,
				message : $scope.message,
				type : $scope.type,
				amount : $scope.amount,
				endDate : $scope.endDate,
				title : $scope.title
			}
		}).then(function(response) {
			$scope.company = response.data;
		})
		$scope.company = {};
	}

}).controller("remove", function($scope, $http) {
	$scope.removeCoupon = function() {
		$http.get("/CouponProject/rest/company/removeCoupon", {
			params : {
				id : $scope.id,
			}
		}).then(function(response) {
			$scope.company = response.data;
		})

	}

}).controller("getCoupon", function($scope, $http) {
	$scope.webCompany = {};
	$scope.getCoupon = function() {
		$http.get("/CouponProject/rest/company/getCoupon", {
			params : {
				id : $scope.id
			}
		}).then(function(response) {
			$scope.webCompany = response.data;
		})
		$scope.webCompany = {};
	}

}).controller(
		"getAll",
		function($scope, $http) {
			$scope.webCompany = {};
			$scope.getAllCoupons = function() {
				$http.get("/CouponProject/rest/company/getAllCoupons").then(
						function(response) {
							$scope.webCompany = response.data;

						})
				$scope.webCompany = {};	
			}
			$scope.getAllCoupons();
		}).controller(
				"byType",
				function($scope, $http) {
			$scope.webCompany = {};
			$scope.getCouponByType = function() {
				$http.get("/CouponProject/rest/company/getCouponByType", {
					params : {
						type : $scope.type
					}

				}).then(function(response) {
					$scope.webCompany = response.data;
				})
				$scope.webCompany = {};
			}
		}).controller('fileUploader', fileUploader)
fileUploader.$inject = [ '$scope', '$http' ];
function convert(str) {
	var date = new Date(str), mnth = ("0" + (date.getMonth() + 1)).slice(-2), day = ("0" + date
			.getDate()).slice(-2);
	return [ date.getFullYear(), mnth, day ].join("-");
}
function fileUploader($scope, $http) {
	$scope.setFile = function(element) {
		$scope.currentFile = element.files[0];
	}

	$scope.UploadFile = function() {
		document.getElementById("reset").reset();
		var formData = new FormData();
		formData.append("file", $scope.currentFile);
		formData.append("price", $scope.price);
		formData.append("message", $scope.message);
		formData.append("type", $scope.type);
		formData.append("amount", $scope.amount);
		formData.append("startDate", convert($scope.startDate));
		formData.append("endDate", convert($scope.endDate));
		formData.append("title", $scope.title);
		return $http.post("/CouponProject/rest/company/createCoupon", formData,
				{
					headers : {
						'Content-Type' : undefined
					},
					transformRequest : angular.identity
				});
	}
};