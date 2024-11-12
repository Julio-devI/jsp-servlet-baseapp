package br.mendonca.testemaven.dao;

import br.mendonca.testemaven.model.entities.Artist;
import br.mendonca.testemaven.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<Artist> listAllArtist() throws ClassNotFoundException, SQLException {
		ArrayList<Artist> lista = new ArrayList<Artist>();
		
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM artists");
		
		while (rs.next()) {
			Artist artist = new Artist();
			artist.setUuid(rs.getString("uuid"));
			artist.setArtistname(rs.getString("artistname"));
			artist.setListeners(rs.getInt("listeners"));
			artist.setActive(rs.getBoolean("active"));
			
			lista.add(artist);
		}
		
		rs.close();
		
		return lista;
	}

	public Artist search(String artistname, Integer listeners) throws ClassNotFoundException, SQLException {
		Artist artist = null;
		
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		// Apesar de qualquer SQL funcionar com Statement, a abordagem de usar Prepared Statement evita SQL Injection.
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
		ps.setString(1, artistname);
		ps.setString(2, listeners.toString());
		System.out.println(ps); // Exibe no console do Docker a query j� montada.
		
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			
			artist = new Artist();
			artist.setUuid(rs.getString("uuid"));
			artist.setArtistname(rs.getString("name"));
			artist.setListeners(rs.getInt("listeners"));
			artist.setActive(rs.getBoolean("active"));
		}
		
		rs.close();
		
		return artist;
	}

	// TODO: N�o testado
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
