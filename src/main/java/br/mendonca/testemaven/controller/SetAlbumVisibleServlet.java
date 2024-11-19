package br.mendonca.testemaven.controller;

import br.mendonca.testemaven.dao.AlbumDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/dashboard/setAlbumVisible")
public class SetAlbumVisibleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String albumIdParam = request.getParameter("albumId");
            String visibleParam = request.getParameter("visible");

            // Verificar se os parâmetros são válidos
            if (albumIdParam == null || visibleParam == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetros inválidos.");
                return;
            }

            UUID albumId = UUID.fromString(albumIdParam);
            boolean visible = Boolean.parseBoolean(visibleParam);


            AlbumDAO albumDAO = new AlbumDAO();
            albumDAO.setAlbumVisible(albumId, visible);

            response.sendRedirect("/dashboard/albuns");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao atualizar visibilidade.");
        }
    }
}
