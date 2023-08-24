<%--
  Created by IntelliJ IDEA.
  User: Equipo MSI
  Date: 6/16/2023
  Time: 9:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/sweetalert2.min.css" type="text/css">
<style>
    #loaderDiv {
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        z-index: 1100;
        align-items: center;
        justify-content: center;
        background-color: white;
        display: flex;
        opacity: 1;
    }
</style>

<div id="loaderDiv" loader>
    <div class="spinner-border" style="width: 5rem; height: 5rem;" role="status"></div>
</div>