<%--
  Created by IntelliJ IDEA.
  User: Equipo MSI
  Date: 7/25/2023
  Time: 6:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registrar usuario</title>
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
                        <div class="col">Formulario</div>
                    </div>
                </div>
                <div class="card-body">
                    <form id="userForm" action="/api/people/save" novalidate method="post" class="needs-validation">
                        <div class="form-group row form-row mb-3">
                            <div class="col">
                                <label class="fw-bold" for="name">Nombre</label>
                                <input type="text" class="form-control" id="name" name="name" required>
                            </div>
                            <div class="col">
                                <label class="fw-bold" for="surname">Primer apellido</label>
                                <input type="text" class="form-control" id="surname" name="surname" required>
                            </div>
                            <div class="col">
                                <label class="fw-bold" for="lastname">Segundo apellido</label>
                                <input type="text" class="form-control" id="lastname" name="lastname" required>
                            </div>
                        </div>
                        <div class="form-group row form-row mb-3">
                            <div class="col">
                                <label class="fw-bold" for="curp">CURP</label>
                                <input type="text" class="form-control" id="curp" name="curp" required>
                            </div>
                            <div class="col">
                                <label class="fw-bold" for="birthday">Fecha de nacimiento</label>
                                <input type="date" class="form-control" id="birthday" name="birthday" required>
                            </div>
                        </div>
                        <div class="form-group row form-row mb-3">
                            <div class="col">
                                <label class="fw-bold" for="username">Nombre de usuario</label>
                                <input type="text" class="form-control" id="username" name="username" required>
                            </div>
                            <div class="col">
                                <label class="fw-bold" for="password">Contraseña</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <div class="col">
                                <label class="fw-bold" for="role">Role</label>
                                <select id="role" name="role" class="form-select" required>
                                    <option value="">Seleccionar...</option>
                                    <c:forEach var="role" items="${roles}">
                                        <option value="${role.id}"><c:out
                                                value="${role.description}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row form-row">
                            <div class="col text-end">
                                <a class="btn btn-outline-danger btn-sm me-2" href="/api/people/all"><i
                                        data-feather="x"></i> CANCELAR</a>
                                <button id="userFormBtn" onclick="sendForm()" type="button" class="btn btn-outline-success btn-sm"><i
                                        data-feather="check"></i> ACEPTAR
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../layouts/footer.jsp"/>
<script>
    const form = document.getElementById("userForm");
    const btn = document.getElementById("userFormBtn");
    const sendForm = () => {
        Swal.fire({
            title: '¿Seguro de realizar la acción?',
            text: "Favor de esperar a que terminae la acción...",
            icon: 'warning',
            showCancelButton: true,
            cancelButtonText: 'Cancelar',
            confirmButtonText: 'Aceptar'
        }).then((result) => {
            if (result.isConfirmed) {
                form.classList.add("was-validated");
                if (!form.checkValidity()) {
                    btn.classList.remove("disabled");
                    btn.innerHTML = `<i data-feather="times"></i> Aceptar`;
                } else {
                    btn.innerHTML = `<div class="d-flex justify-content-center spinner-border-sm">
                                <div class="spinner-border" role="status">
                                    <span class="visually-hidden">Loading...</span>
                                </div>
                            </div>`;
                    btn.classList.add("disabled");
                    form.submit();
                }
            }
        })
    }
</script>
</body>
</html>
