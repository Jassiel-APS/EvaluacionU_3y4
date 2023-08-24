<%--
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
    <title>Tipos pokémon</title>
    <jsp:include page="../../layouts/head.jsp"/>
</head>
<body>
<jsp:include page="../../layouts/navbar.jsp"/>
<div class="container">
    <div class="row">
        <div class="col">
            <h2 class="mt-3 mb-5">POKEMONS</h2>
            <div class="card">
                <div class="card-header">
                    <div class="row">
                        <div class="col">Listado</div>
                        <div class="col text-end">
                            <a href="/api/type/create" class="btn btn-outline-primary btn-sm">
                                <i data-feather="plus"></i> AGREGAR
                            </a>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <s:if test="types.isEmpty()">
                        <div class="row">
                            <div class="col">
                                <h3>Sin registros</h3>
                            </div>
                        </div>
                    </s:if>
                    <table class="table table-striped table-hover datatable" id="datatable">
                        <thead class="table-dark">
                        <th>No.</th>
                        <th>Descripción</th>
                        <th>Acciones</th>
                        </thead>
                        <tbody>
                        <s:forEach items="${types}" varStatus="s" var="type">
                            <tr>
                                <td><s:out value="${s.count}"/></td>
                                <td><s:out value="${type.description}"/></td>
                                <td>
                                    <form action="/api/type/edit" method="get">
                                        <input hidden name="id" value="${type.id}">
                                        <button type="submit" class="btn btn-outline-warning btn-sm">
                                            <i data-feather="edit"></i> EDITAR
                                        </button>
                                    </form>
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
</body>
</html>
