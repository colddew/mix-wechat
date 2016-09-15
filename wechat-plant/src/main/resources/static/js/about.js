angular.module("plantApp", [])

.controller("plantController", function ($scope, $http, $location) {

    var appId = 'wx9e3a65944f0150f9';
    var href = $location.$$absUrl;
    var origin = $location.$$protocol + "://" + $location.$$host;

    if(href) {

        var jsApiConfigUrl = origin + "/plant/jsApiConfig?url=" + href;
        $http.get(jsApiConfigUrl).then(function(jsApiConfig) {

            wx.config({
                debug: false,
                appId: appId,
                timestamp: jsApiConfig.data.timestamp,
                nonceStr: jsApiConfig.data.nonceStr,
                signature: jsApiConfig.data.signature,
                jsApiList: ['openLocation']
            });

            wx.error(function(res) {
                console.info("config for jsapi error...");
            });

            wx.ready(function() {

                console.info("config for jsapi success...");

                $scope.openLocation = function () {

                    wx.openLocation({
                        latitude: 30.2730176988,
                        longitude: 120.1250018758,
                        name: '杭州绿色连萌网络技术有限公司',
                        address: '杭州西湖区万塘路18号黄龙时代广场',
                        scale: 12,
                        infoUrl: 'http://www.qq.com/'
                    });
                };
            });
        });
    }
})

.config(function($locationProvider) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
});