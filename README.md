# Checkpoint 1 - Programação em Java, JPA e Annotations

## Integrantes do Grupo

*   [Nome do Integrante 1] - [RM do Integrante 1]
*   [Nome do Integrante 2] - [RM do Integrante 2]
*   [Nome do Integrante 3] - [RM do Integrante 3]
*   [Nome do Integrante 4] - [RM do Integrante 4]
*   [Nome do Integrante 5] - [RM do Integrante 5]

## Descrição do Projeto

Este projeto tem como objetivo demonstrar o uso de Java, JPA (Java Persistence API) e Annotations para criar um sistema de gerenciamento de funcionários. O sistema inclui classes para representar diferentes tipos de funcionários, a geração automática de código SQL usando Reflection e a interação com um banco de dados Oracle via Hibernate.

## Configuração do Projeto

1.  **Pré-requisitos:**
    *   Java Development Kit (JDK) 11 ou superior.
    *   Maven (se você estiver usando Maven para gerenciamento de dependências).
    *   Banco de dados Oracle configurado e acessível.
    *   IDE de sua preferência (IntelliJ IDEA, Eclipse, NetBeans).

2.  **Configuração do Banco de Dados:**
    *   Crie um usuário e senha no Oracle SQL Developer.
    *   Configure o arquivo `persistence.xml` com as credenciais corretas:

    ```
    <property name="javax.persistence.jdbc.driver" value="oracle.jdbc.driver.OracleDriver"/>
    <property name="javax.persistence.jdbc.url" value="jdbc:oracle:thin:@localhost:1521:xe"/>
    <property name="javax.persistence.jdbc.user" value="[seu_usuario]"/>
    <property name="javax.persistence.jdbc.password" value="[sua_senha]"/>
    ```

3.  **Dependências:**
    *   Se você estiver usando Maven, adicione as seguintes dependências ao seu arquivo `pom.xml`:

    ```
    <dependencies>
        <!-- JPA -->
        <dependency>
            <groupId>jakarta.persistence</groupId>
            <artifactId>jakarta.persistence-api</artifactId>
            <version>3.1.0</version>
        </dependency>
        <!-- Hibernate Core -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.4.4.Final</version>
        </dependency>
        <!-- Driver JDBC para Oracle -->
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc8</artifactId>
            <version>21.11.0.0</version>
        </dependency>
    </dependencies>
    ```

4.  **Importar o Projeto:**
    *   Importe o projeto para sua IDE preferida.
    *   Certifique-se de que as dependências são resolvidas corretamente.

## Explicação do Código

### 1. Classes de Funcionários

*   **`Funcionario`:** Classe base que representa um funcionário genérico.

    ```
    package br.com.fiap.entity;

    public class Funcionario {
        private String nome;
        private int horasTrabalhadas;
        private double valorPorHora;

        public Funcionario(String nome, int horasTrabalhadas, double valorPorHora) {
            this.nome = nome;
            this.horasTrabalhadas = horasTrabalhadas;
            this.valorPorHora = valorPorHora;
        }

        public double calcularSalario() {
            return horasTrabalhadas * valorPorHora;
        }

        public void imprimirInformacao() {
            System.out.println("Nome: " + nome);
            System.out.println("Horas Trabalhadas: " + horasTrabalhadas);
            System.out.println("Valor por Hora: " + valorPorHora);
            System.out.println("Salário Final: " + calcularSalario());
        }
    }
    ```

*   **`FuncionarioSenior`:** Subclasse de `Funcionario` que adiciona um bônus a cada 15 horas trabalhadas.

    ```
    package br.com.fiap.entity;

    public class FuncionarioSenior extends Funcionario {
        private static final double BONUS = 100.0;

        public FuncionarioSenior(String nome, int horasTrabalhadas, double valorPorHora) {
            super(nome, horasTrabalhadas, valorPorHora);
        }

        @Override
        public double calcularSalario() {
            double salarioBase = super.calcularSalario();
            int bonusHoras = getHorasTrabalhadas() / 15;
            return salarioBase + (bonusHoras * BONUS);
        }
    }
    ```

