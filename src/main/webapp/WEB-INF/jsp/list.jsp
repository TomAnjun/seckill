<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/lable.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>秒杀列表页</title>
    <title>秒杀列表 页</title>
    <%@ include file="common/header.jsp" %>
</head>
<body>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3>秒杀列表</h3>
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <td>商品库存id</td>
                            <td>商品名称</td>
                            <td>库存数量</td>
                            <td>秒杀开启时间</td>
                            <td>秒杀结束时间</td>
                            <td>创建时间</td>
                            <td>详情</td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="seckill" items="${list}">
                            <tr>
                                <td>${seckill.seckillId}</td>
                                <td>${seckill.name}</td>
                                <td>${seckill.number}</td>
                                <td>
                                    <fmt:formatDate value="${seckill.startTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                </td>
                                <td>
                                    <fmt:formatDate value="${seckill.endTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                </td>
                                <td>
                                    <fmt:formatDate value="${seckill.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                </td>
                                <td><a class="btn btn-info" href="/seckill/${seckill.seckillId}/detail" target="_blank">详情</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
</body>
</html>