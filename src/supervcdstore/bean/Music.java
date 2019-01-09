/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supervcdstore.bean;

/**
 *
 * @author Administrator
 * 这是一个VCD的歌曲信息相关类
 */
public class Music {
    private String musicId;
    private String musicName;
    private String musicTime;

    public Music() {
    }

    public Music(String musicId, String musicName, String musicTime) {
        this.musicId = musicId;
        this.musicName = musicName;
        this.musicTime = musicTime;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicTime() {
        return musicTime;
    }

    public void setMusicTime(String musicTime) {
        this.musicTime = musicTime;
    }

   
    
}
