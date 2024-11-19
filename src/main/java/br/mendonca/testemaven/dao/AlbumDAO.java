package br.mendonca.testemaven.dao;

import br.mendonca.testemaven.model.entities.Album;
import br.mendonca.testemaven.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public void setAlbumVisible(UUID albumId, boolean visible) throws ClassNotFoundException, SQLException {
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);
        String sql = "UPDATE albuns SET visible = ? WHERE uuid = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, visible);
            ps.setObject(2, albumId);
            ps.executeUpdate();
        }
    }

    public List<Album> listAllAlbum(int pageNumber, int pageSize) throws ClassNotFoundException, SQLException {
        ArrayList<Album> lista = new ArrayList<Album>();

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        if (pageNumber < 1 || pageSize < 1) {
            throw new IllegalArgumentException("pageNumber e pageSize devem ser maiores que 0");
        }

        int offset = (pageNumber - 1) * pageSize;

        String sql = "SELECT * FROM albuns WHERE visible = true LIMIT ? OFFSET ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, pageSize);
        ps.setInt(2, offset);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Album album = new Album();
            album.setUuid(rs.getString("uuid"));
            album.setAlbumname(rs.getString("albumname"));
            album.setTracks(rs.getInt("tracks"));
            album.setReleased(rs.getBoolean("released"));
            album.setVisible(rs.getBoolean("visible"));

            lista.add(album);
        }

        rs.close();

        return lista;
    }

    public int countTotalAlbuns() throws ClassNotFoundException, SQLException {
        int totalAlbuns = 0;
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM albuns");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            totalAlbuns = rs.getInt(1);
        }
        rs.close();
        return totalAlbuns;
    }

    public Album search(String albumname, Integer tracks) throws ClassNotFoundException, SQLException {
        Album album = null;

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        // Apesar de qualquer SQL funcionar com Statement, a abordagem de usar Prepared Statement evita SQL Injection.
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM albuns WHERE albumname = ? AND tracks = ?");
        ps.setString(1, albumname);
        ps.setInt(2, tracks);
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

    public Album searchByID(UUID albumID) throws ClassNotFoundException, SQLException {
        Album album = null;
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM albuns WHERE albumname = ? AND tracks = ?");
        ps.setString(1, albumID.toString());

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
