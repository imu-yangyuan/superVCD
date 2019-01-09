/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import supervcdstore.bean.Music;
import supervcdstore.bean.MusicAlbum;
import supervcdstore.bean.MusicAlbumPath;
import supervcdstore.bean.MusicKind;
import supervcdstore.bean.MusicPath;

/**
 *
 * @author Administrator
 */
public class Test {
    public static void main(String[] args) {
        MusicKind musicKind;
        MusicAlbum musicAlbum;
        Music music;
        MusicPath musicPath;
        MusicAlbumPath musicAlbumPath;
        musicKind=new MusicKind();
        musicAlbum=new MusicAlbum();
        music=new Music();
        musicPath=new MusicPath();
        musicAlbumPath=new MusicAlbumPath();
       List<Music> musics=new ArrayList<Music>();
       List<MusicAlbum> musicAlbums=new ArrayList<MusicAlbum>();
       List<MusicKind>musicKinds=new ArrayList<MusicKind>();
       List<MusicAlbumPath>musicAlbumPaths=new ArrayList<MusicAlbumPath>();
       List<MusicPath>musicPaths=new ArrayList<MusicPath>();
        musicAlbum.setAlbumName("Maroon5");
        musicAlbum.setId(UUID.randomUUID().toString());
        musicAlbum.setKind("欧美");
        musicAlbum.setPrice("15");
        musicAlbum.setSinger("Maroon5");
        musicAlbum.setTime("60");
        musicAlbumPath.setAlbumId(musicAlbum.getId());
        musicAlbumPath.setAlbumPath("E:\\music\\欧美\\Maroon5");
        musicAlbumPath.setPhotoPath("E:\\music\\欧美\\Maroon5\\Maroon5.jpg");
        musicAlbumPaths.add(musicAlbumPath);
        
        music.setMusicId(UUID.randomUUID().toString());
        music.setMusicName("Feelings");
        music.setMusicTime("3");
        musics.add(music);
        musicPath.setMusicId(music.getMusicId());
        musicPath.setMusicPath("E:\\music\\欧美\\Maroon5\\Feelings.mp3");
        musicPaths.add(musicPath);
        
        music=new Music();
        music.setMusicId(UUID.randomUUID().toString());
        music.setMusicName("Lucky Strike");
        music.setMusicTime("4");
        musics.add(music);
        musicPath=new MusicPath();
        musicPath.setMusicId(music.getMusicId());
        musicPath.setMusicPath("E:\\music\\欧美\\Maroon5\\Lucky Strike.mp3");
        musicPaths.add(musicPath);
        
        music=new Music();
        music.setMusicId(UUID.randomUUID().toString());
        music.setMusicName("Sugar");
        music.setMusicTime("4");
        musics.add(music);
        musicPath=new MusicPath();
        musicPath.setMusicId(music.getMusicId());
        musicPath.setMusicPath("E:\\music\\欧美\\Maroon5\\Sugar.mp3");
        musicPaths.add(musicPath);
        
        musicAlbum.setMusics(musics);
        musicAlbums.add(musicAlbum);
        musicKind.setKindName("欧美");
        musicKind.setMusicAlbums(musicAlbums);
        musicKinds.add(musicKind);
        
        
        ///
        musicKind=new MusicKind();
        musicAlbum=new MusicAlbum();
        musics=new ArrayList<Music>();
        musicAlbums=new ArrayList<MusicAlbum>();
        musicAlbum.setAlbumName("薛之谦");
        musicAlbum.setId(UUID.randomUUID().toString());
        musicAlbum.setKind("华语");
        musicAlbum.setPrice("100");
        musicAlbum.setSinger("薛之谦");
        musicAlbum.setTime("150");
        musicAlbumPath=new MusicAlbumPath();
        musicAlbumPath.setAlbumId(musicAlbum.getId());
        musicAlbumPath.setAlbumPath("E:\\music\\华语\\薛之谦");
        musicAlbumPath.setPhotoPath("E:\\music\\华语\\薛之谦\\薛之谦.jpg");
        musicAlbumPaths.add(musicAlbumPath);
        music=new Music();
        music.setMusicId(UUID.randomUUID().toString());
        music.setMusicName("方圆几里");
        music.setMusicTime("3");
        musics.add(music);
        musicPath=new MusicPath();
        musicPath.setMusicId(music.getMusicId());
        musicPath.setMusicPath("E:\\music\\华语\\薛之谦\\方圆几里.mp3");
        musicPaths.add(musicPath);
        
        music=new Music();
        music.setMusicId(UUID.randomUUID().toString());
        music.setMusicName("其实");
        music.setMusicTime("4");
        musics.add(music);
        musicPath=new MusicPath();
        musicPath.setMusicId(music.getMusicId());
        musicPath.setMusicPath("E:\\music\\华语\\薛之谦\\其实.mp3");
        musicPaths.add(musicPath);
        music=new Music();
        music.setMusicId(UUID.randomUUID().toString());
        music.setMusicName("认真的雪");
        music.setMusicTime("4");
        musics.add(music);
        musicPath=new MusicPath();
        musicPath.setMusicId(music.getMusicId());
        musicPath.setMusicPath("E:\\music\\华语\\薛之谦\\认真的雪.mp3");
        musicPaths.add(musicPath);
        
        musicAlbum.setMusics(musics);
        musicAlbums.add(musicAlbum);
        musicKind.setKindName("华语");
        musicKind.setMusicAlbums(musicAlbums);
        musicKinds.add(musicKind);
        Gson gson=new Gson();
        String musicKindsJson=  gson.toJson(musicKinds);
        String musicPathJson=gson.toJson(musicPaths);
        String musicAlbumsPathJson=gson.toJson(musicAlbumPaths);
        System.out.println(musicKindsJson);
        System.out.println(musicPathJson);
        System.out.println(musicAlbumsPathJson);
       
       List<MusicKind> musicKinds1=gson.fromJson(musicKindsJson,new TypeToken<List<MusicKind>>() {}.getType());
        System.out.println(musicKinds1.get(0).getKindName());
        
    }
   
   
}
