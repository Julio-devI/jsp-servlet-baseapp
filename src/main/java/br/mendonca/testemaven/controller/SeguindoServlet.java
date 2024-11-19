package br.mendonca.testemaven.controller;

import br.mendonca.testemaven.dao.ConnectionPostgres;
import br.mendonca.testemaven.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/seguindo")
public class SeguindoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter page = response.getWriter();

        UserDAO userDAO = new UserDAO();

        UUID followerId = UUID.fromString(request.getParameter("followerId")); // Exemplo de captura do parâmetro
        List<UUID> followingList = new ArrayList<>();

        try (Connection connection = ConnectionPostgres.getConexao()) {
            followingList = userDAO.getFollowing(connection, followerId);

            page.println("<html lang='pt-br'><head><title>Seguindo</title></head><body>");
            page.println("<h1>Usuário está seguindo:</h1>");
            if (!followingList.isEmpty()) {
                for (UUID followedId : followingList) {
                    page.println("<p>Seguindo: " + followedId.toString() + "</p>");
                }
            } else {
                page.println("<p>Você não está seguindo ninguém.</p>");
            }
            page.println("</body></html>");

        } catch (Exception e) {
            // Escreve as mensagens de Exception em uma pagina de resposta.
            // Nao apagar este bloco.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            page.println("<html lang='pt-br'><head><title>Error</title></head><body>");
            page.println("<h1>Error</h1>");
            page.println("<code>" + sw.toString() + "</code>");
            page.println("</body></html>");
            page.close();
        } finally {
            page.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Definir o tipo de conteúdo da resposta como HTML
        response.setContentType("text/html");
        PrintWriter page = response.getWriter();

        UserDAO userDAO = new UserDAO();

        // Obter parâmetros enviados no formulário
        UUID followerId = UUID.fromString(request.getParameter("followerId"));
        UUID followedId = UUID.fromString(request.getParameter("followedId"));
        String action = request.getParameter("action");

        try (Connection connection = ConnectionPostgres.getConexao()) {
            if ("follow".equalsIgnoreCase(action)) {
                userDAO.followUser(connection, followerId, followedId);
            } else if ("unfollow".equalsIgnoreCase(action)) {
                userDAO.unfollowUser(connection, followerId, followedId);
            } else {
                request.setAttribute("errorMessage", "Ação inválida.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            // Recupera os usuários seguidos pelo usuário logado
            List<UUID> followingList = userDAO.getFollowing(connection, followerId);
            request.setAttribute("followingList", followingList);
            request.getRequestDispatcher("dashboard/list-following.jsp").forward(request, response);  // Exibe a página com os usuários seguidos

        } catch (Exception e) {
            // Tratamento de erro
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            page.println("<html lang='pt-br'><head><title>Error</title></head><body>");
            page.println("<h1>Error</h1>");
            page.println("<code>" + sw.toString() + "</code>");
            page.println("</body></html>");
            page.close();
        }
    }



}
