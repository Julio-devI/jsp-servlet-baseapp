package br.mendonca.testemaven.dao;

import br.mendonca.testemaven.model.entities.Musica;
import br.mendonca.testemaven.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicaDAO {

    public void registerMusica(Musica musica) throws ClassNotFoundException, SQLException {
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO musicas (nomeMusica, visualizacao, musicaFavorita) values (?,?,?)");
        ps.setString(1, musica.getNomeMusica());
        ps.setInt(2, musica.getVisualizacao());
        ps.setBoolean(3, musica.getMusicaFavorita());
        ps.execute();
        ps.close();
    }

    public List<Musica> listAllMusica() throws ClassNotFoundException, SQLException {
        ArrayList<Musica> lista = new ArrayList<Musica>();

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM musicas");

        while (rs.next()) {
            Musica musica = new Musica();
            musica.setUuid(rs.getString("uuid"));
            musica.setNomeMusica(rs.getString("nomeMusica"));
            musica.setVisualizacao(rs.getInt("visualizacao"));
            musica.setMusicaFavorita(rs.getBoolean("musicaFavorita"));

            lista.add(musica);
        }

        rs.close();

        return lista;
    }

    public Musica search(String nomeMusica, Integer visualizacao) throws ClassNotFoundException, SQLException {
        Musica musica = null;

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
        ps.setString(1, nomeMusica);
        ps.setString(2, visualizacao.toString());
        System.out.println(ps);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {

            musica = new Musica();
            musica.setUuid(rs.getString("uuid"));
            musica.setNomeMusica(rs.getString("nomeMusica"));
            musica.setVisualizacao(rs.getInt("visualizacao"));
            musica.setMusicaFavorita(rs.getBoolean("musicaFavorita"));
        }

        rs.close();

        return musica;
    }

    public List<User> search(String name) throws ClassNotFoundException, SQLException {
        ArrayList<User> lista = new ArrayList<User>();

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

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
