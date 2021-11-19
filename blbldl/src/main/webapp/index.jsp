<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="jsp/header.jsp" %>
<!DOCTYPE>
<html lang="zh">
<head>
    <title>B L B L D L - 首页</title>

    <style type="text/css">
        * {
            margin: 0 auto;
            padding: 0;
        }
        .search_div {
            width: 100%;
            height: 500px;
            background-color: rgba(0, 0, 0, 0.7);
            text-align: center;
            vertical-align: center;
            padding-top: 20px;
        }

        .search_form input[type="text"] {
            width: 550px;
            height: 30px;
            line-height: 30px;
            padding-left: 5px;
            outline: none;
        }
        .search_form input[type="submit"] {
            width: 85px;
            height: 30px;
            line-height: 26px;
            font-family: cursive;
            font-size: 17px;
            font-weight: 800;
            margin-left: 10px;
            border-radius: 4px;
        }
        #msg {
            font-size: 20px;
            margin-top: 165px;
            color: antiquewhite;
        }
    </style>

    <script type="text/javascript">
        window.onload = function () {
            document.getElementById("search_submit").onclick = function () {
                let value = document.getElementById("url").value
                if (value == null || value.trim() == "") {
                    alert("请输入视频链接！");
                    return false;
                } else {
                    document.getElementById("msg").innerText = "正在爬取数据，大概需要1~2分钟时间，请耐心等候!";
                }
            };
        };
    </script>
</head>

<body>
<div class="search_div">
    <form action="courseServlet" method="post" class="search_form">
        <input type="hidden" name="action" value="searchCourse">
        <table>
            <tr>
                <td><input type="text" name="url" id="url" placeholder="请输入视频链接" /></td>
                <td><input type="submit" id="search_submit" value="查询"></td>
            </tr>
        </table>
    </form>

    <div id="msg"></div>
</div>
</body>
</html>
