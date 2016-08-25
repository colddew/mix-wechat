angular.module("plantApp", [])

.controller("plantController", function ($scope, $http, $location) {

    var code = $location.search().code;     // indispensable
    var state = $location.search().state;   // just for self-defined checking
    
    if(code && state) {

        var oAuthTokenUrl = "/oAuthToken/wechat?code=" + code + "&state=" + state;
        $http.get(oAuthTokenUrl).then(function(oAuthToken) {

            console.info(oAuthToken);
            console.info(oAuthToken.data);
            console.info(oAuthToken.data.openid);

            //TODO don't need user's permission, get user info indirectly if binding openid with user info
        });
    }
})

.config(function($locationProvider) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
});