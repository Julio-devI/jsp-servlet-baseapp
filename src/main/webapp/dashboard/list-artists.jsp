<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.UUID"%>

<%
	if (session.getAttribute("user") != null) {
		// Obtendo a lista de seguidores a partir do atributo request
		List<UUID> followingList = (List<UUID>) request.getAttribute("followingList");
%>

<!doctype html>
<html lang="pt-br" data-bs-theme="dark">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Gerenciar Seguidores</title>
	<link
			href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
			rel="stylesheet"
			integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
			crossorigin="anonymous">
	<link href="style.css" rel="stylesheet">
</head>
<body class="bg-body-tertiary">

<!-- Barra de Navegação -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<div class="container-fluid">
		<a class="navbar-brand" href="/dashboard/dashboard.jsp">Gerência de Configuração</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item"><a class="nav-link" href="/dashboard/dashboard.jsp">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="/dashboard/users">Users</a></li>
				<li class="nav-item"><a class="nav-link" href="/dashboard/artists">Artists</a></li>
				<li class="nav-item"><a class="nav-link" href="../form-add-artists.jsp">Add Artists</a></li>
				<li class="nav-item"><a class="nav-link" href="/dashboard/albuns">Albuns</a></li>
				<li class="nav-item"><a class="nav-link" href="../form-add-album.jsp">Add Albuns</a></li>
				<li class="nav-item"><a class="nav-link" href="/dashboard/about.jsp">About</a></li>
			</ul>
			<span class="navbar-text">
                <a class="btn btn-success" href="/auth/logoff">Logoff</a>
            </span>
		</div>
	</div>
</nav>

<!-- Conteúdo Principal -->
<div class="container mt-5">
	<h2 class="text-center">Gerenciar Seguidores</h2>

	<!-- Lista de seguidores -->
	<div class="card mt-4">
		<div class="card-header">
			<h3>Usuários que você segue:</h3>
		</div>
		<div class="card-body">
			<table class="table table-bordered table-hover">
				<thead>
				<tr>
					<th>ID do Usuário</th>
					<th>Ações</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="userId" items="${followingList}">
					<tr>
						<td>${userId}</td>
						<td>
							<form method="post" action="seguindo" class="d-inline">
								<input type="hidden" name="action" value="unfollow">
								<input type="hidden" name="followerId" value="${sessionScope.user.uuid}">
								<input type="hidden" name="followedId" value="${userId}">
								<button type="submit" class="btn btn-danger btn-sm">Deixar de Seguir</button>
							</form>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<!-- Formulário para adicionar seguidores -->
	<div class="card mt-4">
		<div class="card-header">
			<h3>Seguir um novo usuário</h3>
		</div>
		<div class="card-body">
			<form method="post" action="seguindo">
				<input type="hidden" name="action" value="follow">
				<input type="hidden" name="followerId" value="${sessionScope.user.uuid}">
				<div class="mb-3">
					<label for="followedId" class="form-label">ID do Usuário</label>
					<input type="text" id="followedId" name="followedId" class="form-control"
						   placeholder="Digite o ID do usuário">
				</div>
				<button type="submit" class="btn btn-primary">Seguir</button>
			</form>
		</div>
	</div>
</div>

<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous">
</script>
</body>
</html>
<%
	} else {
		response.sendRedirect("/auth/login.jsp");
	}
%>
