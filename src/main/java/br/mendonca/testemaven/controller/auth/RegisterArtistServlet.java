package br.mendonca.testemaven.controller.auth;

import br.mendonca.testemaven.dao.ArtistDAO;
import br.mendonca.testemaven.model.entities.Artist;
import br.mendonca.testemaven.services.ArtistService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebServlet("/register-artist")
public class RegisterArtistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("form-add-artist.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter page = response.getWriter();

		try{
			String artistName = request.getParameter("artistname");
			Integer listeners = Integer.parseInt(request.getParameter("listeners"));
			boolean active = request.getParameter("active") != null;

			Artist artist = new Artist();
			artist.setArtistname(artistName);
			artist.setListeners(listeners);
			artist.setActive(active);

			ArtistDAO artistDAO = new ArtistDAO();
			artistDAO.registerArtist(artist);

			response.sendRedirect("dashboard/dashboard.jsp");

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
