<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.mendonca.testemaven.model.entities.Artist"%>

<% if (session.getAttribute("user") != null && request.getAttribute("hiddenArtistList") != null) { %>

<!doctype html>
<html lang="pt-br" data-bs-theme="dark">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Gerência de Configuração</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<link href="style.css" rel="stylesheet">
</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">

<main class="w-100 m-auto form-container">

	<nav class="navbar navbar-expand-lg bg-body-tertiary">
		<div class="container-fluid">
			<a class="navbar-brand" href="/dashboard/dashboard.jsp">Gerência de Configuração</a>
			<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarText"
					aria-controls="navbarText" aria-expanded="false"
					aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarText">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" href="/dashboard/dashboard.jsp">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="/dashboard/users">Users</a></li>
					<li class="nav-item"><a class="nav-link" href="/dashboard/artists">Artists</a></li>
					<li class="nav-item"><a class="nav-link" href="/dashboard/hiddenArtists">Artists hidden</a></li>
					<li class="nav-item"><a class="nav-link" href="../form-add-artist.jsp">Add Artists</a></li>
					<li class="nav-item"><a class="nav-link" href="/dashboard/about.jsp">About</a></li>
				</ul>
				<span class="navbar-text">
						<a class="btn btn-success" href="/auth/logoff">Logoff</a>
					</span>
			</div>
		</div>
	</nav>

	<h1 class="h3 mb-3 fw-normal">Artistas</h1>
	<table class="table">
		<thead>
		<tr>
			<th scope="col">Nome do artista</th>
			<th>Mais detalhes</th>
			<th>Restaurar</th>
		</tr>
		</thead>
		<tbody>
		<%
			List<Artist> hiddenArtistList = (List<Artist>) request.getAttribute("hiddenArtistList");
			for (Artist artist : hiddenArtistList) {
		%>
		<tr>
			<td><%= artist.getArtistname() %></td>
			<td><a href="list-more-details-artists.jsp?artistId=<%= artist.getUuid() %>" class="btn btn-success btn-sm"> Mais detalhes </a></td>
			<td>
				<a href="/dashboard/setArtistVisible?artistId=<%= artist.getUuid() %>&visible=false"
				   class="btn btn-danger btn-sm">
					Deletar
				</a>
			</td>
		</tr>
		<% } %>
		</tbody>
	</table>

	<div class="pagination">
		<%
			int currentPage = (Integer) request.getAttribute("currentPage");
			int totalPages = (Integer) request.getAttribute("totalPages");
		%>

		<% if (currentPage > 1) { %>
		<a href="?page=<%= currentPage - 1 %>" class="btn btn-primary">Anterior</a>
		<% } %>

		<span>Página <%= currentPage %> de <%= totalPages %></span>

		<% if (currentPage < totalPages) { %>
		<a href="?page=<%= currentPage + 1 %>" class="btn btn-primary">Próxima</a>
		<% } %>
	</div>

</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>

<% } %>
