<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="es">

<head>

```
<meta charset="UTF-8">

<title>Nuevo Libro - Biblioteca Digital Untec</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/estilos.css">
```

</head>

<body class="pagina-formulario">

<main class="formulario-contenedor">

```
<section class="tarjeta-formulario">

    <div class="formulario-franja"></div>


    <!-- CABECERA -->

    <div class="cabecera-formulario">

        <div class="formulario-marca">

            <div class="icono-libro">
                <span></span>
                <span></span>
            </div>

            <div class="marca-texto">

                <strong>UNTEC</strong>

                <small>
                    BIBLIOTECA DIGITAL
                </small>

            </div>

        </div>


        <p class="formulario-etiqueta">
            GESTIÓN DEL CATÁLOGO
        </p>

        <h1>
            Ingreso de nuevo libro
        </h1>

        <p class="formulario-descripcion">
            Registre los datos bibliográficos del nuevo ejemplar.
        </p>

    </div>


    <!-- FORMULARIO -->

    <form action="${pageContext.request.contextPath}/nuevoLibro"
          method="post"
          class="formulario-libro">


        <!-- TÍTULO -->

        <div class="campo-formulario campo-completo">

            <label for="titulo">
                Título
            </label>

            <input type="text"
                   id="titulo"
                   name="titulo"
                   maxlength="150"
                   required>

        </div>


        <!-- AUTOR / CATEGORÍA -->

        <div class="fila-formulario">

            <div class="campo-formulario">

                <label for="autor">
                    Autor
                </label>

                <select id="autor"
                        name="autor"
                        required>

                    <option value="">
                        -- Seleccione un autor --
                    </option>

                    <c:forEach var="autor"
                               items="${autores}">

                        <option value="${autor.idAutor}">
                            ${autor.nombreAutor}
                        </option>

                    </c:forEach>

                </select>

            </div>


            <div class="campo-formulario">

                <label for="categoria">
                    Categoría
                </label>

                <input type="text"
                       id="categoria"
                       name="categoria"
                       maxlength="10"
                       required>

            </div>

        </div>


        <!-- CÓDIGOS -->

        <div class="fila-formulario">

            <div class="campo-formulario">

                <label for="codigoInterno">
                    Código interno
                </label>

                <input type="text"
                       id="codigoInterno"
                       name="codigoInterno"
                       maxlength="20"
                       required>

            </div>


            <div class="campo-formulario">

                <label for="codigoLocalizacion">
                    Código de localización
                </label>

                <input type="text"
                       id="codigoLocalizacion"
                       name="codigoLocalizacion"
                       maxlength="40">

            </div>

        </div>


        <!-- ÁREA / STOCK -->

        <div class="fila-formulario">

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

                        <option value="${area.idArea}">
                            ${area.descripcion}
                        </option>

                    </c:forEach>

                </select>

            </div>


            <div class="campo-formulario">

                <label for="stockTotal">
                    Stock total
                </label>

                <input type="number"
                       id="stockTotal"
                       name="stockTotal"
                       min="0"
                       value="1"
                       required>

                <small class="ayuda-campo">
                    El stock disponible se establecerá automáticamente.
                </small>

            </div>

        </div>


        <!-- EDITORIAL -->

        <div class="campo-formulario campo-completo">

            <label for="editorial">
                Editorial
            </label>

            <input type="text"
                   id="editorial"
                   name="editorial"
                   maxlength="50">

        </div>


        <!-- BOTONES -->

        <div class="botones-formulario">

            <button type="button"
                    class="boton-cancelar"
                    onclick="window.location.href='${pageContext.request.contextPath}/catalogo'">

                CANCELAR

            </button>


            <button type="submit"
                    class="boton-grabar">

                GRABAR

            </button>

        </div>


    </form>


    <!-- PIE -->

    <div class="formulario-pie">

        Biblioteca Digital Untec
        <span>•</span>
        Sistema Evaluación Módulo 5 Claudio Gutiérrez

    </div>

</section>
```

</main>

</body>

</html>
