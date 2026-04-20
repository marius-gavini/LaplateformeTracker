package com.example;

import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    public static User authenticate(String username, String password) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findByUsername(username);

        if (user == null) {
            return null;
        }
        if (BCrypt.checkpw(password, user.getPassword())) {
            System.out.print("it's matching");
            return user;
        }
        System.out.println("Not matching");
        return null;
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
