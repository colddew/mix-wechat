//url在JavaScript中是location.href.split('#')[0]获取

wx.config({
    debug: false,
    appId: 'wx9e3a65944f0150f9',
    timestamp: 1471884418,
    nonceStr: 'tQaPUKLZajYhM8gD',
    signature: '3efeb0686462f19736fdf5896684711e676953f5',
    jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage', 'getNetworkType', 'openLocation', 'getLocation']
});

wx.ready(function() {
    console.info("config for jsapi success...");
});

wx.error(function(res) {
    console.info("config for jsapi error...");
});

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
    scale: 1,
    infoUrl: 'http://www.163.com'
});

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