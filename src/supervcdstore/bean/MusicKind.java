/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supervcdstore.bean;

import java.util.List;

/**
 *
 * @author Administrator
 */
public class MusicKind {
    private String kindName;
    private List<MusicAlbum> musicAlbums;

    public MusicKind(String kindName, List<MusicAlbum> musicAlbums) {
        this.kindName = kindName;
        this.musicAlbums = musicAlbums;
    }

    public MusicKind() {
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public List<MusicAlbum> getMusicAlbums() {
        return musicAlbums;
    }

    public void setMusicAlbums(List<MusicAlbum> musicAlbums) {
        this.musicAlbums = musicAlbums;
    }
    
}
