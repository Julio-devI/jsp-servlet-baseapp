package br.mendonca.testemaven.dao;

import br.mendonca.testemaven.model.entities.Album;
import br.mendonca.testemaven.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAO {

    public void registerAlbum(Album album) throws ClassNotFoundException, SQLException {
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO albuns (albumname, tracks, released) values (?,?,?)");
        ps.setString(1, album.getAlbumname());
        ps.setInt(2, album.getTracks());
        ps.setBoolean(3, album.getReleased());
        ps.execute();
        ps.close();
    }

    public List<Album> listAllAlbum() throws ClassNotFoundException, SQLException {
        ArrayList<Album> lista = new ArrayList<Album>();

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM albuns");

        while (rs.next()) {
            Album album = new Album();
            album.setUuid(rs.getString("uuid"));
            album.setAlbumname(rs.getString("albumname"));
            album.setTracks(rs.getInt("tracks"));
            album.setReleased(rs.getBoolean("released"));

            lista.add(album);
        }

        rs.close();

        return lista;
    }

    public Album search(String albumname, Integer tracks) throws ClassNotFoundException, SQLException {
        Album album = null;

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        // Apesar de qualquer SQL funcionar com Statement, a abordagem de usar Prepared Statement evita SQL Injection.
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
        ps.setString(1, albumname);
        ps.setString(2, tracks.toString());
        System.out.println(ps); // Exibe no console do Docker a query j� montada.

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {

            album = new Album();
            album.setUuid(rs.getString("uuid"));
            album.setAlbumname(rs.getString("albumname"));
            album.setTracks(rs.getInt("tracks"));
            album.setReleased(rs.getBoolean("released"));
        }

        rs.close();

        return album;
    }

    // TODO: Nao testado
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
