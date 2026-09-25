# BibliotecaUntecEval
Biblioteca Untec (proyecto evaluación módulo 5)

Proyecto de prestamos de libros (Aplicación de JAVA EE (Web dinámica))

Link Módelo Datos:
https://github.com/ClaudioIvanGT1965/BibliotecaUntecEval/issues/1#issue-5585404035

Resumen de funcionalidades:
---------------------------
1)	Login: Autentificación de usuario (método hashing).

2)	Solicitar/Devolver: registro de préstamos y devoluciones.

Validaciones:   a) Todos los perfiles de usuario pueden solicitar préstamos.
                           b) El stock debe ser 1 o más ejemplares disponibles.
                           c)  Mismo usuario no puede solicitar un ejemplar que ya tiene en su poder.

3)	Editar: interfaz que permite realizar cambios directos en el catálogo.
                           Función Modificar: permite cambiar datos de un título, con excepción de: ID, 
                           Título, Autor y Código interno.
              Validaciones:   a) No permite datos en blanco, con excepción de la Editorial.
                           Función Eliminar: permite quitar un Título del catálogo en forma permanente.
              Validaciones:  a) No permite eliminar un título que ya tiene historia (integridad
                                            referencial). En su defecto, solo se puede determinar stock 0 para que
                                           desaparezca del catálogo.
4)	Ingresar nuevo: Agrega un título al catálogo.

Validaciones: a) No permite grabar si existen campos en blanco o no seleccionado, con 
                             excepción de Editorial.

5)	 Mis prestamos: Despliega en la grilla principal los títulos que están en poder del usuario.
                   
        Perfiles:
        Estudiante:  Solo usar filtros para buscar títulos y Solicitar/Devolver ejemplares.
         Username: estudiante,    Password: 123456
        Bibliotecario: -  Usar filtros para buscar títulos y Solicitar/Devolver ejemplares. 
                                  - Editar y Modificar/Eliminar Títulos.
                                  -  Agregar nuevos títulos.
         Username: bibliotecario,   Password: 123456

