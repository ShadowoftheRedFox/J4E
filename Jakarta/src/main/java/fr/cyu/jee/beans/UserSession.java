package fr.cyu.jee.beans;

import java.beans.JavaBean;
import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@JavaBean
@Entity
@Table(name = "user_session")
public class UserSession {
    public static final int TOKEN_LENGTH = 32;

    @Id
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "token", nullable = false)
    private String token;

    public UserSession() {
        generateToken();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Generate a random token.
     */
    public void generateToken() {
        final String SALT_CHARS = "ABCEDFEGHIJKLMNOPQRSTUVWXYZabcedfeghijklmnopqrstuvwxyz0123456789";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < TOKEN_LENGTH) {
            int index = Math.abs(rnd.nextInt() % SALT_CHARS.length());
            salt.append(SALT_CHARS.charAt(index));
        }
        this.token = salt.toString();
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof UserSession) {
            return ((UserSession) obj).getToken().equals(getToken());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }

    @Override
    public String toString() {
        return getId() + ": " + getUser().getId() + " " + getToken();
    }
}
