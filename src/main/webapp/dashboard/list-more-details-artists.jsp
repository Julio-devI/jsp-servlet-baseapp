<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.UUID"%>
<%@ page import="br.mendonca.testemaven.dao.ArtistDAO"%>
<%@ page import="br.mendonca.testemaven.model.entities.Artist"%>

<%
	// Pega o ID do artista passado na URL
	String artistId = request.getParameter("artistId");

	if (artistId != null) {
		try {
			UUID artistUUID = UUID.fromString(artistId);

			// Busca o artista pelo ID usando o DAO
			ArtistDAO artistDAO = new ArtistDAO();
			Artist artist = artistDAO.searchById(artistUUID); // Método correto para buscar pelo ID

			if (artist != null) {
%>
<!doctype html>
<html lang="pt-br" data-bs-theme="dark">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Detalhes do Artista</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
	<h1>Detalhes do Artista: <%= artist.getArtistname() %></h1>
	<p><strong>Nome:</strong> <%= artist.getArtistname() %></p>
	<p><strong>Listeners:</strong> <%= artist.getListeners() %></p>
	<p><strong>Ativo:</strong> <%= artist.getActive() ? "Sim" : "Não" %></p>
	<a href="/dashboard/artists" class="btn btn-secondary">Voltar</a>
</div>
</body>
</html>
<%
} else {
%>
<p>Artista não encontrado.</p>
<%
	}
} catch (IllegalArgumentException e) {
%>
<p>ID do artista inválido.</p>
<%
} catch (Exception e) {
%>
<p>Erro ao buscar artista: <%= e.getMessage() %></p>
<%
	}
} else {
%>
<p>ID do artista não fornecido.</p>
<%
	}
%>
