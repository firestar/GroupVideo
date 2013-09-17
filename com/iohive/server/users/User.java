/*
 * Synload Development Services
 * September 16, 2013
 * Nathaniel Davidson <nathaniel.davidson@gmail.com>
 * http://synload.com
*/
package com.iohive.server.users;
import it.sauronsoftware.base64.Base64;

import java.nio.channels.NotYetConnectedException;

import org.java_websocket.WebSocket;

import com.iohive.server.Server;



public class User{
    Base64 base64 = new Base64();
    Server server = null;
    WebSocket main = null;
    public User(WebSocket conn, Server serv){
        main = conn;
        server = serv;
    }
    public void sendMessage(String message, String from){
        try {
            main.send(server.jsonbuilder.message(from, message));
        } catch (NotYetConnectedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void removeGroup(String key){

    }
    public void sendResponseData(String data){
        try {
            main.send(data);
        } catch (NotYetConnectedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}