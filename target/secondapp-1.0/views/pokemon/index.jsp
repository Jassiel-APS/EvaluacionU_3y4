<%--
  Created by IntelliJ IDEA.
  User: Equipo MSI
  Date: 6/28/2023
  Time: 11:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Pokemon Index</title>
    <jsp:include page="../../layouts/head.jsp"/>
</head>
<body>
<jsp:include page="../../layouts/navbar.jsp"/>
<!-- NAV NAVBAR -->
<div class="container">
    <div class="row">
        <div class="col">
            <h2 class="mt-3 mb-5">POKEMONS</h2>
            <div class="card">
                <div class="card-header">
                    <div class="row">
                        <div class="col">Listado</div>
                        <div class="col text-end">
                            <button type="button"
                                    data-bs-toggle="modal"
                                    data-bs-target="#createPokemon"
                                    class="btn btn-outline-primary btn-sm"
                            ><i data-feather="plus"></i> AGREGAR
                            </button>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <s:if test="pokemons.isEmpty()">
                        <div class="row">
                            <div class="col">
                                <h3>Sin registros</h3>
                            </div>
                        </div>
                    </s:if>
                    <div class="row row-cols-lg-4 row-cols-md-3 row-cols-sm-1 row-cols-xl-6 gap-4 m-auto">
                        <s:forEach items="${pokemons}" var="pokemon" varStatus="p">
                            <div class="col card h-100" style="width: 18rem;">
                                <img src="/api/pokemon/loadfile?file=${pokemon.id}" class="card-img-top"
                                     alt="${pokemon.name}">
                                <div class="card-body">
                                    <h5 class="card-title">Nombre: <s:out value="${pokemon.name}"/></h5>
                                    <p class="card-text">Persona: <s:out value="${pokemon.person.name}"/> <s:out
                                            value="${pokemon.person.surname}"/><s:out
                                            value="${pokemon.person.lastname}"/></p>
                                    <p class="card-text">Poder: <s:out value="${pokemon.power}"/></p>
                                    <p class="card-text">Puntos de experiencia: <s:out value="${pokemon.ps}"/></p>
                                    <p class="card-text">Puntos de salud: <s:out value="${pokemon.hp}"/></p>
                                    <p class="card-text">Habilidades: <s:out value="${pokemon.abilities}"/></p>
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                            data-bs-target="#updatePokemon"
                                            onclick="editPokemon(${pokemon.id})"
                                            id="editPokemon${pokemon.id}"
                                            data-id="${pokemon.id}" data-name="${pokemon.name}"
                                            data-power="${pokemon.power}"
                                            data-height="${pokemon.height}" data-weight="${pokemon.weight}"
                                            data-hp="${pokemon.hp}"
                                            data-ps="${pokemon.ps}" data-abilities="${pokemon.abilities}"
                                            data-person="${pokemon.person.id}"
                                            data-typepokemon="${pokemon.type.id}">
                                        <i data-feather="edit"></i> EDITAR
                                    </button>
                                </div>
                            </div>
                        </s:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="createPokemon" data-bs-backdrop="static"
     data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="staticBackdropLabel">Registrar pokemon</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col">
                        <form id="pokemonForm" action="/api/pokemon/save" method="POST" novalidate
                              enctype="multipart/form-data">
                            <div class="row form-group mb-3 form-row">
                                <div class="col-4">
                                    <label for="name">Nombre del pokemon</label>
                                    <input type="text" required name="name" id="name" class="form-control"
                                           placeholder="Pikachu"/>
                                    <div class="invalid-feedback">
                                        Campo obligatorii
                                    </div>
                                </div>
                                <div class="col-4">
                                    <label for="type">Tipo de pokemon</label>
                                    <select name="typeId" id="type" class="form-select" required>
                                        <option value="">Seleccione...</option>
                                        <s:forEach var="typee" items="${types}">
                                            <option value="${typee.id}"><s:out
                                                    value="${typee.description}"/></option>
                                        </s:forEach>
                                    </select>
                                    <div class="invalid-feedback">
                                        Campo obligatorii
                                    </div>
                                </div>
                                <div class="col-4">
                                    <label for="belongsTo">Persona a la que pertence</label>
                                    <select name="personId" id="belongsTo" required class="form-select">
                                        <option value="">Seleccione...</option>
                                        <s:forEach var="person" items="${people}">
                                            <option value="${person.id}"><s:out value="${person.name}"/> <s:out
                                                    value="${person.surname}"/> <s:out
                                                    value="${person.lastname}"/></option>
                                        </s:forEach>
                                    </select>
                                    <div class="invalid-feedback">
                                        Campo obligatorii
                                    </div>
                                </div>
                            </div>
                            <div class="row form-row form-group mb-3">
                                <div class="col-4">
                                    <label for="hp">Puntos de salud</label>
                                    <input type="number" required id="hp" name="hp" class="form-control" min="1">
                                    <div class="invalid-feedback">
                                        Campo obligatorio
                                    </div>
                                </div>
                                <div class="col-4">
                                    <label for="ps">Puntos de experiencia</label>
                                    <input type="number" required name="ps" id="ps" class="form-control" min="1">
                                    <div class="invalid-feedback">
                                        Campo obligatorio
                                    </div>
                                </div>
                                <div class="col-4">
                                    <label for="power">Puntos de poder</label>
                                    <input type="number" required id="power" name="power" class="form-control" min="1">
                                    <div class="invalid-feedback">
                                        Campo obligatorio
                                    </div>
                                </div>
                            </div>
                            <div class="row form-row form-group mb-3">
                                <div class="col-6">
                                    <label for="weight">Peso</label>
                                    <input type="number" name="weight" id="weight" min="1" class="form-control"
                                           required>
                                    <div class="invalid-feedback">
                                        Campo obligatorio
                                    </div>
                                </div>
                                <div class="col-6">
                                    <label for="height">Altura</label>
                                    <input type="number" name="height" id="height" min="1" class="form-control"
                                           required>
                                    <div class="invalid-feedback">
                                        Campo obligatorio
                                    </div>
                                </div>
                            </div>
                            <div class="row form-row form-group mb-3">
                                <div class="col-12">
                                    <label for="abilities">Peso</label>
                                    <textarea type="number" rows="3" name="abilities" id="abilities"
                                              class="form-control"
                                              required></textarea>
                                    <div class="invalid-feedback">
                                        Campo obligatorio
                                    </div>
                                </div>
                            </div>
                            <div class="row form-row form-group mb-3">
                                <div class="col-12">
                                    <label for="pokemonImg">Archivo pokemon</label>
                                    <input type="file" class="form-control" onchange="handleFileChange()"
                                           accept="image/*" id="pokemonImg"
                                           name="filePokemon">
                                </div>
                                <div class="col-12 mt-5" id="preview"></div>
                            </div>
                            <div class="row form-row form-group mb-3">
                                <div class="col-12 text-end">
                                    <button type="button" class="btn btn-outline-danger btn-sm me-2"
                                            data-bs-dismiss="modal">
                                        <i data-feather="x"></i> Cancelar
                                    </button>
                                    <button type="button" id="savePokemon" onclick="sendForm()"
                                            class="btn btn-outline-success btn-sm">
                                        <i data-feather="check"></i> Aceptar
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="updatePokemon" data-bs-backdrop="static"
     data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="updateh1">Registrar pokemon</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col">
                        <form id="updatePokemonForm" action="/api/pokemon/update" method="POST" novalidate
                              enctype="multipart/form-data">
                            <div class="row form-group mb-3 form-row">
                                <input hidden value="" id="upId" name="id">
                                <div class="col-4">
                                    <label for="name">Nombre del pokemon</label>
                                    <input type="text" required name="name" id="upname" class="form-control"
                                           placeholder="Pikachu"/>
                                    <div class="invalid-feedback">
                                        Campo obligatorii
                                    </div>
                                </div>
                                <div class="col-4">
                                    <label for="type">Tipo de pokemon</label>
                                    <select name="typeId" id="uptype" class="form-select" required>
                                        <option value="">Seleccione...</option>
                                        <s:forEach var="typee" items="${types}">
                                            <option value="${typee.id}"><s:out
                                                    value="${typee.description}"/></option>
                                        </s:forEach>
                                    </select>
                                    <div class="invalid-feedback">
                                        Campo obligatorii
                                    </div>
                                </div>
                                <div class="col-4">
                                    <label for="belongsTo">Persona a la que pertence</label>
                                    <select name="personId" id="upbelongsTo" required class="form-select">
                                        <option value="">Seleccione...</option>
                                        <s:forEach var="person" items="${people}">
                                            <option value="${person.id}"><s:out value="${person.name}"/> <s:out
                                                    value="${person.surname}"/> <s:out
                                                    value="${person.lastname}"/></option>
                                        </s:forEach>
                                    </select>
                                    <div class="invalid-feedback">
                                        Campo obligatorii
                                    </div>
                                </div>
                            </div>
                            <div class="row form-row form-group mb-3">
                                <div class="col-4">
                                    <label for="hp">Puntos de salud</label>
                                    <input type="number" required id="uphp" name="hp" class="form-control" min="1">
                                    <div class="invalid-feedback">
                                        Campo obligatorio
                                    </div>
                                </div>
                                <div class="col-4">
                                    <label for="ps">Puntos de experiencia</label>
                                    <input type="number" required name="ps" id="upps" class="form-control" min="1">
                                    <div class="invalid-feedback">
                                        Campo obligatorio
                                    </div>
                                </div>
                                <div class="col-4">
                                    <label for="power">Puntos de poder</label>
                                    <input type="number" required id="uppower" name="power" class="form-control"
                                           min="1">
                                    <div class="invalid-feedback">
                                        Campo obligatorio
                                    </div>
                                </div>
                            </div>
                            <div class="row form-row form-group mb-3">
                                <div class="col-6">
                                    <label for="weight">Peso</label>
                                    <input type="number" name="weight" id="upweight" min="1" class="form-control"
                                           required>
                                    <div class="invalid-feedback">
                                        Campo obligatorio
                                    </div>
                                </div>
                                <div class="col-6">
                                    <label for="height">Altura</label>
                                    <input type="number" name="height" id="upheight" min="1" class="form-control"
                                           required>
                                    <div class="invalid-feedback">
                                        Campo obligatorio
                                    </div>
                                </div>
                            </div>
                            <div class="row form-row form-group mb-3">
                                <div class="col-12">
                                    <label for="abilities">Peso</label>
                                    <textarea type="number" rows="3" name="abilities" id="upabilities"
                                              class="form-control"
                                              required></textarea>
                                    <div class="invalid-feedback">
                                        Campo obligatorio
                                    </div>
                                </div>
                            </div>
                            <div class="row form-row form-group mb-3">
                                <div class="col-12">
                                    <label for="pokemonImg">Archivo pokemon</label>
                                    <input type="file" class="form-control" onchange="upHandleFileChange()"
                                           accept="image/*" id="uppokemonImg"
                                           name="filePokemon">
                                </div>
                                <div class="col-12 mt-5" id="uppreview"></div>
                            </div>
                            <div class="row form-row form-group mb-3">
                                <div class="col-12 text-end">
                                    <button type="button" class="btn btn-outline-danger btn-sm me-2"
                                            data-bs-dismiss="modal">
                                        <i data-feather="x"></i> Cancelar
                                    </button>
                                    <button type="button" id="updatePokemonBtn" onclick="upSendForm()"
                                            class="btn btn-outline-success btn-sm">
                                        <i data-feather="check"></i> Aceptar
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../layouts/footer.jsp"/>
<script>
    const form = document.getElementById("pokemonForm");
    const form2 = document.getElementById("updatePokemonForm");
    const btn = document.getElementById("savePokemon");
    const btn2 = document.getElementById("updatePokemonBtn");
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
    const upSendForm = () => {
        Swal.fire({
            title: '¿Seguro de realizar la acción?',
            text: "Favor de esperar a que terminae la acción...",
            icon: 'warning',
            showCancelButton: true,
            cancelButtonText: 'Cancelar',
            confirmButtonText: 'Aceptar'
        }).then((result) => {
            if (result.isConfirmed) {
                form2.classList.add("was-validated");
                if (!form2.checkValidity()) {
                    btn2.classList.remove("disabled");
                    btn2.innerHTML = `<i data-feather="times"></i> Aceptar`;
                } else {
                    btn2.innerHTML = `<div class="d-flex justify-content-center spinner-border-sm">
                                <div class="spinner-border" role="status">
                                    <span class="visually-hidden">Loading...</span>
                                </div>
                            </div>`;
                    btn2.classList.add("disabled");
                    form2.submit();
                }
            }
        })
    }
    const handleFileChange = () => {
        const inputFile = document.getElementById("pokemonImg").files;
        let preview = document.getElementById("preview");
        preview.innerHTML = "";
        for (let i = 0; i < inputFile.length; i++) {
            let reader = new FileReader();
            reader.onloadend = (result) => {
                preview.innerHTML = "<img src='" + result.target.result
                    + "' style='height: 200px;width: auto;'/>";
            }
            reader.readAsDataURL(inputFile[i]);
        }
    }
    const upHandleFileChange = () => {
        const inputFile = document.getElementById("uppokemonImg").files;
        let uppreview = document.getElementById("uppreview");
        uppreview.innerHTML = "";
        for (let i = 0; i < inputFile.length; i++) {
            let reader = new FileReader();
            reader.onloadend = (result) => {
                uppreview.innerHTML = "<img src='" + result.target.result
                    + "' style='height: 200px;width: auto;'/>";
            }
            reader.readAsDataURL(inputFile[i]);
        }
    }
    const editPokemon = (idPokemon) => {
        const btn = document.getElementById("editPokemon" + idPokemon)
        const id = btn.dataset.id;
        const name = btn.dataset.name;
        const power = btn.dataset.power;
        const ps = btn.dataset.ps;
        const hp = btn.dataset.hp;
        const height = btn.dataset.height;
        const weight = btn.dataset.weight;
        const abilities = btn.dataset.abilities;
        const person = btn.dataset.person;
        const typePokemon = btn.dataset.typepokemon;
        document.getElementById("upId").value = id;
        document.getElementById("upname").value = name;
        document.getElementById("upheight").value = height;
        document.getElementById("uppower").value = power;
        document.getElementById("upweight").value = weight;
        document.getElementById("uphp").value = hp;
        document.getElementById("upps").value = ps;
        document.getElementById("upabilities").value = abilities;
        document.getElementById("upbelongsTo").value = person;
        document.getElementById("uptype").value = typePokemon;
    }
    window.addEventListener("DOMContentLoaded", () => {
        feather.replace();
        if (!${param['result']==false?param['result']:true}) {
            Swal.fire({
                title: 'Información...',
                text: '${param['message']}',
                icon: '${param['result']}?"success":"error"',
                confirmButtonText: 'Aceptar'
            });
        }
        document.getElementById("loaderDiv").style.display = "none";
    }, false);
</script>
</body>
</html>
