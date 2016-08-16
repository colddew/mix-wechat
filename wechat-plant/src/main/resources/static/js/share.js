wx.config({
    debug: true,
    appId: 'wx9e3a65944f0150f9',
    timestamp: 1471367293,
    nonceStr: 'MyfVDTK5IvQlsrHU',
    signature: '4e8b5958025f0d907c49b0557b5cc9983ba8465d',
    jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage', 'getNetworkType', 'openLocation', 'getLocation']
});

wx.ready(function() {
    console.info("config for jsapi success...");
});

// wx.error(function(res) {
//     // console.info(res);
//     console.info("config for jsapi error...");
// });

// wx.checkJsApi({
//     jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage', 'getNetworkType', 'openLocation', 'getLocation'],
//     success: function(res) {
//         console.info("check for jsapi...");
//     }
// });

// wx.onMenuShareTimeline({
//     title: '分享到朋友圈标题',
//     link: '',
//     imgUrl: '',
//     success: function() {
//
//     },
//     cancel: function() {
//
//     }
// });
//
// wx.onMenuShareAppMessage({
//     title: '分享给朋友标题',
//     desc: '分享描述',
//     link: '',
//     imgUrl: '',
//     type: 'link',
//     dataUrl: '',
//     success: function() {
//
//     },
//     cancel: function() {
//
//     }
// });

// wx.getNetworkType({
//     success: function(res) {
//         var networkType = res.networkType;
//         console.info(networkType);
//     }
// });

wx.openLocation({
    latitude: 30.16,
    longitude: 120.07,
    name: '黄龙',
    address: '黄龙时代广场',
    scale: 5,
    infoUrl: 'http://www.163.com'
});

wx.getLocation({
    type: 'wgs84',
    success: function(res) {
        var latitude = res.latitude;
        var longitude = res.longitude;
        var speed = res.speed;
        var accuracy = res.accuracy;
        console.info(res);
    }
});