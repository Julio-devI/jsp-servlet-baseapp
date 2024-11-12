package br.mendonca.testemaven.controller.auth;

import br.mendonca.testemaven.dao.AlbumDAO;
import br.mendonca.testemaven.model.entities.Album;
import br.mendonca.testemaven.services.AlbumService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebServlet("/register-album")
public class RegisterAlbumServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("form-add-album.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter page = response.getWriter();

        try{
            String albumName = request.getParameter("albumname");
            Integer tracks = Integer.parseInt(request.getParameter("tracks"));
            boolean released = request.getParameter("released") != null;

            Album album = new Album();
            album.setAlbumname(albumName);
            album.setTracks(tracks);
            album.setReleased(released);

            AlbumDAO albumDAO = new AlbumDAO();
            albumDAO.registerAlbum(album);

            response.sendRedirect("./dashboard/dashboard.jsp");

        }catch (Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            page.println("<html lang='pt-br'><head><title>Error</title></head><body>");
            page.println("<h1>Error</h1>");
            page.println("<code>" + sw.toString() + "</code>");
            page.println("</body></html>");
            page.close();
        } finally {

        }
    }
}