*   **`FuncionarioEstagiario` e `FuncionarioGerente`:** Outras subclasses de `Funcionario` com diferentes lógicas de cálculo de salário.

    ```
    package br.com.fiap.entity;

    public class FuncionarioEstagiario extends Funcionario {
        private double descontoImposto;

        public FuncionarioEstagiario(String nome, int horasTrabalhadas, double valorPorHora, double descontoImposto) {
            super(nome, horasTrabalhadas, valorPorHora);
            this.descontoImposto = descontoImposto;
        }

        @Override
        public double calcularSalario() {
            double salarioBruto = super.calcularSalario();
            return salarioBruto - (salarioBruto * descontoImposto / 100);
        }
    }
    ```

    ```
    package br.com.fiap.entity;

    public class FuncionarioGerente extends Funcionario {
        private double adicionalLideranca;

        public FuncionarioGerente(String nome, int horasTrabalhadas, double valorPorHora, double adicionalLideranca) {
            super(nome, horasTrabalhadas, valorPorHora);
            this.adicionalLideranca = adicionalLideranca;
        }

        @Override
        public double calcularSalario() {
            return super.calcularSalario() + adicionalLideranca;
        }
    }
    ```

### 2. Anotações e Reflection

*   **`@Tabela` e `@Coluna`:** Anotações personalizadas para mapear classes e atributos para tabelas e colunas no banco de dados.

    ```
    package br.com.fiap.entity;

    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.ElementType;
    import java.lang.annotation.Target;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Tabela {
        String nome();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Coluna {
        String nome();
    }
    ```

*   **`FuncionarioTabela`:** Classe que representa a tabela de funcionários no banco de dados.

    ```
    package br.com.fiap.entity;

    @Tabela(nome = "TAB_FUNCIONARIO")
    public class FuncionarioTabela {

        @Coluna(nome = "ID")
        private int id;

        @Coluna(nome = "NOME")
        private String nome;

        @Coluna(nome = "HORAS_TRABALHADAS")
        private int horasTrabalhadas;

        @Coluna(nome = "VALOR_POR_HORA")
        private double valorPorHora;

        // Getters e Setters
    }
    ```

*   **`GeradorSQL`:** Classe que utiliza Reflection para gerar automaticamente o código SQL.

    ```
    package br.com.fiap.util;

    import br.com.fiap.entity.Coluna;
    import br.com.fiap.entity.Tabela;
    import java.lang.reflect.Field;

    public class GeradorSQL {

        public static String gerarSelect(Class<?> classe) {
            if (!classe.isAnnotationPresent(Tabela.class)) {
                throw new IllegalArgumentException("Classe não possui a anotação @Tabela");
            }

            Tabela tabela = classe.getAnnotation(Tabela.class);
            StringBuilder sql = new StringBuilder("SELECT ");

            Field[] campos = classe.getDeclaredFields();
            for (int i = 0; i < campos.length; i++) {
                if (campos[i].isAnnotationPresent(Coluna.class)) {
                    Coluna coluna = campos[i].getAnnotation(Coluna.class);
                    sql.append(coluna.nome());
                    if (i < campos.length - 1) sql.append(", ");
                }
            }

            sql.append(" FROM ").append(tabela.nome());
            return sql.toString();
        }
    }
    ```

### 3. Persistência com JPA e Hibernate

*   **`persistence.xml`:** Arquivo de configuração do JPA.

    ```
    <persistence-unit name="CheckpointPU">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <class>br.com.fiap.entity.FuncionarioTabela</class>
        <properties>
            <property name="javax.persistence.jdbc.driver" value="oracle.jdbc.driver.OracleDriver"/>
            <property name="javax.persistence.jdbc.url" value="jdbc:oracle:thin:@localhost:1521:xe"/>
            <property name="javax.persistence.jdbc.user" value="usuario"/>
            <property name="javax.persistence.jdbc.password" value="senha"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.Oracle12cDialect"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
        </properties>
    </persistence-unit>
    ```

*   **`FuncionarioDAO`:** Classe que realiza as operações de CRUD no banco de dados.

    ```
    package br.com.fiap.dao;

    import jakarta.persistence.EntityManager;
    import jakarta.persistence.EntityManagerFactory;
    import jakarta.persistence.Persistence;
    import br.com.fiap.entity.FuncionarioTabela;

    public class FuncionarioDAO {

        private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CheckpointPU");

        public void criarFuncionario(FuncionarioTabela funcionario) {
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(funcionario);
                em.getTransaction().commit();
                System.out.println("Funcionário inserido no banco.");
            } finally {
                em.close();
            }
        }
    }
    ```

### 4. Classe Principal (`Main.java`)

