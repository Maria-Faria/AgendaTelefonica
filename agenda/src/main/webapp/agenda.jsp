<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%> 

<%@ page import="com.model.JavaBeans"%>
<%@ page import="java.util.ArrayList"%>

<%
    ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("contatos");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agenda de contatos</title>
    <link rel="icon" href="images/icon.png">
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h1 class="page">Agenda de contatos</h1>
    <a href="novo.html" class="button2">Novo contato</a>
    <a href="report" class="button3" target="_blank">Relatório</a>

    <table id="tabela">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Telefone</th>
                <th>E-mail</th>
                <th>Opções</th>
            </tr>
        </thead>

        <tbody>
            <% for (int i = 0; i < lista.size(); i++) { %>
                <tr>
                    <td> <%=lista.get(i).getId() %></td> <!--pega o conteúdo de uma variável-->
                    <td> <%=lista.get(i).getNome() %></td>
                    <td> <%=lista.get(i).getFone() %></td>
                    <td> <%=lista.get(i).getEmail() %></td>
                    <td>
                        <a href="select?id=<%=lista.get(i).getId()%>" class="button2">Editar</a>
                        <a href="javascript:confirmar(<%=lista.get(i).getId()%>)" class="button3">Excluir</a>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>

    <script src="js/confirmador.js"></script>
</body>
</html>