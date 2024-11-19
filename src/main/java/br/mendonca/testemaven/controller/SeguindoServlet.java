package br.mendonca.testemaven.controller;

import br.mendonca.testemaven.dao.ConnectionPostgres;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/seguindo")
public class SeguindoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int followerId = Integer.parseInt(request.getParameter("followerId"));
        int followedId = Integer.parseInt(request.getParameter("followedId")); // Pode ser usado para follow/unfollow

        try (Connection connection = ConnectionPostgres.getConexao()) {
            switch (action) {
                case "follow":
                    followUser(connection, followerId, followedId);
                    break;
                case "unfollow":
                    unfollowUser(connection, followerId, followedId);
                    break;
                case "view":
                    List<Integer> following = getFollowing(connection, followerId);
                    request.setAttribute("followingList", following);
                    request.getRequestDispatcher("/seguindo.jsp").forward(request, response);
                    return;
            }
            response.sendRedirect("seguindo?action=view&followerId=" + followerId);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no servidor");
        }
    }

    private void followUser(Connection connection, int followerId, int followedId) throws SQLException {
        String sql = "INSERT INTO user_followers (follower_id, followed_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, followerId);
            stmt.setInt(2, followedId);
            stmt.executeUpdate();
        }
    }

    private void unfollowUser(Connection connection, int followerId, int followedId) throws SQLException {
        String sql = "DELETE FROM user_followers WHERE follower_id = ? AND followed_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, followerId);
            stmt.setInt(2, followedId);
            stmt.executeUpdate();
        }
    }

    private List<Integer> getFollowing(Connection connection, int followerId) throws SQLException {
        String sql = "SELECT followed_id FROM user_followers WHERE follower_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, followerId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Integer> followingList = new ArrayList<>();
                while (rs.next()) {
                    followingList.add(rs.getInt("followed_id"));
                }
                return followingList;
            }
        }
    }
}
