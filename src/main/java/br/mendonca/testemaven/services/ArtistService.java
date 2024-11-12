package br.mendonca.testemaven.services;

import br.mendonca.testemaven.dao.ArtistDAO;
import br.mendonca.testemaven.model.entities.Artist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistService {

	private final ArtistDAO artistDAO;

	public ArtistService() {
		this.artistDAO = new ArtistDAO();
	}

	public void register(String artistname, Integer listeners, Boolean active) throws ClassNotFoundException, SQLException {
		if(artistname == null || artistname.isEmpty()) {
			throw new IllegalArgumentException("O nome do artista não pode ser vazio");
		}

		if(listeners == null || listeners < 0) {
			throw new IllegalArgumentException("O nome do artista não pode ser vazio");
		}

		Artist artist = new Artist();

		artist.setArtistname(artistname);
		artist.setListeners(listeners);
		artist.setActive(active);

		artistDAO.registerArtist(artist);
	}

	public List<Artist> listArtistsPage(int pageNumber, int itemsPerPage) throws ClassNotFoundException, SQLException {
		int offset = (pageNumber - 1) * itemsPerPage;  // Calcula o offset para a consulta

		return artistDAO.listAllArtist(offset, itemsPerPage);
	}

	public int countTotalArtists() throws ClassNotFoundException, SQLException {
		return artistDAO.countTotalArtists();
	}

	public List<Artist> listAllArtists(int pageNumber, int itemsPerPage) throws ClassNotFoundException, SQLException {
		return artistDAO.listAllArtist(pageNumber, itemsPerPage);
	}
}
