package br.mendonca.testemaven.model.entities;

public class Musica {
    
    private String uuid;
	private String nomeMusica;
	private Integer visualizacao;
	private Boolean musicaFavorita;

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
	public String getNomeMusica() {
		return nomeMusica;
	}
	public void setNomeMusica(String nomeMusica) {
		this.nomeMusica = nomeMusica;
	}
	public Integer getVisualizacao() {
		return visualizacao;
	}
	public void setVisualizacao(Integer visualizacao) {
		this.visualizacao = visualizacao;
	}
	public Boolean getMusicaFavorita() {
		return musicaFavorita;
	}
	public void setMusicaFavorita(Boolean musicaFavorita) {
		is.musicaFavorita = musicaFavorita;
	}

}
