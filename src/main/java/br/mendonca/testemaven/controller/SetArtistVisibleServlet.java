package br.mendonca.testemaven.controller;

import br.mendonca.testemaven.dao.ArtistDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/dashboard/setArtistVisible")
public class SetArtistVisibleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String artistIdParam = request.getParameter("artistId");
            String visibleParam = request.getParameter("visible");

            // Verificar se os parâmetros são válidos
            if (artistIdParam == null || visibleParam == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetros inválidos.");
                return;
            }

            UUID artistId = UUID.fromString(artistIdParam);
            boolean visible = Boolean.parseBoolean(visibleParam);

            // Chamando o metodo do DAO para atualizar a visibilidade
            ArtistDAO artistDAO = new ArtistDAO();
            artistDAO.setArtistVisible(artistId, visible);

            // Redireciona para a lista de artistas
            response.sendRedirect("/dashboard/artists"); // Ajuste para o caminho correto da página de artistas
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao atualizar visibilidade.");
        }
    }
}
