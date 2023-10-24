<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edição de contato</title>
    <link rel="icon" href="images/icon.png">
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <h1 class="page">Editar contato</h1>

    <form name="frmContato" action="update">
        <table>
            <tr>
                <td><input type="text" name="id" id="box3" readonly value="<%out.print(request.getAttribute("id"));%>"></td>
            </tr>

            <tr>
                <td><input type="text" name="name" class="box1" value="<%out.print(request.getAttribute("name"));%>"></td>
            </tr>

            <tr>
                <td><input type="text" name="tel" class="box2" value="<%out.print(request.getAttribute("tel"));%>"></td>
            </tr>

            <tr>
                <td><input type="email" name="email" class="box1" value="<%out.print(request.getAttribute("email"));%>"></td>
            </tr>
        </table>

        <input type="button" value="Salvar" class="button2" onclick="validar()">
    </form>

    <script src="js/validador.js"></script>
</body>
</html>