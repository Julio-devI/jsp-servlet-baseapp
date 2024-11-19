package br.mendonca.testemaven.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.mendonca.testemaven.model.entities.User;

public class UserDAO {

	public void register(User user) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		PreparedStatement ps = conn.prepareStatement("INSERT INTO users (name, email, password) values (?,?,?)");
		ps.setString(1, user.getName());
		ps.setString(2, user.getEmail());
		ps.setString(3, user.getPassword());
		ps.execute();
		ps.close();
	}
	
	public List<User> listAllUser() throws ClassNotFoundException, SQLException {
		ArrayList<User> lista = new ArrayList<User>();
		
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM users");
		
		while (rs.next()) {
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

	public User search(String email, String password) throws ClassNotFoundException, SQLException {
		User user = null;
		
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		// Apesar de qualquer SQL funcionar com Statement, a abordagem de usar Prepared Statement evita SQL Injection.
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
		ps.setString(1, email);
		ps.setString(2, password);
		System.out.println(ps); // Exibe no console do Docker a query js montada.
		
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			
			user = new User();
			user.setUuid(rs.getString("uuid"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
		}
		
		rs.close();
		
		return user;
	}

	public void followUser(Connection connection, UUID followerId, UUID followedId) throws SQLException {
		String sql = "INSERT INTO user_followers (follower_id, followed_id) VALUES (?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setObject(1, followerId); // Usando UUID como parâmetro
			stmt.setObject(2, followedId); // Usando UUID como parâmetro
			stmt.executeUpdate();
		}
	}

	public void unfollowUser(Connection connection, UUID followerId, UUID followedId) throws SQLException {
		String sql = "DELETE FROM user_followers WHERE follower_id = ? AND followed_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setObject(1, followerId); // Usando UUID como parâmetro
			stmt.setObject(2, followedId); // Usando UUID como parâmetro
			stmt.executeUpdate();
		}
	}

	public List<UUID> getFollowing(Connection connection, UUID followerId) throws SQLException {
		String sql = "SELECT followed_id FROM user_followers WHERE follower_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setObject(1, followerId); // Usando UUID como parâmetro
			try (ResultSet rs = stmt.executeQuery()) {
				List<UUID> followingList = new ArrayList<>();
				while (rs.next()) {
					followingList.add((UUID) rs.getObject("followed_id")); // Recupera os UUIDs
				}
				return followingList;
			}
		}
	}


	//TO DO: Nao testado
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
