<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Gerenciar Seguidores</title>
</head>
<body>
<h1>Usuários que você segue:</h1>
<ul>
  <!-- Itera sobre a lista de IDs dos usuários que o usuário logado segue -->
  <c:forEach var="userId" items="${followingList}">
    <li>
      Usuário ID: ${userId}
      <!-- Formulário para "deixar de seguir" -->
      <form method="post" action="seguindo">
        <input type="hidden" name="action" value="unfollow">
        <input type="hidden" name="followerId" value="${param.followerId}">
        <input type="hidden" name="followedId" value="${userId}">
        <button type="submit">Deixar de Seguir</button>
      </form>
    </li>
  </c:forEach>
</ul>

<h2>Seguir um novo usuário</h2>
<form method="post" action="seguindo">
  <input type="hidden" name="action" value="follow">
  <input type="hidden" name="followerId" value="${param.followerId}">
  <label for="followedId">ID do usuário a seguir:</label>
  <input type="text" id="followedId" name="followedId">
  <button type="submit">Seguir</button>
</form>
</body>
</html>
