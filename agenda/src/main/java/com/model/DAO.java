package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
    /** Módulo de conexão **/
    //Parâmetros de conexão
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost/agenda";
    private String user = "root";
    private String password = "Sbbhqk#203";

    //Método de conexão
    private Connection conectar() {
        Connection con = null;

        try {
            Class.forName(driver); //lê o driver do banco de dados
            con = DriverManager.getConnection(url, user, password); //estabelece a conexão; gerencia o driver
            return con;
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    //teste de conexão
    /*public void testeConexao() {
        try {
            Connection con = conectar();
            System.out.println(con);
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }*/

    /* CRUD CREATE */
    public void inserirContato(JavaBeans contato) {
        String create = "insert into contatos (nome, fone, email) values (?, ?, ?)";

        try {
            //abrir a conexão com o banco
            Connection con = conectar();

            //Preparar a query para execução no banco de dados
            PreparedStatement pst = con.prepareStatement(create);

            //substituir os parâmetros (?) pelo conteúdo das variáveis JavaBeans
            pst.setString(1, contato.getNome()); //1 --> primeira (?)
            pst.setString(2, contato.getFone());
            pst.setString(3, contato.getEmail());

            //Executar a query
            pst.executeUpdate(); //insere no banco

            //encerrar a conexão com o db
            con.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /* CRUD READ */
    public ArrayList<JavaBeans> listarContatos() {
        //Criando um objeto para acessar a classe JavaBeans
        ArrayList<JavaBeans> contatos = new ArrayList<JavaBeans>();

        String read = "select * from contatos order by nome";

        try {
            Connection con = conectar();
            PreparedStatement pst = con.prepareStatement(read);
            ResultSet rs = pst.executeQuery();//armazenar o retorno do banco de dados temporariamente em um objeto

            //o laço abaixo será executado enquanto houver contatos
            while(rs.next()) {
                //variáveis de apoio que recebem os dados do banco
                String id = rs.getString(1);
                String nome = rs.getString(2);
                String fone = rs.getString(3);
                String email = rs.getString(4);

                //populando o ArrayList
                JavaBeans c = new JavaBeans(id, nome, fone, email);
                contatos.add(c);
            }

            con.close();

            return contatos;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /** CRUD UPDATE **/
    //selecionar o contato
    public void selecionarContato(JavaBeans contato) {
        String read2 = "select * from contatos where id = ?";

        try {
            Connection con = conectar();

            PreparedStatement pst = con.prepareStatement(read2);
            pst.setString(1, contato.getId());

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                //setar as variáveis JavaBeans
                contato.setId(rs.getString(1));
                contato.setNome(rs.getString(2));
                contato.setFone(rs.getString(3));
                contato.setEmail(rs.getString(4));
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //editar o contato
    public void alterarContato(JavaBeans contato) {
        String create = "update contatos set nome=?, fone=?, email=? where id=?";

        try {
            Connection con = conectar();

            PreparedStatement pst = con.prepareStatement(create);
            pst.setString(1, contato.getNome());
            pst.setString(2, contato.getFone());
            pst.setString(3, contato.getEmail());
            pst.setString(4, contato.getId());

            pst.executeUpdate();

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /* CRUD DELETE */
    public void deletarContato(JavaBeans contato) {
        String delete = "delete from contatos where id=?";

        try {
            Connection con = conectar();

            PreparedStatement pst = con.prepareStatement(delete);

            pst.setString(1, contato.getId());

            pst.executeUpdate();

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
