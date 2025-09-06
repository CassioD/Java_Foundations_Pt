package com.example.model;

public class User {

    private int id;
    private String fullName;
    private String cpf;
    private String email;
    private String jobTitle;
    private String login;
    private String password;
    private UserProfile profile;

    public enum UserProfile {
        ADMINISTRADOR,
        GERENTE,
        COLABORADOR
    }

    // Construtor, Getters e Setters

    public User() {
    }

    public User(int id, String fullName, String cpf, String email, String jobTitle, String login, String password, UserProfile profile) {
        this.id = id;
        this.fullName = fullName;
        this.cpf = cpf;
        this.email = email;
        this.jobTitle = jobTitle;
        this.login = login;
        this.password = password;
        this.profile = profile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }
}