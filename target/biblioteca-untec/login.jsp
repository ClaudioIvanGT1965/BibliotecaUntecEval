<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <title>Inicio de Sesión - Biblioteca Digital Untec</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/estilos.css">
</head>

<body class="pagina-login">

    <main class="login-contenedor">

        <section class="tarjeta-login">

            <div class="login-franja"></div>

            <div class="login-contenido">

                <!-- Marca -->

                <div class="login-marca">

                    <div class="icono-libro">
                        <span></span>
                        <span></span>
                    </div>

                    <div class="marca-texto">
                        <strong>UNTEC</strong>
                        <small>BIBLIOTECA DIGITAL</small>
                    </div>

                </div>


                <!-- Título -->

                <h1>Inicio de sesión</h1>

                <p class="login-subtitulo">
                    Ingrese sus credenciales para acceder al sistema
                </p>


                <!-- Mensaje de error -->

                <c:if test="${not empty mensaje}">
                    <div class="mensaje-error">
                        ${mensaje}
                    </div>
                </c:if>


                <!-- Formulario -->

                <form action="${pageContext.request.contextPath}/login"
                      method="post"
                      class="formulario-login">

                    <div class="campo-login">

                        <label for="username">
                            Nombre de usuario
                        </label>

                        <input type="text"
                               id="username"
                               name="username"
                               maxlength="50"
                               autocomplete="username"
                               required>

                    </div>


                    <div class="campo-login">

                        <label for="password">
                            Password
                        </label>

                        <input type="password"
                               id="password"
                               name="password"
                               maxlength="100"
                               autocomplete="current-password"
                               required>

                    </div>


                    <button type="submit"
                            class="boton-login">
                        INGRESAR
                    </button>

                </form>


                <!-- Usuarios de prueba -->

                <div class="usuarios-prueba">

                    <h2>Usuarios de prueba</h2>

                    <p class="texto-prueba">
                        Utilice cualquiera de las siguientes cuentas
                        para probar el sistema.
                    </p>

                    <div class="credenciales">

                        <div class="credencial">

                            <h3>BIBLIOTECARIO</h3>

                            <p>
                                <strong>Usuario:</strong>
                                bibliotecario
                            </p>

                            <p>
                                <strong>Password:</strong>
                                123456
                            </p>

                        </div>


                        <div class="credencial">

                            <h3>ESTUDIANTE</h3>

                            <p>
                                <strong>Usuario:</strong>
                                estudiante
                            </p>

                            <p>
                                <strong>Password:</strong>
                                123456
                            </p>

                        </div>

                    </div>

                </div>

            </div>


            <div class="login-pie">
                Sistema Evaluación Módulo 5 Claudio Gutiérrez
            </div>

        </section>

    </main>

</body>

</html>