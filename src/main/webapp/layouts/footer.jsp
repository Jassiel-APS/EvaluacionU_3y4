<%--
  Created by IntelliJ IDEA.
  User: Equipo MSI
  Date: 6/16/2023
  Time: 9:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/sweetalert2.all.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/feather-icons.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/datatable.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/datatable-bootstrap.min.js"></script>
<script>
    feather.replace();
    $(document).ready(() => {
        $('.datatable').DataTable({
            language: {
                url: 'https://cdn.datatables.net/plug-ins/1.11.5/i18n/es-MX.json',
            },
        });
        document.getElementById("loaderDiv").style.display = "none";
    })
</script>