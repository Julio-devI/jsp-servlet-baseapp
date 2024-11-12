package br.mendonca.testemaven.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import br.mendonca.testemaven.dao.AlbumDAO;
import br.mendonca.testemaven.model.entities.Album;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard/albuns")
public class ListAlbunsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter page = response.getWriter();

        try {
            AlbumDAO albumDAO = new AlbumDAO();
            List<Album> albumList = albumDAO.listAllAlbum();

            request.setAttribute("albumList", albumList);
            request.getRequestDispatcher("list-albuns.jsp").forward(request, response);
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

        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter page = response.getWriter();

        try {
            // A programacao do servlet deve ser colocada neste bloco try.
            // Apague o conteudo deste bloco try e escreva seu codigo.
            String parametro = request.getParameter("nomeparametro");

            page.println("Parametro: " + parametro);
            page.close();


        } catch (Exception e) {
            // Escreve as mensagens de Exception em uma pagina de resposta.
            // Nao apagar este bloco.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            page.println("<html lang='pt-br'><head><title>Error</title></head><body>");
            page.println("<h1>Error</h1>");
            page.println("<code>");
            page.println(sw.toString());
            page.println("</code>");
            page.println("</body></html>");
            page.close();
        } finally {

        }
    }
}
