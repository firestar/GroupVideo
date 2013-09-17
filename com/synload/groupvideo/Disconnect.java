/*
 * Synload Development Services
 * September 16, 2013
 * Nathaniel Davidson <nathaniel.davidson@gmail.com>
 * http://synload.com
*/
package com.synload.groupvideo;

import org.java_websocket.WebSocket;



public class Disconnect extends Thread {
    WebSocket thisSocket = null;
    public Disconnect(WebSocket socket){
        thisSocket = socket;
    }
    @Override
    @SuppressWarnings("static-access")
    public void run() {
        try {
            this.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        thisSocket.close(4);
    }
}