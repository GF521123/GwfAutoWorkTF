
Notification.requestPermission(function() {
    if (Notification.permission === 'granted') {
        // 用户点击了允许
        var n = new Notification('网易有道词典', {
            body: '程序已启动',
            icon: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549464117983&di=286ad42d05b9ea9720daa1d62cd18ee5&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F8326cffc1e178a8208b90d86fc03738da977e80b.jpg'
        })

        setTimeout(function() {
            n.close();
    },
        3000
    )

        n.onclick = function (e) {
            window.open("https://www.baidu.com")
            console.log(1, e);
        }

        n.onerror = function (e) {
            console.log(2, e);
        }

        n.onshow = function (e) {
            console.log(3, e);
        }

        n.onclose = function (e) {
            console.log(4, e);
        }

    } else if (Notification.permission === 'denied') {
        // 用户点击了拒绝
    } else {
        // 用户没有做决定
    }
})