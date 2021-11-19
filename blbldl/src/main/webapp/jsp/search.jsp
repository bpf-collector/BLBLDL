<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>
<!DOCTYPE>
<html lang="zh">
<head>
    <title>B L B L D L - 搜索</title>

    <script type="text/javascript" src="static/script/jquery-3.1.1.min.js"></script>

    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }
        .search_div {
            background-color: gray;
            padding-bottom: 20px;
        }
        .search_div ul li {
            list-style: none;
            display: inline-block;
            cursor: pointer;
            text-align: center;
            vertical-align: middle;
            height: 40px;
            font-size: 22px;
            width: 33%;
            line-height: 40px;
        }
        .search_content div {
            display: none;
        }
        .li_selected {
            color: white;
            border-bottom: 1px solid white;
        }
        #div_show {
            display: block;
        }

        .search_content span {
            font-size: 16px;
            font-weight: bold;
        }
        .search_content table {
            margin: 0 auto;
            margin-top: 20px;
        }
        .search_content div {
            text-align: center;
        }
        input[type="submit"] {
            font-size: 18px;
            padding: 5px 40px;
            margin-top: 15px;
        }
        input[type="text"] {
            height: 25px;
            outline: none;
            padding: 0 2px;
            font-size: 16px;
        }

        .result_div table {
            min-width: 250px;
            border-collapse: collapse;
            /* 设置内容过多时，表格不会被撑开 */
            table-layout: fixed;
            word-break: break-all;
            margin: 0 auto;
            margin-top: 20px;
        }
        .result_div table tr td {
            border: 2px solid rgba(0, 0, 0, 0.9);
            font-size: 16px;
            height: 35px;
            padding-left: 10px;
            padding-right: 10px;
            color: #393d49;
        }
        .result_div table tr td a {
            text-decoration: none;
            color: #393d49;
        }
        .result_div table tr td a:hover {
            text-decoration: underline;
        }
    </style>

    <script type="text/javascript">
        $(function (){
            $(".search_div ul li").click(function () {
                $(".search_div ul li").removeClass("li_selected");
                let className = ".search_content ." + $(this).attr("class");
                $(this).addClass("li_selected");
                console.log(className);
                $(".search_content div").attr("id", "");
                $(className).attr("id", "div_show");
            })
        });
    </script>
</head>
<body>
<%-- 搜索栏 --%>
<div class="search_div">
    <ul>
        <li class="all_course" class="li_selected">全部课程</li>
        <li class="cou_title">课程标题</li>
        <li class="all_owner">全部UP主</li>
    </ul>
    <div class="search_content">
        <div class="all_course div_show">
            <form action="searchServlet" method="post">
                <input type="hidden" name="action" value="getAllCourse">
                <input style="margin-top: 64px;" type="submit" value="查询所有课程" />
            </form>
        </div>
        <div class="cou_title">
            <form action="searchServlet" method="post">
                <input type="hidden" name="action" value="getCourseLikeTitle">
                <table>
                    <tr>
                        <td><span>包含课程标题：</span></td>
                        <td><input type="text" name="title"></td>
                    </tr>
                </table>
                <input type="submit" value="查询" />
            </form>
        </div>
        <div class="all_owner">
            <form action="searchServlet" method="post">
                <input type="hidden" name="action" value="getAllOwner">
                <input style="margin-top: 64px;" type="submit" value="查询所有UP主" />
            </form>
        </div>
    </div>
</div>

<%-- 结果 --%>
<div class="result_div">
    <c:if test="${empty ownerList && not empty courseList}">
        <table>
            <tr>
                <td>序号</td>
                <td>标题</td>
                <td>集数</td>
                <td>作者</td>
            </tr>
            <c:forEach items="${courseList}" var="course" varStatus="num">
                <tr>
                    <td>${num.count}</td>
                    <td><a href="courseServlet?action=searchCourse&url=https://www.bilibili.com/video/${course.bvId}">${course.title}</a></td>
                    <td>${course.videos}</td>
                    <td>${ownerMap.get(course.ownerId).name}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${not empty ownerList}">
        <table>
            <tr>
                <td>序号</td>
                <td>昵称</td>
            </tr>
            <c:forEach items="${ownerList}" var="owner" varStatus="num">
                <tr>
                    <td>${num.count}</td>
                    <td><a href="searchServlet?action=getCourseByOwnerId&ownerId=${owner.id}">${owner.name}</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
