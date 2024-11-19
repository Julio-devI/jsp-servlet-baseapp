package br.mendonca.testemaven.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import br.mendonca.testemaven.dao.ConnectionPostgres;

public class InstallService {
	
	private void statement(String sql) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionPostgres.getConexao();
		conn.setAutoCommit(true);
		
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
		st.close();
	}
	
	public void testConnection() throws ClassNotFoundException, SQLException {
		ConnectionPostgres.getConexao();
	}
	
	public void deleteUserTable() throws ClassNotFoundException, SQLException {
		statement("DROP TABLE IF EXISTS users");
	}
	
	public void createUserTable() throws ClassNotFoundException, SQLException {
		statement("CREATE TABLE users ("
					+ "    uuid UUID DEFAULT gen_random_uuid() PRIMARY KEY,"
					+ "    name VARCHAR(255) NOT NULL,"
					+ "    email VARCHAR(255) NOT NULL,"
					+ "    password VARCHAR(255) NOT NULL)");
	}

	public void createUserFollowersTable() throws ClassNotFoundException, SQLException {
		statement("CREATE TABLE IF NOT EXISTS user_followers ("
				+ "    uuid UUID DEFAULT gen_random_uuid() PRIMARY KEY,"
				+ "    follower_id UUID NOT NULL,"
				+ "    followed_id UUID NOT NULL,"
				+ "    FOREIGN KEY (follower_id) REFERENCES users(uuid) ON DELETE CASCADE,"
				+ "    FOREIGN KEY (followed_id) REFERENCES users(uuid) ON DELETE CASCADE,"
				+ "    UNIQUE (follower_id, followed_id)"
				+ ")");
	}

	public void deleteArtistTable() throws ClassNotFoundException, SQLException {
		statement("DROP TABLE IF EXISTS artists");
	}

	public void createArtistTable() throws ClassNotFoundException, SQLException {
		statement("CREATE TABLE artists ("
				+ "    uuid UUID DEFAULT gen_random_uuid() PRIMARY KEY,"
				+ "    artistname VARCHAR(255) NOT NULL,"
				+ "    listeners INTEGER NOT NULL,"
				+ "    active BOOLEAN NOT NULL,"
				+ "	   visible BOOLEAN DEFAULT true NOT NULL)");
	}

	public void deleteAlbumTable() throws ClassNotFoundException, SQLException {
		statement("DROP TABLE IF EXISTS albuns");
	}

	public void createAlbumTable() throws ClassNotFoundException, SQLException {
		statement("CREATE TABLE albuns ("
				+ "    uuid UUID DEFAULT gen_random_uuid() PRIMARY KEY,"
				+ "    albumname VARCHAR(255) NOT NULL,"
				+ "    tracks INTEGER NOT NULL,"
				+ "    released BOOLEAN NOT NULL)");
	}

	public void seedArtist1Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (artistname, listeners, active) VALUES ('"
				+ "Madonna" + "', "
				+ 12 + ", "
				+ true + ")");
	}

	public void seedArtist2Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (artistname, listeners, active) VALUES ('"
				+ "Bruno Mars" + "', "
				+ 20000 + ", "
				+ true + ")");
	}

	public void seedArtist3Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (artistname, listeners, active) VALUES ('"
				+ "Gusttavo Lima" + "', "
				+ 120000 + ", "
				+ true + ")");
	}

	public void seedArtist4Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (artistname, listeners, active) VALUES ('"
				+ "Ed Sheeran" + "', "
				+ 50000 + ", "
				+ true + ")");
	}

	public void seedArtist5Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (artistname, listeners, active) VALUES ('"
				+ "Katy Perry" + "', "
				+ 2 + ", "
				+ true + ")");
	}

	public void seedArtist6Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (artistname, listeners, active) VALUES ('"
				+ "Paul McCartney" + "', "
				+ 5 + ", "
				+ true + ")");
	}

	public void seedArtist7Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (artistname, listeners, active) VALUES ('"
				+ "Mariah Carey" + "', "
				+ 12 + ", "
				+ true + ")");
	}

	public void seedAlbum1Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (albumname, tracks, released) VALUES ('"
				+ "Like a Virgin" + "', "
				+ 9 + ", "
				+ true + ")");
	}

	public void seedAlbum2Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (albumname, tracks, released) VALUES ('"
				+ "24K Magic" + "', "
				+ 9 + ", "
				+ true + ")");
	}

	public void seedAlbum3Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (albumname, tracks, released) VALUES ('"
				+ "Thriller" + "', "
				+ 15 + ", "
				+ true + ")");
	}

	public void seedAlbum4Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (albumname, tracks, released) VALUES ('"
				+ "Dark Side of Moon" + "', "
				+ 8 + ", "
				+ true + ")");
	}

	public void seedAlbum5Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (albumname, tracks, released) VALUES ('"
				+ "Back in Black" + "', "
				+ 14 + ", "
				+ true + ")");
	}

	public void seedAlbum6Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (albumname, tracks, released) VALUES ('"
				+ "999" + "', "
				+ 9 + ", "
				+ false + ")");
	}

	public void seedAlbum7Table() throws ClassNotFoundException, SQLException {
		statement("INSERT INTO artists (albumname, tracks, released) VALUES ('"
				+ "The Hills" + "', "
				+ 13 + ", "
				+ true + ")");
	}
}
