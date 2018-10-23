//页面交互逻辑代码
//javascirpt模块化
var seckill = {
    //页面中涉及到的URL
    URL : {
        now : function(){
            return "/seckill/time/now"
        },
        md5 : function(seckillId){
            return "/seckill/" + seckillId + "/exposer";
        },
        kill : function(seckillId, md5){
            return "/seckill/" + seckillId + "/" + md5 + "/execution"
        }
    },
    //验证手机号
    validatePhone : function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true
        } else {
            return false;
        }
    },
    //执行秒杀函数
    excuteKill : function (seckillId, seckillBox) {
        $.post(seckill.URL.md5(seckillId), {}, function (result) {
            if (result && result['success']) {
                var data = result['data'];
                var md5 = data['md5'];
                $.post(seckill.URL.kill(seckillId, md5), {}, function (success) {
                    if (success && success['success']) {
                        var killData = success['data'];
                        seckillBox.hide().html(killData['stateInfo']).show();
                    } else {
                        console.log(success);
                    }
                });
            } else {
                console.log(result);
            }
        })
    },
    //计时函数
    countDown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        if (nowTime > endTime) {
            seckillBox.hide().html('秒杀结束').show();
        }else if(nowTime < startTime) {
            //秒杀未开始，开始计时事件绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                //时间格式
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.hide().html(format).show();
            }).on('finish.countdown', function () {
                //计时完成，获取秒杀地址，控制实现逻辑，执行秒杀
                //显示秒杀按钮
                seckillBox.hide().html('<button class="btn btn-info" id="seckill-btn"></button>').show();
                var seckillBtn = $('#seckill-btn').on('click', function () {
                    seckill.excuteKill(seckillId, seckillBox);
                });
            })
        }
        else{
            //秒杀开始,获取秒杀地址，实现秒杀
            seckillBox.hide().html('<button class="btn btn-info" id="seckill-btn">点击秒杀</button>').show();
            $('#seckill-btn').on('click', function () {
                seckill.excuteKill(seckillId, seckillBox);
            });
        }
    },
    detail : {
        //页面初始化
        init : function(params){
            //验证用户是否登录
            var killPhone = $.cookie('killPhone');
            //验证手机号，多出地方需要用到，需要封装为一个方法
            if (!seckill.validatePhone(killPhone)) {
                //如果cookie中存放的信息不通过验证，则提醒用户输入手机号，并进行写入cookie操作
                var killPhoneModal = $("#killPhoneModal");
                //显示弹出层
                killPhoneModal.modal({
                    show: true,//显示模态框
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false//关闭键盘事件
                });
                $('#btnPhoneConfirm').click(function () {
                    var killPhone = $("#killPhone").val();
                    if (seckill.validatePhone(killPhone)) {
                        //电话写入cookie
                        $.cookie('killPhone', killPhone, {expires: 7, path: '/seckill'});
                        //重新加载页面
                        window.location.reload();
                    }else{
                        //显示提示信息
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                    }
                });
            } else {
                //获取服务器时间
                $.get(seckill.URL.now(), {}, function (result) {
                    if (result && result['success']) {
                        var nowTime = result['data'];
                        var seckillId = params['seckillId'];
                        var startTime = params['startTime'];
                        var endTime = params['endTime'];
                        seckill.countDown(seckillId, nowTime, startTime, endTime);
                    } else {
                        console.log(result);
                    }
                });
            }
        }
    }
}