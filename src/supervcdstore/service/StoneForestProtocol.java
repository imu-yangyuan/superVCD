/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supervcdstore.service;

/**
 *
 * @author Administrator
 */
public interface StoneForestProtocol {
    	public static final int OP_GET_MUSIC_CATEGORIES = 100;
	public static final int OP_GET_MUSIC_RECORDINGS = 101;
	
	public static final int DEFAULT_PORT = 5150;
	public static final String DEFAULT_HOST = "localhost"; 
}
