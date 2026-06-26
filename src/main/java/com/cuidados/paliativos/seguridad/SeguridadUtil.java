package com.cuidados.paliativos.seguridad;

import org.mindrot.jbcrypt.BCrypt;

public class SeguridadUtil {

    public static boolean verificar(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}