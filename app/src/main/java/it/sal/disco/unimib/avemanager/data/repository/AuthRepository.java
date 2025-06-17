package it.sal.disco.unimib.avemanager.data.repository;

public class AuthRepository {

    public boolean login(String username, String password) {
        // Finto login per test
        return "admin".equals(username) && "1234".equals(password);
    }
}
