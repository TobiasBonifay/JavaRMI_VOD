package fr.polytech.rmi.server;

/**
 * Represents the user
 */
public class User {

    private final String email;
    private String password;

    /**
     * Construct the user with the given email and password
     *
     * @param email    final String email
     * @param password String password
     */
    public User(final String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Return the user's email address
     *
     * @return email address as string
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Return the user's password
     *
     * @return password as string
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Allow the user to change their password
     *
     * @param password new password as string
     */
    public void setPassword(String password) {
        this.password = password;
    }


}
