package cl.untec.biblioteca.util;
import org.mindrot.jbcrypt.BCrypt;

public class GenerarPassword {

    public static void main(String[] args) {

        String password = "123456";

        String hash = BCrypt.hashpw(
                password,
                BCrypt.gensalt()
        );

        System.out.println(hash);
    }
}


