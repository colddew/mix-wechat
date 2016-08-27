//url在JavaScript中是location.href.split('#')[0]获取

// wx.config({
//     debug: false,
//     appId: 'wx9e3a65944f0150f9',
//     timestamp: 1472055867,
//     nonceStr: 'sRCikZeuKiCOZ7oN',
//     signature: '176385337c55ccb3ee41766235b1ae19096b4e15',
//     jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage', 'getNetworkType', 'openLocation', 'getLocation']
// });

// wx.error(function(res) {
//     console.info("config for jsapi error...");
// });

// wx.checkJsApi({
//     jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage', 'getNetworkType', 'openLocation', 'getLocation'],
//     success: function(res) {
//         console.info("check for jsapi...");
//     }
// });

// wx.getLocation({
//     type: 'wgs84',
//     success: function(res) {
//         var latitude = res.latitude;
//         var longitude = res.longitude;
//         var speed = res.speed;
//         var accuracy = res.accuracy;
//         console.info(res);
//     }
// });

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
                jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage', 'getNetworkType', 'openLocation', 'getLocation']
            });

            wx.error(function(res) {
                console.info("config for jsapi error...");
            });

            wx.ready(function() {

                console.info("config for jsapi success...");

                $scope.openLocation = function () {

                    wx.openLocation({
                        latitude: 23.099994,
                        longitude: 113.324520,
                        name: 'TIT 创意园',
                        address: '广州市海珠区新港中路 397 号',
                        scale: 14,
                        infoUrl: 'http://weixin.qq.com'
                    });
                };

                $scope.shareAppMessage = function () {

                    wx.onMenuShareAppMessage({
                        title: '互联网之子',
                        desc: '在长大的过程中，我才慢慢发现，我身边的所有事，别人跟我说的所有事，那些所谓本来如此，注定如此的事，它们其实没有非得如此，事情是可以改变的。更重要的是，有些事既然错了，那就该做出改变。',
                        link: 'http://movie.douban.com/subject/25785114/',
                        imgUrl: 'http://img3.douban.com/view/movie_poster_cover/spst/public/p2166127561.jpg',
                        type: 'link',
                        dataUrl: '',
                        trigger: function () {
                            alert('用户点击发送给朋友');
                        },
                        success: function () {
                            alert('已分享');
                        },
                        cancel: function () {
                            alert('已取消');
                        },
                        fail: function (res) {
                            alert(JSON.stringify(res));
                        }
                    });
                };

                $scope.shareTimeline = function () {

                    wx.onMenuShareTimeline({
                        title: '互联网之子',
                        link: 'http://movie.douban.com/subject/25785114/',
                        imgUrl: 'http://img3.douban.com/view/movie_poster_cover/spst/public/p2166127561.jpg',
                        trigger: function () {
                            alert('用户点击分享到朋友圈');
                        },
                        success: function () {
                            alert('已分享');
                        },
                        cancel: function () {
                            alert('已取消');
                        },
                        fail: function (res) {
                            alert(JSON.stringify(res));
                        }
                    });
                };

                $scope.getNetworkType = function () {

                    wx.getNetworkType({
                        success: function(res) {
                            alert(res.networkType);
                        }
                    });
                }
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