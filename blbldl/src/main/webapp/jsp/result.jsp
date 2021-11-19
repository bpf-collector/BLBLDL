<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp"%>
<!DOCTYPE>
<html lang="zh">
<head>
    <title>B L B L D L - 搜索结果</title>

    <script type="text/javascript" src="static/script/jquery-3.1.1.min.js"></script>

    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }
        .course_div, .page_div {
            background-color: gray;
            padding-bottom: 20px;
        }
        .course_div p, .page_div p {
            display: block;
            width: 100%;
            height: 40px;
            font-size: 20px;
            line-height: 40px;
            background-color: #282b33;
            color: #FFFFFF;
            padding-left: 20px;
        }
        .course_div table, .page_div table {
            min-width: 250px;
            border-collapse: collapse;
            /* 设置内容过多时，表格不会被撑开 */
            table-layout: fixed;
            word-break: break-all;
            margin: 0 auto;
            margin-top: 20px;
        }
        .course_div table tr td, .page_div table tr td {
            border: 2px solid rgba(0, 0, 0, 0.9);
            font-size: 16px;
            height: 35px;
            padding-left: 10px;
            padding-right: 10px;
            color: #393d49;
        }
        .course_div table tr td textarea {
            outline: none;
            background-color: gray;
            color: #393d49;
            width: 800px;
            height: 90px;
            margin: 5px 0px;
        }
        .course_div table tr td a, .page_div table tr td a {
            text-decoration: none;
            color: #393d49;
        }
        .course_div table tr td a:hover, .page_div table tr td a:hover {
            text-decoration: underline;
        }

        .num_table tr td input[type="number"] {
            width: 80px;
            outline: none;
            padding: 0px 5px;
        }

        .path {
            width: 430px;
            height: 25px;
            outline: none;
            padding-left: 2px;
        }

        .download_btn {
            margin: 10px auto;
            width: 100px;
            height: 32px;
            line-height: 28px;
            background-color: coral;
            color: aliceblue;
            border-radius: 8px;
            outline: none;
            display: block;
            font-size: 16px;
        }
    </style>

    <script type="text/javascript">
        window.onload = function () {
            document.getElementById("download_btn").onclick = function () {
                let download_checkbox = document.getElementsByName("download");
                let count = 0;
                for (let i = 0; i < download_checkbox.length; i++) {
                    if (download_checkbox[i].checked == true) {
                        count++;
                        break;
                    }
                }
                if (count == 0) {
                    alert("您并没有选择下载的视频呢！");
                    return false;
                }

                let path_input = document.getElementById("outPath");
                if (path_input.value.trim().length == 0 ) {
                    alert("请输入保存的路径！");
                    path_input.value = "";
                    return false;
                }

                if (!confirm("确定下载吗？")) {
                    return false;
                }
            };

            $("input#num_submit").click(function() {
                let length = $("table.num_table").attr("length");
                let min_num = parseInt($("input#min_num").val());
                let max_num = parseInt($("input#max_num").val());

                if (min_num < 0 || min_num > max_num) {
                    alert("最小值超出范围！");
                    $("input#min_num").val(1);
                    return false;
                }
                if (max_num > length) {
                    alert("最大值超出范围！");
                    $("input#max_num").val(length);
                    return false;
                }

                let download_checkbox = document.getElementsByName("download");
                for (let i = 0; i < download_checkbox.length; i++) {
                    if (i >= min_num-1 && i < max_num) {
                        download_checkbox[i].checked = true;
                    } else {
                        download_checkbox[i].checked = false;
                    }
                }
            });
        };
    </script>
</head>
<body>
<div class="main">
    <div class="course_div">
        <p>课程信息</p>
        <table cellspacing="0">
            <tr>
                <td>课程代码</td>
                <td>${sessionScope.course.bvId}</td>
            </tr>
            <tr>
                <td>课程标题</td>
                <td><a href="http://bilibili.com/${sessionScope.course.bvId}">${sessionScope.course.title}</a></td>
            </tr>
            <tr>
                <td>总视频数量</td>
                <td>${sessionScope.course.videos}</td>
            </tr>
            <tr>
                <td>up主</td>
                <td><a href="http://space.bilibili.com/${sessionScope.owner.mid}">${sessionScope.owner.name}</a></td>
            </tr>
            <tr>
                <td>课程介绍</td>
                <td><textarea>${sessionScope.course.intro}</textarea></td>
            </tr>
        </table>
    </div>

    <div class="page_div">
        <p>视频信息</p>
        <table class="num_table" length="${sessionScope.course.pages.size()}">
            <tr>
                <td>快速选择</td>
                <td>
                    <input type="number" id="min_num" name="min" placeholder="最小值" />
                    <span>到</span>
                    <input type="number" id="max_num" name="max" placeholder="最大值" />
                </td>
                <td>
                    <input type="button" id="num_submit" value="确定" />
                </td>
            </tr>
        </table>
        <form action="courseServlet" method="post">
            <input type="hidden" name="action" value="downloadPages">
            <table>
                <tr>
                    <td></td>
                    <td>集数</td>
                    <td>标题</td>
                    <td>时长</td>
                    <td>质量</td>
                    <td>链接</td>
                </tr>
                <c:forEach items="${sessionScope.course.pages}" var="page">
                    <tr>
                        <td><input type="checkbox" name="download" value="${page.value.pageNo}" id=""></td>
                        <td>${page.key}</td>
                        <td>${page.value.part}</td>
                        <td><fmt:formatNumber type="number" pattern="00" value="${page.value.duration/60}"/>:<fmt:formatNumber
                                type="number" pattern="00" value="${page.value.duration%60}"/></td>
                        <td>${page.value.quality}</td>
                        <td><a href="http://www.bilibili.com/${page.value.bvId}?p=${page.key}">视频链接</a></td>
                    </tr>
                </c:forEach>
            </table>
            <table>
                <tr>
                    <td>视频保存路径：</td>
                    <td><input type="text" class="path" id="outPath" name="outPath" /></td>
                </tr>
            </table>
            <input type="submit" value="确定下载" class="download_btn" id="download_btn" />
        </form>
    </div>
</div>
</body>
</html>
