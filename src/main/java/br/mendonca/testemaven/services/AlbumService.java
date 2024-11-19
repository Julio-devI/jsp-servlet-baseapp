package br.mendonca.testemaven.services;

import br.mendonca.testemaven.dao.AlbumDAO;
import br.mendonca.testemaven.model.entities.Album;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumService {

    private final AlbumDAO albumDAO;

    public AlbumService() {
        this.albumDAO = new AlbumDAO();
    }

    public void register(String albumname, Integer tracks, Boolean released) throws ClassNotFoundException, SQLException {
        if(albumname == null || albumname.isEmpty()) {
            throw new IllegalArgumentException("O nome do album não pode ser vazio");
        }

        if(tracks == null || tracks < 0) {
            throw new IllegalArgumentException("A quantidade de faixas não pode ser menor que zero");
        }

        Album album = new Album();

        album.setAlbumname(albumname);
        album.setTracks(tracks);
        album.setReleased(released);

        albumDAO.registerAlbum(album);
    }

    public List<Album> listAllAlbum(int pageNumber, int itemsPerPage) throws ClassNotFoundException, SQLException {
        int offset = (pageNumber - 1) * itemsPerPage;
        return albumDAO.listAllAlbum(offset, itemsPerPage);
    }
    public int countTotalAbuns() throws ClassNotFoundException, SQLException {
        return albumDAO.countTotalAlbuns();
    }
    public List<Album> listAllAlbuns(int pageNumber, int itemsPerPage) throws ClassNotFoundException, SQLException {
        return albumDAO.listAllAlbum(pageNumber, itemsPerPage);
    }
}
