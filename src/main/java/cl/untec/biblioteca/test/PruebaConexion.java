package cl.untec.biblioteca.test;

import cl.untec.biblioteca.dao.ConexionBD;

import java.sql.Connection;

public class PruebaConexion {

    public static void main(String[] args) {

        try {

            Connection conexion =
                    ConexionBD.getInstancia().getConexion();

            System.out.println(
                    "====================================");
            System.out.println(
                    " CONEXION A MARIADB CORRECTA");
            System.out.println(
                    "====================================");

            System.out.println(
                    "Base de datos: "
                    + conexion.getCatalog());

        } catch (Exception e) {

            System.out.println(
                    "ERROR DE CONEXION");

            e.printStackTrace();
        }
    }
}