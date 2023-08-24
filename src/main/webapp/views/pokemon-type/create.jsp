<%--
  Created by IntelliJ IDEA.
  User: Equipo MSI
  Date: 7/25/2023
  Time: 6:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Registrar nuevo tipo</title>
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
                    </div>
                </div>
                <div class="card-body">
                    <form id="typeForm" action="/api/type/save" method="post" class="needs-validation" novalidate>
                        <div class="row form-row form-group">
                            <div class="col mb-3">
                                <label for="description" class="fw-bold">Descripción</label>
                                <input type="text" class="form-control" name="description" id="description"/>
                            </div>
                        </div>
                        <div class="row form-row form-group mb-3">
                            <div class="col text-end">
                                <a href="/api/type/all" class="btn btn-outline-danger btn-sm">
                                    <i data-feather="x"></i> CANCELAR
                                </a>
                                <button id="btnTypeForm" onclick="sendForm()" type="button"
                                        class="btn btn-outline-success btn-sm">
                                    <i data-feather="check"></i> REGISTRAR
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
    $(document).ready(() => {
        document.getElementById("loaderDiv").style.display = "none";
    });

    const form = document.getElementById("typeForm");
    const btn = document.getElementById("btnTypeForm");
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

    feather.replace();
</script>
</body>
</html>
