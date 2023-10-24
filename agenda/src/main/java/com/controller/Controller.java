package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.model.DAO;
import com.model.JavaBeans;

@WebServlet(urlPatterns = {"/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report"})
public class Controller extends HttpServlet{
    private static final long serialVersionUID = 1L;
    DAO dao = new DAO();
    JavaBeans contato = new JavaBeans();

    public Controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{        
        String action = request.getServletPath();
        System.out.println(action);

        if(action.equals("/main")) {
            contatos(request, response);
        
        }else if(action.equals("/insert")) {
            novoContato(request, response);
        
        }else if(action.equals("/select")) {
            listarContato(request, response);
        
        }else if(action.equals("/update")) {
            editarContato(request, response);
            
        }else if(action.equals("/delete")) {
            removerContato(request, response);

        }else if(action.equals("/report")) {
            gerarRelatorio(request, response);

        }else {
            response.sendRedirect("index.html");
        }
    }

    //Listar contatos
    protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Criando um objeto que irá receber os dados JavaBeans
        ArrayList<JavaBeans> lista = dao.listarContatos();

        //teste de recebimento da lista
       /*for(int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i).getId());
            System.out.println(lista.get(i).getNome());
            System.out.println(lista.get(i).getFone());
            System.out.println(lista.get(i).getEmail());
        }*/

        //Encaminhar a lista ao documento agenda.jsp
        request.setAttribute("contatos", lista);
        RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
        rd.forward(request, response);
    }

    //criar novo contato
    protected void novoContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //teste de recebimento dos dados do formulário
        /*System.out.println(request.getParameter("nome"));
        System.out.println(request.getParameter("fone"));
        System.out.println(request.getParameter("email"));*/

        //setar as variáveis JavaBeans
        contato.setNome(request.getParameter("name"));
        contato.setFone(request.getParameter("tel"));
        contato.setEmail(request.getParameter("email"));

        //invocar o método inserirContato passando o objeto contato
        dao.inserirContato(contato);

        //redirecionar para o documento agenda.jsp
        response.sendRedirect("main");
    }

    //Editar contato

    //selecionar contato antes de editar
    protected void listarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Recebimento do id do contato que será editado
        String id = request.getParameter("id");
        //System.out.println(id);

        //Setar a variável JavaBeans
        contato.setId(id);

        //executar o método selecionarContato (DAO)
        dao.selecionarContato(contato);

        //setar os atributos do formulário com o conteúdo JavaBeans
        request.setAttribute("id", contato.getId());
        request.setAttribute("name", contato.getNome());
        request.setAttribute("tel", contato.getFone());
        request.setAttribute("email", contato.getEmail());

        //encaminhar ao documento editar.jsp
        RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
        rd.forward(request, response);

        //teste de recebimento
        /*System.out.println(contato.getId());
        System.out.println(contato.getNome());
        System.out.println(contato.getFone());
        System.out.println(contato.getEmail());*/
    }

    //editar de fato
    protected void editarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //teste de recebimento
        /*System.out.println(request.getParameter("id"));
        System.out.println(request.getParameter("name"));
        System.out.println(request.getParameter("tel"));
        System.out.println(request.getParameter("email"));*/

        //setar as variáveis JavaBenas
        contato.setId(request.getParameter("id"));
        contato.setNome(request.getParameter("name"));
        contato.setFone(request.getParameter("tel"));
        contato.setEmail(request.getParameter("email"));

        //executar o método alterarContato()
        dao.alterarContato(contato);

        //redirecionar para o documento agenda.jsp (atualizando as alterações)
        response.sendRedirect("main");
    }

    //Remover um contato
    protected void removerContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        //setar a variável id JavaBeans
        contato.setId(id);

        //executar o método deletarContato(DAO) passando o objeto contato
        dao.deletarContato(contato);

        //redirecionar para o documento agenda.jsp atualizando informações
        response.sendRedirect("main");
    }

    //gerar relatório em PDF
    protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Document documento = new Document();

        try {
            //tipo de conteúdo
            response.setContentType("application/pdf"); //resposta será um pdf

            //nome do documento
            response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");

            //criar o documento
            PdfWriter.getInstance(documento, response.getOutputStream());

            //abrir o documento para gerar o conteúdo
            documento.open();
            documento.add(new Paragraph("Lista de contatos: "));
            documento.add(new Paragraph(" ")); //quebra de linha

            //criar uma tabela
            PdfPTable tabela = new PdfPTable(3); //3 colunas

            //cabeçalho
            PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
            PdfPCell col2 = new PdfPCell(new Paragraph("Telefone"));
            PdfPCell col3 = new PdfPCell(new Paragraph("Email"));

            tabela.addCell(col1);
            tabela.addCell(col2);
            tabela.addCell(col3);

            //popular a tabela com os contatos
            ArrayList<JavaBeans> lista = dao.listarContatos();

            for(int i = 0; i < lista.size(); i++) {
                tabela.addCell(lista.get(i).getNome());
                tabela.addCell(lista.get(i).getFone());
                tabela.addCell(lista.get(i).getEmail());
            }

            documento.add(tabela);

            documento.close();
            
        } catch (Exception e) {
            System.out.println(e);
            documento.close();
        }
    }
}
