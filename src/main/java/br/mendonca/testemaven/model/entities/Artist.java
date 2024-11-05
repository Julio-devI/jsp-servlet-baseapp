package br.mendonca.testemaven.model.entities;

public class Artist {

    private String uuid;
    private String artistname;
    private Integer listeners;
    private Boolean active;

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getArtistname() {
        return artistname;
    }
    public void setArtistname(String name) {
        this.artistname = artistname;
    }
    public Integer getListeners() {
        return listeners;
    }
    public void setListeners(Integer listeners) {
        this.listeners = listeners;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
}
