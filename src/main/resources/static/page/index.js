var timetemp = 1000*60*3;
var tepnum = 0;
window.onload= function(){
    getNum();
    var interval3=setInterval(function(){
        getNum();
    },timetemp);
}
window.getNum = function(){
    tepnum++;
    $("#static").html("检索种....");
    $.ajax({
        url:'CheckPendingMember',
        type:'post',
        dataType:'json',
        success:function(data){
            $("#tempSearchNum").html(tepnum);
            if(data.status == 0){
                notifyMe();
                $("#static").html('检索结束');
                $("#restnum").html(data.totalRows);
                // if(window.Notification && Notification.permission !== "denied") {
                //     Notification.requestPermission(function(status) {
                //         Notification.requestPermission();
                //
                //     });
                // }
                if(data.totalRows==0){
                    $("#restnum").css("color","black");
                }else{

                    $("#restnum").css("color","red");
                }
                $("#Threetime").html('300');
                clickstart();
            }else{
                $("#static").html('接口异常');
                $("#Threetime").html('300');
                clickstart();

            }

        },
        error:function(){
            $("#static").html('接口异常');
            $("#Threetime").html('300');
            clickstart();

        }
    })
}

interval1=setInterval(getNum,timetemp);
clearInterval(interval1);


function bang() {
    var inp = $("#Threetime").html();
    var time = parseInt(inp) - 1;
    $("#Threetime").html(time);

    if(time == 0) {
        clearInterval(clock);
    }
}

var clock;
function clickstart(){
    clock= setInterval('bang()', 1000);
}
Notification.requestPermission(function() {
    if (Notification.permission === 'granted') {
        // 用户点击了允许
        var n = new Notification('网易有道词典', {
            body: '程序已启动',
            // icon: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549464117983&di=286ad42d05b9ea9720daa1d62cd18ee5&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F8326cffc1e178a8208b90d86fc03738da977e80b.jpg'
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

function notifyMe() {
    // 判断浏览器是否支持唤醒
    if (window.Notification) {
        var popNotice = function () {
            if (!Notification.permission === 'granted') return
                var notification = new Notification('阿巴阿巴', {
                body: '提示提示提示'
            })
            // 点击通知的回调函数
            notification.onclick = function () {
                window.open('https://www.tf0914.com/login')
                notification.close()
            }
        }
        /* 授权过通知 */
        if (Notification.permission === 'granted') {
            popNotice()
        } else {
            /* 未授权，先询问授权 */
            Notification.requestPermission(function (permission) {
                popNotice()
            })
        }
    }
}