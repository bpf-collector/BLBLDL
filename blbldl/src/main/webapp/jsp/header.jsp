<%@ page contentType="text/html;charset=UTF-8" %>
<%-- 配置动态的base标签 --%>
<%
    String basePath = request.getScheme()
        + "://" + request.getServerName()
        + ":" + request.getServerPort()
        + request.getContextPath() + "/";
    pageContext.setAttribute("basePath", basePath);
%>

<base href="<%=basePath%>">
