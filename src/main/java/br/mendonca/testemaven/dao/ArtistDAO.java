package br.mendonca.testemaven.dao;

import br.mendonca.testemaven.model.entities.Artist;
import br.mendonca.testemaven.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArtistDAO {

	public void registerArtist(Artist artist) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		PreparedStatement ps = conn.prepareStatement("INSERT INTO artists (artistname, listeners, active) values (?,?,?)");
		ps.setString(1, artist.getArtistname());
		ps.setInt(2, artist.getListeners());
		ps.setBoolean(3, artist.getActive());
		ps.execute();
		ps.close();
	}

	public void setArtistVisible(UUID artistId, boolean visible) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		String sql = "UPDATE artists SET visible = ? WHERE uuid = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setBoolean(1, visible);
			ps.setObject(2, artistId);
			ps.executeUpdate();
		}
	}

	public List<Artist> listHiddenArtists(int pageNumber, int pageSize) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		if (pageNumber < 1 || pageSize < 1) {
			throw new IllegalArgumentException("pageNumber e pageSize devem ser maiores que 0");
		}

		ArrayList<Artist> lista = new ArrayList<Artist>();

		// Calcular o offset com base no número da página e no tamanho da página
		int offset = (pageNumber - 1) * pageSize;

		// Criar a consulta SQL com paginação
		String sql = "SELECT * FROM artists WHERE visible = false LIMIT ? OFFSET ?";

		// Criar um PreparedStatement para executar a consulta
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pageSize);  // Definir o tamanho da página
		ps.setInt(2, offset);     // Definir o offset baseado na página

		// Executar a consulta para listar artistas com visible = true e com a paginação aplicada
		ResultSet rs = ps.executeQuery();

		// Processar os resultados da consulta
		while (rs.next()) {
			Artist artist = new Artist();
			artist.setUuid(rs.getString("uuid"));
			artist.setArtistname(rs.getString("artistname"));
			artist.setListeners(rs.getInt("listeners"));
			artist.setActive(rs.getBoolean("active"));
			artist.setVisible(rs.getBoolean("visible"));

			lista.add(artist);
		}

		// Fechar o ResultSet
		rs.close();

		// Fechar o PreparedStatement
		ps.close();

		return lista;
	}

	public List<Artist> listAllArtist(int pageNumber, int pageSize) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		if (pageNumber < 1 || pageSize < 1) {
			throw new IllegalArgumentException("pageNumber e pageSize devem ser maiores que 0");
		}

		ArrayList<Artist> lista = new ArrayList<Artist>();

		// Calcular o offset com base no número da página e no tamanho da página
		int offset = (pageNumber - 1) * pageSize;

		// Criar a consulta SQL com paginação
		String sql = "SELECT * FROM artists WHERE visible = true LIMIT ? OFFSET ?";

		// Criar um PreparedStatement para executar a consulta
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, pageSize);  // Definir o tamanho da página
		ps.setInt(2, offset);     // Definir o offset baseado na página

		// Executar a consulta para listar artistas com visible = true e com a paginação aplicada
		ResultSet rs = ps.executeQuery();

		// Processar os resultados da consulta
		while (rs.next()) {
			Artist artist = new Artist();
			artist.setUuid(rs.getString("uuid"));
			artist.setArtistname(rs.getString("artistname"));
			artist.setListeners(rs.getInt("listeners"));
			artist.setActive(rs.getBoolean("active"));
			artist.setVisible(rs.getBoolean("visible"));

			lista.add(artist);
		}

		// Fechar o ResultSet
		rs.close();

		// Fechar o PreparedStatement
		ps.close();

		return lista;
	}



	public int countTotalArtists() throws ClassNotFoundException, SQLException {
		int totalArtists = 0;

		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM artists");
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			totalArtists = rs.getInt(1);
		}

		rs.close();

		return totalArtists;
	}

	public Artist search(String artistname, Integer listeners) throws ClassNotFoundException, SQLException {
		Artist artist = null;

		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		// Usando PreparedStatement para evitar SQL Injection e procurando pelo nome do artista e número de ouvintes
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM artists WHERE artistname = ? AND listeners = ?");
		ps.setString(1, artistname);  // Passando o nome do artista
		ps.setInt(2, listeners);  // Passando o número de ouvintes

		// Exibindo a query no console para depuração
		System.out.println(ps);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			// Criando o objeto Artist com os dados retornados do banco
			artist = new Artist();
			artist.setUuid(rs.getString("uuid"));  // Corrigido para pegar o UUID
			artist.setArtistname(rs.getString("artistname"));  // Corrigido para acessar a coluna "artistname"
			artist.setListeners(rs.getInt("listeners"));  // Acessando a coluna "listeners"
			artist.setActive(rs.getBoolean("active"));  // Acessando a coluna "active"
		}

		rs.close();

		return artist;
	}

	public Artist searchById(UUID artistId) throws ClassNotFoundException, SQLException {
		Artist artist = null;

		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		// Adiciona o cast explícito para UUID no PostgreSQL
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM artists WHERE uuid = CAST(? AS uuid)");
		ps.setString(1, artistId.toString());  // O UUID é passado como string, o cast converte para UUID

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			artist = new Artist();
			artist.setUuid(rs.getString("uuid"));
			artist.setArtistname(rs.getString("artistname"));
			artist.setListeners(rs.getInt("listeners"));
			artist.setActive(rs.getBoolean("active"));
		}

		rs.close();

		return artist;
	}

	// TO DO: Nao testado
	public List<User> search(String name) throws ClassNotFoundException, SQLException {
		ArrayList<User> lista = new ArrayList<User>();

		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);

		// Apesar de qualquer SQL funcionar com Statement, a abordagem de usar Prepared Statement evita SQL Injection.
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE ? LIKE LOWER(?) || LOWER(name) || '%'");
		ps.setString(1, name);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			User user = new User();
			user.setUuid(rs.getString("uuid"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));

			lista.add(user);
		}

		rs.close();

		return lista;
	}
}
