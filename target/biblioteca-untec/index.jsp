<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <title>Biblioteca Digital Untec</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/estilos.css">
</head>

<body class="pagina-inicio">

    <main class="inicio">

        <section class="tarjeta-inicio">

            <div class="inicio-franja"></div>

            <div class="inicio-contenido">

                <div class="inicio-marca">

                    <div class="icono-libro">
                        <span></span>
                        <span></span>
                    </div>

                    <div class="marca-texto">
                        <strong>UNTEC</strong>
                        <small>BIBLIOTECA DIGITAL</small>
                    </div>

                </div>

                <p class="inicio-etiqueta">
                    SISTEMA DE GESTIÓN BIBLIOGRÁFICA
                </p>

                <h1>
                    Biblioteca Digital
                    <span>Untec</span>
                </h1>

                <p class="inicio-descripcion">
                    Sistema Evaluación Módulo 5 Claudio Gutiérrez
                </p>

                <a class="boton-ingreso"
                   href="${pageContext.request.contextPath}/login">
                    INGRESAR AL SISTEMA
                </a>

            </div>

            <div class="inicio-pie">
                Biblioteca Digital Untec
            </div>

        </section>

    </main>

</body>

</html>
