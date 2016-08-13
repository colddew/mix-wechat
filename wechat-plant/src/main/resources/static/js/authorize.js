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

    // $location.path("/oauthToken/wechat");
    // $location.replace();

    var code = $location.search().code;
    var state = $location.search().state;
    if(code && state) {
        $http.get("/oauthToken/wechat?code=" + code + "&state=" + state)
            .then(function(oAuthToken) {
            console.info(oAuthToken);
        });
    }
})

.config(function($locationProvider) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
});