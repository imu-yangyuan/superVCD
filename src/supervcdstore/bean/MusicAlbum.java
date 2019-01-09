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
 * 歌手名字
 * 专辑名字
 * 类型
 * 时间
 * 价格
 */
public class MusicAlbum  {
    private String id;
    private String singer;
    private String albumName;
    private String kind;
    private String time;
    private String price; 
    private List<Music> musics;

    public MusicAlbum() {
    }

    public MusicAlbum(String id, String singer, String albumName, String kind, String time, String price, List<Music> musics) {
        this.id = id;
        this.singer = singer;
        this.albumName = albumName;
        this.kind = kind;
        this.time = time;
        this.price = price;
        this.musics = musics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }
    

    

  
    
}
