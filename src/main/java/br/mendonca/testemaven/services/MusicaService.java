package br.mendonca.testemaven.services;

import br.mendonca.testemaven.dao.MusicaDAO;
import br.mendonca.testemaven.model.entities.Musica;

import java.sql.SQLException;
import java.util.List;

public class MusicaService {
    
    private final MusicaDAO musicaDAO;

    public MusicaService() {
        this.musicaDAO = new MusicaDAO();
    }

    public void register(String nomeMusica, Integer visualizacao, Boolean musicaFavorita) throws ClassNotFoundException, SQLException {
        if(nomeMusica == null || nomeMusica.isEmpty()) {
            throw new IllegalArgumentException("O nome da música não pode ser vazio");
        }

        if(visualizacao == null || visualizacao < 0) {
            throw new IllegalArgumentException("O número de visualizações não pode ser negativo");
        }

        Musica musica = new Musica();

        musica.setNomeMusica(nomeMusica);
        musica.setVisualizacao(visualizacao);
        musica.setMusicaFavorita(musicaFavorita);

        musicaDAO.registerMusica(musica);
    }

    public List<Musica> listAllMusicas() throws ClassNotFoundException, SQLException {
        return musicaDAO.listAllMusica();
    }
}
