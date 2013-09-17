/*
 * Synload Development Services
 * September 16, 2013
 * Nathaniel Davidson <nathaniel.davidson@gmail.com>
 * http://synload.com
*/
package com.iohive.server;
import it.sauronsoftware.base64.Base64;

import java.net.InetSocketAddress;
import java.util.Hashtable;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.iohive.server.request.RequestGenerator;
import com.iohive.server.request.RequestHandler;
import com.iohive.server.users.User;


public class Server extends WebSocketServer {
    public Server( InetSocketAddress address, Draft d ) {
        super( address );
    }
    public Group newGroup(String name){
        Group newGroup = new Group(this);
        newGroup.groupTitle = name;
        activeGroups.put(name, newGroup);
        return newGroup;
    }
    public Group getGroup(String name){
        if(!activeGroups.containsKey(name)){
            return this.newGroup(name);
        }else{
            return activeGroups.get(name);
        }
    }
    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }
    private RequestHandler request = new RequestHandler();
    public Hashtable<String, Group> activeGroups = new Hashtable<String, Group>();
    public Hashtable<WebSocket, String> clientGroup = new Hashtable<WebSocket, String>();
    public Hashtable<WebSocket, String> clientName = new Hashtable<WebSocket, String>();
    public RequestGenerator jsonbuilder = new RequestGenerator();
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        User user = new User(conn, this);
        user.sendMessage("Welcome to AnimeCap!","System");
        user.sendResponseData(this.jsonbuilder.user_nick());
        System.out.println(conn+" connected");
    }
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        if(clientGroup.containsKey(conn)){
            Group group = getGroup(clientGroup.get(conn));
            group.removeClient(conn);
            group.sendData(this.jsonbuilder.left(clientName.get(conn)));
            if(group.empty()){
                group.thread.interrupt();
                System.out.println(clientGroup.get(conn)+" destroyed!");
                activeGroups.remove(clientGroup.get(conn));
                clientGroup.remove(conn);
            }else{
                if(group.getOwner().equals(conn)){
                    group.setOwner(group.clients.get(0));
                    System.out.println(clientName.get(group.clients.get(0))+" now owner of "+clientGroup.get(conn));
                }
                clientGroup.remove(conn);
                String users = "";
                for(WebSocket cl: group.clients){
                    if(users.equals("")){
                        users = clientName.get(cl);
                    }else{
                        users += ","+clientName.get(cl);
                    }
                }
                group.sendData("act=plylst&usrs="+Base64.encode(users));
            }
        }
        if(clientName.containsKey(conn)){
            System.out.println(clientName.get(conn)+" disconnected!");
            clientName.remove(conn);
        }
    }
    @Override
    public void onMessage(WebSocket conn, String message) {
        request.translateRequest(conn,message, this);
    }
}