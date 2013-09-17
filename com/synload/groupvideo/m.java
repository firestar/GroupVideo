/*
 * Synload Development Services
 * September 16, 2013
 * Nathaniel Davidson <nathaniel.davidson@gmail.com>
 * http://synload.com
*/
package com.synload.groupvideo;

import java.net.InetSocketAddress;

import org.java_websocket.drafts.Draft_76;


public class m {
    public m(){

    }
    public static void main(String[] args) {
        int port = 5000;
        Server s = null;
        InetSocketAddress f = new InetSocketAddress("0.0.0.0",port);
        s = new Server( f , new Draft_76());
        s.start();
        System.out.println("Started server on port: " + s.getPort());
    }
}