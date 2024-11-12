<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    	<form action="register-album" method="post">
    		<h1 class="h3 mb-3 fw-normal">Cadastrar novo album:</h1>

    		<div class="form-floating mb-3">
    			<input type="text" name="albumname" class="form-control" id="albumname" placeholder="Digite o nome do album" required />
    			<label for="albumname">Nome do album</label>
    		</div>

    		<div class="form-floating mb-3">
    			<input type="number" name="tracks" class="form-control" id="tracks" placeholder="Digite quantas faixas esse album tem" min="0" required/>
    			<label for="tracks">Número de faixas</label>
    		</div>

    		<div class="form-check mb-3">
    			<input type="checkbox" name="released" class="form-check-input" id="released" />
    			<label class="form-check-label" for="released">Foi lançado?</label>
    		</div>

    		<button class="btn btn-primary w-100 py-2 mt-2">Cadastrar novo album</button>
    	</form>
    </main>



    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
  </body>
</html>