<!DOCTYPE>
<html lang="zh">
<head>
    <title>B L B L D L - 搜索结果</title>
    <#include "head.ftl">

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
</head>
<body>
<div class="main">
    <div class="course_div">
        <p>课程信息</p>
        <table cellspacing="0">
            <tr>
                <td>课程代码</td>
                <td id="bvid">${course.bvid}</td>
            </tr>
            <tr>
                <td>课程标题</td>
                <td><a href="">${course.title}</a></td>
            </tr>
            <tr>
                <td>总视频数量</td>
                <td>${course.videos}</td>
            </tr>
            <tr>
                <td>up主</td>
                <td><a href="http://space.bilibili.com/${course.owner.mid}">${course.owner.name}</a></td>
            </tr>
            <tr>
                <td>课程介绍</td>
                <td><textarea>${course.intro}</textarea></td>
            </tr>
        </table>
    </div>


    <div class="page_div">
        <p>视频信息</p>
        <input type="hidden" name="action" value="downloadPages">
        <table>
            <tr>
                <td></td>
                <td>集数</td>
                <td>标题</td>
                <td>时长</td>
                <td>链接</td>
                <td><a href="javascript:void(0)" type="button" onclick="load_urls()">加载所有链接</a></td>
            </tr>
            <#list course.pageList as page>
                <tr>
                    <td><input type="checkbox" name="download" value="${page.pageno}" id=""></td>
                    <td>${page.pageno}</td>
                    <td>${page.part}</td>
                    <td>${page.duration}</td>
                    <td><a target="_blank" href="http://www.bilibili.com/${page.bvid}?p=${page.pageno}">视频链接</a></td>
                    <td><a href="${ctx}/course/url?bvid=${course.bvid}&pageno=${page.pageno}" type="button">👇</a></td>
                </tr>
            </#list>
        </table>
    </div>
</div>
</body>
</html>
