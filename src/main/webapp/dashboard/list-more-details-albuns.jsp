<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.UUID"%>
<%@ page import="br.mendonca.testemaven.dao.AlbumDAO"%>
<%@ page import="br.mendonca.testemaven.model.entities.Album"%>

<%
	String albumId = request.getParameter("albumId");

	if (albumId != null) {
		try {
			UUID albumUUID = UUID.fromString(albumId);

			AlbumDAO albumDAO = new AlbumDAO();
			Album album = albumDAO.searchById(albumUUID);

			if (album != null) {
%>
<!doctype html>
<html lang="pt-br" data-bs-theme="dark">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Detalhes do Album</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
	<h1>Detalhes do Album: <%= album.getAlbumname() %></h1>
	<p><strong>Nome:</strong> <%= album.getAlbumname() %></p>
	<p><strong>Faixas:</strong> <%= album.getTracks() %></p>
	<p><strong>Lançado:</strong> <%= album.getReleased() ? "Sim" : "Não" %></p>
	<a href="/dashboard/albuns" class="btn btn-secondary">Voltar</a>
</div>
</body>
</html>
<%
} else {
%>
<p>Album não encontrado.</p>
<%
	}
} catch (IllegalArgumentException e) {
%>
<p>ID do album inválido.</p>
<%
} catch (Exception e) {
%>
<p>Erro ao buscar album: <%= e.getMessage() %></p>
<%
	}
} else {
%>
<p>ID do album não fornecido.</p>
<%
	}
%>
