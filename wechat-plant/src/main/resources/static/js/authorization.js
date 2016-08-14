angular.module("plantApp", [])

.controller("plantController", function ($scope, $http, $location) {

    // console.info($location.absUrl());
    // console.info($location.host());
    // console.info($location.port());
    // console.info($location.protocol());
    // console.info($location.path());
    // console.info($location.url());
    // console.info($location.search());
    // console.info($location.search().code);
    // console.info($location.search().state);

    // $location.path("/oAuthToken/wechat");
    // $location.replace();

    var code = $location.search().code;
    var state = $location.search().state;
    if(code && state) {

        var userInfoUrl = "/plant/userInfo?code=" + code + "&state=" + state;
        $http.get(userInfoUrl).then(function(oAuthToken) {
            var avtar = oAuthToken.data.headimgurl.replace(new RegExp(/\/0$/g), "/46");
            $scope.avtar = avtar;
            $scope.nickname = oAuthToken.data.nickname;
        });
    }
})

.config(function($locationProvider) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
});