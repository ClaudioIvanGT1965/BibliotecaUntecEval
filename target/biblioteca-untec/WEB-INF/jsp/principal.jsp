<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="es">

<head>

<meta charset="UTF-8">

<title>Biblioteca Digital Untec</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/estilos.css">

</head>

<body class="pagina-catalogo">

<header class="catalogo-header">

<div class="catalogo-marca">

    <div class="icono-libro">
        <span></span>
        <span></span>
    </div>

    <div class="marca-texto">
        <strong>UNTEC</strong>
        <small>BIBLIOTECA DIGITAL</small>
    </div>

</div>


<div class="catalogo-usuario">

    <div class="datos-usuario">

        <span class="usuario-label">
            USUARIO
        </span>

        <strong>
            ${usuario.username}
        </strong>

        <span class="separador">
            |
        </span>

        <span class="usuario-label">
            CATEGORÍA
        </span>

        <strong>
            ${usuario.categoria}
        </strong>

    </div>


    <c:if test="${sessionScope.usuario.categoria == 'BIBLIOTECARIO'}">

        <button type="button"
                class="boton-nuevo"
                onclick="window.location.href='${pageContext.request.contextPath}/nuevoLibro'">

            + INGRESAR NUEVO

        </button>

    </c:if>

</div>

</header>

<main class="catalogo-contenedor">

<!-- Título -->

<section class="cabecera-catalogo">

    <div>

        <p class="catalogo-etiqueta">
            SISTEMA DE GESTIÓN BIBLIOGRÁFICA
        </p>

        <h1>
            Catálogo de libros
        </h1>

        <p class="catalogo-descripcion">
            Consulte y gestione los ejemplares disponibles en la biblioteca.
        </p>

    </div>

</section>


<!-- Filtros -->

<section class="panel-filtros">

    <div class="titulo-filtros">

        <span class="icono-filtro">
            FILTROS
        </span>

        <span>
            Utilice los filtros para localizar un libro.
        </span>

    </div>


    <form action="${pageContext.request.contextPath}/catalogo"
          method="get"
          class="formulario-filtros">


        <div class="filtro">

            <label for="area">
                Área
            </label>

            <select id="area"
                    name="area">

                <option value="">
                    Todas las áreas
                </option>

                <c:forEach var="area"
                           items="${areas}">

                    <option value="${area.idArea}"
                            <c:if test="${param.area == area.idArea}">
                                selected
                            </c:if>>

                        ${area.descripcion}

                    </option>

                </c:forEach>

            </select>

        </div>


        <div class="filtro">

            <label for="autor">
                Autor
            </label>

            <select id="autor"
                    name="autor">

                <option value="">
                    Todos los autores
                </option>

                <c:forEach var="autor"
                           items="${autores}">

                    <option value="${autor.idAutor}"
                            <c:if test="${param.autor == autor.idAutor}">
                                selected
                            </c:if>>

                        ${autor.nombreAutor}

                    </option>

                </c:forEach>

            </select>

        </div>


        <button type="submit"
                class="boton-filtrar">

            FILTRAR

        </button>
    <div class="contenedor-mis-prestamos">

    <c:choose>

        <c:when test="${soloMisPrestamos}">

            <a href="${pageContext.request.contextPath}/catalogo"
               class="boton-mis-prestamos boton-mis-prestamos-activo">

                VER CATÁLOGO

            </a>

        </c:when>

        <c:otherwise>

            <button type="submit"
                    name="misPrestamos"
                    value="true"
                    class="boton-mis-prestamos">

                MIS PRÉSTAMOS

            </button>

        </c:otherwise>

    </c:choose>

</div>


    </form>

</section>


<!-- Tabla -->

<section class="panel-catalogo">

    <div class="tabla-titulo">

        <div>

            <strong>
                Libros registrados
            </strong>

            <span>
                Catálogo disponible
            </span>

        </div>

    </div>


    <div class="tabla-contenedor">

        <table>

            <thead>

                <tr>

                    <th>ID</th>

                    <th>Título</th>

                    <th>Autor</th>

                    <th>Código interno</th>

                    <th>Stock disponible</th>

                    <th>Acción</th>

                </tr>

            </thead>


            <tbody>
                <c:if test="${soloMisPrestamos && empty catalogo}">

    <tr>

        <td colspan="6"
            style="text-align: center; padding: 35px;">

            No tienes préstamos activos en este momento.

        </td>

    </tr>

</c:if>
                <c:forEach var="libro"
                           items="${catalogo}">

                    <tr>

                        <td class="columna-id">
                            ${libro.id}
                        </td>


                        <td class="columna-titulo">

                            <strong>
                                ${libro.titulo}
                            </strong>

                        </td>


                        <td>
                            ${libro.autor.nombreAutor}
                        </td>


                        <td>

                            <span class="codigo">
                                ${libro.codigoInterno}
                            </span>

                        </td>


                        <td class="columna-stock">

                            <span class="stock">
                                ${libro.stockDisponible}
                            </span>

                        </td>


                        <td class="columna-acciones">


                            <c:choose>

                                <c:when test="${libro.prestamoActivo}">

                                    <form action="${pageContext.request.contextPath}/prestamo"
                                          method="post"
                                          class="form-accion">

                                        <input type="hidden"
                                               name="idLibro"
                                               value="${libro.id}">

                                        <input type="hidden"
                                               name="accion"
                                               value="DEVOLVER">

                                        <button type="submit"
                                                class="boton-accion boton-devolver">

                                            DEVOLVER

                                        </button>

                                    </form>

                                </c:when>


                                <c:otherwise>

                                    <form action="${pageContext.request.contextPath}/prestamo"
                                          method="post"
                                          class="form-accion">

                                        <input type="hidden"
                                               name="idLibro"
                                               value="${libro.id}">

                                        <input type="hidden"
                                               name="accion"
                                               value="SOLICITAR">

                                        <button type="submit"
                                                class="boton-accion boton-solicitar">

                                            SOLICITAR

                                        </button>

                                    </form>

                                </c:otherwise>

                            </c:choose>


                            <c:if test="${sessionScope.usuario.categoria == 'BIBLIOTECARIO'}">

                                <form action="${pageContext.request.contextPath}/editarLibro"
                                      method="get"
                                      class="form-accion">

                                    <input type="hidden"
                                           name="id"
                                           value="${libro.id}">

                                    <button type="submit"
                                            class="boton-accion boton-editar">

                                        EDITAR

                                    </button>

                                </form>

                            </c:if>


                        </td>

                    </tr>

                </c:forEach>

            </tbody>

        </table>

    </div>

</section>


<footer class="catalogo-footer">

    Biblioteca Digital Untec
    <span>•</span>
    Sistema Evaluación Módulo 5 Claudio Gutiérrez

</footer>

</main>

</body>

</html>
