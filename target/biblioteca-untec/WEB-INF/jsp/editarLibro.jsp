<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Editar Libro - Biblioteca Digital Untec</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/estilos.css">
</head>

<body class="pagina-formulario">

<main class="formulario-contenedor">

    <section class="tarjeta-formulario">

        <!-- Franja superior -->
        <div class="formulario-franja"></div>

        <!-- Encabezado -->
        <div class="cabecera-formulario">

            <div class="formulario-marca">

                <div class="icono-libro">
                    <span></span>
                    <span></span>
                </div>

                <div class="marca-texto">
                    <strong>UNTEC</strong>
                    <small>BIBLIOTECA DIGITAL</small>
                </div>

            </div>

            <p class="formulario-etiqueta">
                GESTIÓN DEL CATÁLOGO
            </p>

            <h1>Edición de libro</h1>

            <p class="formulario-descripcion">
                Modifique los datos administrativos del ejemplar seleccionado.
            </p>

        </div>

        <c:if test="${not empty mensaje}">
        <div class="mensaje-error">
        ${mensaje}
        </div>
</c:if>
        <!-- Formulario -->
        <form action="${pageContext.request.contextPath}/editarLibro"
              method="post"
              class="formulario-libro">


            <!-- ID -->
            <div class="fila-formulario">

                <div class="campo-formulario">
                    <label for="id">ID</label>

                    <input type="text"
                           id="id"
                           value="${libro.id}"
                           disabled>

                    <input type="hidden"
                           name="id"
                           value="${libro.id}">
                </div>


                <!-- Código interno -->
                <div class="campo-formulario">
                    <label for="codigoInterno">
                        Código interno
                    </label>

                    <input type="text"
                           id="codigoInterno"
                           value="${libro.codigoInterno}"
                           disabled>
                </div>

            </div>


            <!-- Título -->
            <div class="campo-formulario campo-completo">

                <label for="titulo">
                    Título
                </label>

                <input type="text"
                       id="titulo"
                       value="${libro.titulo}"
                       disabled>

            </div>


            <!-- Autor / Categoría -->
            <div class="fila-formulario">

                <div class="campo-formulario">

                    <label for="autor">
                        Autor
                    </label>

                    <input type="text"
                           id="autor"
                           value="${libro.autor.nombreAutor}"
                           disabled>

                </div>


                <div class="campo-formulario">

                    <label for="categoria">
                        Categoría
                    </label>

                    <input type="text"
                           id="categoria"
                           name="categoria"
                           maxlength="10"
                           value="${libro.categoria}"
                           required>

                </div>

            </div>


            <!-- Localización / Área -->
            <div class="fila-formulario">

                <div class="campo-formulario">

                    <label for="codigoLocalizacion">
                        Código de localización
                    </label>

                    <input type="text"
                           id="codigoLocalizacion"
                           name="codigoLocalizacion"
                           maxlength="40"
                           value="${libro.codigoLocalizacion}">

                </div>


                <div class="campo-formulario">

                    <label for="area">
                        Área
                    </label>

                    <select id="area"
                            name="area">

                        <option value="0">
                            -- Sin área --
                        </option>

                        <c:forEach var="area"
                                   items="${areas}">

                            <option value="${area.idArea}"
                                <c:if test="${libro.area != null &&
                                             libro.area.idArea == area.idArea}">
                                    selected
                                </c:if>>
                                ${area.descripcion}
                            </option>

                        </c:forEach>

                    </select>

                </div>

            </div>


            <!-- Stock / Editorial -->
            <div class="fila-formulario">

                <div class="campo-formulario">

                    <label for="stockTotal">
                        Stock total
                    </label>

                    <input type="number"
                           id="stockTotal"
                           name="stockTotal"
                           min="0"
                           value="${libro.stockTotal}"
                           required>

                    <small class="ayuda-campo">
                        El stock disponible se ajustará según la situación
                        de los préstamos existentes.
                    </small>

                </div>


                <div class="campo-formulario">

                    <label for="editorial">
                        Editorial
                    </label>

                    <input type="text"
                           id="editorial"
                           name="editorial"
                           maxlength="50"
                           value="${libro.editorial}">

                </div>

            </div>


            <!-- Botones -->
          <div class="botones-formulario">

    <button type="button"
            class="boton-cancelar"
            onclick="window.location.href='${pageContext.request.contextPath}/catalogo'">
        CANCELAR
    </button>

    <button type="submit"
        name="accion"
        value="ELIMINAR"
        class="boton-eliminar"
        formnovalidate
        onclick="return confirm('¿Está seguro de que desea eliminar este libro?');">
        ELIMINAR
</button>

    <button type="submit"
            name="accion"
            value="GRABAR"
            class="boton-grabar">
        GRABAR
    </button>

</div>

        </form>


        <!-- Pie -->
        <div class="formulario-pie">

            Biblioteca Digital Untec

            <span>•</span>

            Sistema Evaluación Módulo 5 Claudio Gutiérrez

        </div>

    </section>

</main>

</body>
</html>

