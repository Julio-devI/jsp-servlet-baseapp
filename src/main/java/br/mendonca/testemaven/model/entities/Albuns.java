package br.mendonca.testemaven.model.entities;

public class Albuns {

    private String uuid;
    private String nome;
    private int reproducoes;
    private boolean maisReproduzido;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getReproducoes() {
        return reproducoes;
    }

    public void setReproducoes(int reproducoes) {
        this.reproducoes = reproducoes;
    }

    public boolean isMaisReproduzido() {
        return maisReproduzido;
    }

    public void setMaisReproduzido(boolean maisReproduzido) {
        this.maisReproduzido = maisReproduzido;
    }
}