*   Demonstra a criação de instâncias das classes de funcionários, a geração de código SQL e a interação com o banco de dados.

    ```
    package br.com.fiap;

    import br.com.fiap.entity.Funcionario;
    import br.com.fiap.entity.SeniorEmployee;
    import br.com.fiap.entity.FuncionarioTabela;
    import br.com.fiap.entity.FuncionarioEstagiario;
    import br.com.fiap.entity.ManagerEmployee;
    import br.com.fiap.util.GeradorSQL;
    import br.com.fiap.dao.FuncionarioDAO;

    import jakarta.persistence.EntityManager;
    import jakarta.persistence.EntityManagerFactory;
    import jakarta.persistence.Persistence;

    public class Main {

        public static void main(String[] args) {
            // Criação de funcionários
            Funcionario funcionario = new Funcionario("João", 160, 25.0);
            System.out.println("Salário do funcionário: " + funcionario.calcularSalario());
            funcionario.imprimirInformacao();

            FuncionarioSenior seniorEmployee = new FuncionarioSenior("Maria", 200, 30.0);
            System.out.println("Salário do funcionário sênior: " + seniorEmployee.calcularSalario());
            seniorEmployee.imprimirInformacao();

            FuncionarioEstagiario funcionarioEstagiario = new FuncionarioEstagiario("Ana", 120, 15.0, 10.0);
            System.out.println("Salário do funcionário estagiário: " + funcionarioEstagiario.calcularSalario());
            funcionarioEstagiario.imprimirInformacao();

            FuncionarioGerente managerEmployee = new FuncionarioGerente("Pedro", 220, 40.0, 500.0);
            System.out.println("Salário do funcionário gerente: " + managerEmployee.calcularSalario());
            managerEmployee.imprimirInformacao();

            // Geração de SQL
            String sql = GeradorSQL.gerarSelect(FuncionarioTabela.class);
            System.out.println("Código SQL gerado: " + sql);

            // Interação com o banco de dados
            FuncionarioTabela funcionarioTabela = new FuncionarioTabela();
            funcionarioTabela.setId(1);
            funcionarioTabela.setNome("Carlos");
            funcionarioTabela.setHorasTrabalhadas(180);
            funcionarioTabela.setValorPorHora(28.0);

            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            funcionarioDAO.criarFuncionario(funcionarioTabela);

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CheckpointPU");
            EntityManager em = emf.createEntityManager();

            try {
                FuncionarioTabela funcionarioEncontrado = em.find(FuncionarioTabela.class, 1);
                if (funcionarioEncontrado != null) {
                    System.out.println("Funcionário encontrado no banco: " + funcionarioEncontrado.getNome());
                } else {
                    System.out.println("Funcionário não encontrado no banco.");
                }
            } finally {
                em.close();
                emf.close();
            }
        }
    }
    ```

## Simulações

(Adicione aqui screenshots das simulações e exemplos de execução do código)

1.  **Exemplo de Saída no Console:**

    ```
    Salário do funcionário: 4000.0
    Nome: João
    Horas Trabalhadas: 160
    Valor por Hora: 25.0
    Salário Final: 4000.0
    Salário do funcionário sênior: 6600.0
    Nome: Maria
    Horas Trabalhadas: 200
    Valor por Hora: 30.0
    Salário Final: 6600.0
    Bônus aplicado: 1300.0
    Salário do funcionário estagiário: 1620.0
    Nome: Ana
    Horas Trabalhadas: 120
    Valor por Hora: 15.0
    Salário Final: 1620.0
    Desconto de Imposto: 10.0%
    Salário Líquido: 1458.0
    Salário do funcionário gerente: 9300.0
    Nome: Pedro
    Horas Trabalhadas: 220
    Valor por Hora: 40.0
    Salário Final: 9300.0
    Adicional de Liderança: 500.0
    Salário Final: 9300.0
    Código SQL gerado: SELECT ID, NOME, HORAS_TRABALHADAS, VALOR_POR_HORA FROM TAB_FUNCIONARIO
    Funcionário inserido no banco.
    Funcionário encontrado no banco: Carlos
    ```

2.  **Código SQL Gerado:**

    ![Screenshot do código SQL gerado](link-para-screenshot-sql.png)

3.  **Resultado da Consulta no Banco de Dados:**

    ![Screenshot do resultado da consulta no banco](link-para-screenshot-banco.png)

## Considerações Finais

Este projeto demonstra a utilização de conceitos avançados de Java, como Reflection e Annotations, além de JPA e Hibernate para a persistência de dados. Ele fornece uma base sólida para a construção de sistemas mais complexos e escaláveis.

