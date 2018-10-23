<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/lable.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>秒杀详情页</title>
    <%@ include file="common/header.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3>商品详情</h3>
        </div>
        <div class="panel-body text-center">
            <h3 class="text-danger">
                <%--显示time图标--%>
                <span class="glyphicon glyphicon-time"></span>
                <%--展示倒计时--%>
                <span class="glyphicon" id="seckill-box"></span>
            </h3>
            <h4>${seckill.name}</h4>
        </div>
    </div>
</div>
<%--弹出手机模态框--%>
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhone" placeholder="请输入手机号" class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <%--输出验证信息--%>
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" class="btn btn-primary" id="btnPhoneConfirm">
                    确定
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>

    <%--使用cdn获取js http://www.bootcdn.cn/--%>
    <%--jquery 获取cookie插件--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
    <%--jquery 倒计时插件--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>
    <%--页面主要交互逻辑--%>
    <script src="/js/seckill.js" type="text/javascript"></script>
    <%--传入参数，进行页面初始化--%>
    <script type="text/javascript">
        $(function () {
            seckill.detail.init({
                seckillId: ${seckill.seckillId},
                startTime: ${seckill.startTime.time},
                endTime: ${seckill.endTime.time}
            });
        });
    </script>
</body>
</html>