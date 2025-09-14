/**
 * Define o módulo da aplicação, suas dependências e quais pacotes são abertos para reflexão.
 */
module com.ccks.sistemagestaoccks {
    // Módulos do JavaFX necessários para a aplicação
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    // Módulos de dependências externas
    requires java.sql;             // Para JDBC e conexão com o banco
    requires jbcrypt;              // Para hashing de senhas (nome de módulo automático)
    requires mysql.connector.j;    // Para o driver do MySQL

    // 'Abre' os pacotes para o JavaFX, permitindo que ele use reflexão.
    // Isso é crucial para que o FXML Loader possa instanciar seus controladores
    // e para que a TableView possa acessar as propriedades dos seus modelos.
    opens com.ccks.ui to javafx.fxml;
    opens com.ccks.model to javafx.base;

    // Exporta o pacote que contém a classe de inicialização da aplicação (App/Launcher)
    exports com.ccks;
}
