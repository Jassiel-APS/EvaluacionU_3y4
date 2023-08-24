
  Created by IntelliJ IDEA.
  User: Equipo MSI
  Date: 7/25/2023
  Time: 6:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>PK | USUARIOS</title>
    <jsp:include page="../../layouts/head.jsp"/>
</head>
<body>
<jsp:include page="../../layouts/navbar.jsp"/>
<div class="container">
    <div class="row">
        <div class="col">
            <h2 class="mt-3 mb-5">USUARIOS</h2>
            <div class="card">
                <div class="card-header">
                    <div class="row">
                        <div class="col">LISTADO</div>
                        <div class="col text-end">
                            <a href="/api/people/create" class="btn btn-outline-primary btn-sm">
                                <i data-feather="plus"></i> AGREGAR
                            </a>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <s:if test="users.isEmpty()">
                        <div class="row">
                            <div class="col">
                                <h3>Sin registros</h3>
                            </div>
                        </div>
                    </s:if>
                    <table class="table table-striped table-hover datatable" id="datatable">
                        <thead class="table-dark">
                        <th>No.</th>
                        <th>Usuario</th>
                        <th>Persona</th>
                        <th>CURP</th>
                        <th>Role</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                        </thead>
                        <tbody>
                        <s:forEach items="${users}" varStatus="s" var="user">
                            <tr>
                                <td><s:out value="${s.count}"/></td>
                                <td><s:out value="${user.username}"/></td>
                                <td><s:out value="${user.person.name}"/> <s:out value="${user.person.surname}"/> <s:out
                                        value="${user.person.lastname}"/></td>
                                <td><s:out value="${user.person.curp}"/></td>
                                <td><s:out value="${user.role.description}"/></td>
                                <td><s:out value="${user.person.status}"/></td>
                                <td>
                                    <div class="row">
                                        <div class="col">
                                            <form action="/api/people/edit" method="get">
                                                <input hidden name="id" value="${user.id}">
                                                <button type="submit" class="btn btn-outline-warning btn-sm">
                                                    <i data-feather="edit"></i> EDITAR
                                                </button>
                                            </form>
                                        </div>
                                        <div class="col">
                                            <form action="/api/people/enable" method="post">
                                                <input hidden name="id" value="${user.id}">
                                                <input hidden name="personId" value="${user.person.id}">
                                                <s:if test="${user.person.status == \"INACTIVO\"}">
                                                    <button type="submit" class="btn btn-outline-success btn-sm">
                                                        <i data-feather="edit"></i> ACTIVAR
                                                    </button>
                                                </s:if>
                                                <s:if test="${user.person.status != \"INACTIVO\"}">
                                                    <button type="submit" class="btn btn-outline-danger btn-sm">
                                                        <i data-feather="edit"></i> DESACTIVAR
                                                    </button>
                                                </s:if>
                                            </form>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </s:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../layouts/footer.jsp"/>
<script>
    window.addEventListener("DOMContentLoaded", () => {
        if (!${param['result']==false?param['result']:true}) {
            Swal.fire({
                title: 'Información...',
                text: '${param['message']}',
                icon: '${param['result']==false ? 'success':'error'}',
                confirmButtonText: 'Aceptar'
            });
        }
        if (!${param['result']==false?param['result']:true}) {
            Swal.fire({
                title: 'Información...',
                text: '${param['message']}',
                icon: 'success',
                confirmButtonText: 'Aceptar'
            });
        }
        if (${param['result']==true?param['result']:false}) {
            Swal.fire({
                title: 'Información...',
                text: '${param['message']}',
                icon: 'error',
                confirmButtonText: 'Aceptar'
            });
        }
    }, false);
</script>
</body>
</html>
