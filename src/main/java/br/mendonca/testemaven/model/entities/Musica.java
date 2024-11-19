package br.mendonca.testemaven.model.entities;

public class Musica {
    
    private String uuid;
	private String nomeMusica;
	private int visualizacao;
	private boolean musicaFavorita;

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
	public int getvisualizacao() {
		return visualizacao;
	}
	public void setVisualizacao(int visualizacao) {
		this.visualizacao = visualizacao;
	}
	public boolean getMusicaFavorita() {
		return musicaFavorita;
	}
	//public void setMusicaFavorita(boolean musicaFavorita) {
		//is.musicaFavorita = musicaFavorita;
	//}

}
