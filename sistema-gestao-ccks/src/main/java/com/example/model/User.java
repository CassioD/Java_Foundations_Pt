package com.example.model;

/**
 * Classe de Modelo (Model ou POJO - Plain Old Java Object) que representa um Usuário.
 * Ela encapsula os dados de um usuário, correspondendo à estrutura da tabela 'users' no banco de dados.
 */
public class User {

    // Atributos que representam as colunas da tabela 'users'.
    private int id;
    private String fullName;
    private String cpf;
    private String email;
    private String jobTitle;
    private String login;
    private String password; // Usado para receber a senha do formulário e para criar o hash. Não armazena o hash lido do banco.
    private UserProfile profile;

    /**
     * Enum para representar os diferentes perfis de usuário no sistema.
     * Usar um Enum torna o código mais seguro e legível do que usar Strings.
     */
    public enum UserProfile {
        ADMINISTRADOR,
        GERENTE,
        COLABORADOR
    }

    // Construtores, Getters e Setters

    /**
     * Construtor padrão.
     */
    public User() {
    }

    /**
     * Construtor completo para criar um objeto User com todos os seus dados.
     */
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

    // --- Getters e Setters ---
    // Métodos padrão para acessar e modificar os atributos privados da classe (encapsulamento).

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

    /**
     * Retorna a representação em String do objeto, que por padrão será o nome completo.
     * Isso é útil para exibir o usuário em componentes de UI como ComboBox.
     * @return O nome completo do usuário.
     */
    @Override
    public String toString() {
        return this.fullName;
    }
}