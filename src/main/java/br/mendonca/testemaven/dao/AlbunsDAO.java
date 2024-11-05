package br.mendonca.testemaven.dao;
import br.mendonca.testemaven.model.entities.Albuns;
import br.mendonca.testemaven.model.entities.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbunsDAO {
    public void registerAlbuns(Albuns albuns) throws ClassNotFoundException, SQLException {
        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO artists (artistname, listeners, active) values (?,?,?)");
        ps.setString(1, albuns.getNome());
        ps.setInt(2, albuns.getReproducoes());
        ps.setBoolean(3, albuns.isMaisReproduzido());
        ps.execute();
        ps.close();
    }

    public List<Albuns> listAllAlbuns() throws ClassNotFoundException, SQLException {
        ArrayList<Albuns> lista = new ArrayList<Albuns>();

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM albuns");

        while (rs.next()) {
            Albuns albuns = new Albuns();
            albuns.setUuid(rs.getString("uuid"));
            albuns.setNome(rs.getString("Nome Album"));
            albuns.setReproducoes(rs.getInt("Reproducoes"));
            albuns.setMaisReproduzido(rs.getBoolean("Mais Reproduzido"));

            lista.add(albuns);
        }

        rs.close();

        return lista;
    }
    public Albuns search(String Nome, Integer reproducoes) throws ClassNotFoundException, SQLException {
        Albuns albuns = null;

        Connection conn = ConnectionPostgres.getConexao();
        conn.setAutoCommit(true);

        // Apesar de qualquer SQL funcionar com Statement, a abordagem de usar Prepared Statement evita SQL Injection.
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
        ps.setString(1, Nome);
        ps.setString(2, reproducoes.toString());
        System.out.println(ps); // Exibe no console do Docker a query j� montada.

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {

            albuns = new Albuns();
            albuns.setUuid(rs.getString("uuid"));
            albuns.setNome(rs.getString("Nome Album"));
            albuns.setReproducoes(rs.getInt("Reproducoes"));
            albuns.setMaisReproduzido(rs.getBoolean("Mais Reproduzido"));
        }

        rs.close();

        return albuns;
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