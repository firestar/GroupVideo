/*
 * Synload Development Services
 * September 16, 2013
 * Nathaniel Davidson <nathaniel.davidson@gmail.com>
 * http://synload.com
*/
package com.iohive.server;


import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map.Entry;

import org.java_websocket.WebSocket;

import com.iohive.server.users.User;


public class Group{
    public WebSocket Owner = null;
    private Server server = null;
    public String groupVideo = null;
    public String groupTitle = null; 
    public boolean privateSession = false;
    public String password = "";
    public long syncReTime = 0;
    public String lastAction = null;
    public float leadPosition = 0;
    public GroupThread thread = null;
    public ArrayList<WebSocket> clients = new ArrayList<WebSocket>();
    public ArrayList<String> bans = new ArrayList<String>();
    public Hashtable<WebSocket, Float> times = new Hashtable<WebSocket, Float>();
    public Hashtable<WebSocket, Float> delays = new Hashtable<WebSocket, Float>();
    public Group(Server serv){
        server = serv;
        thread = new GroupThread(server,this);
        thread.start();

    }
    public void addClient(WebSocket conn){
        if(clients.isEmpty()){
            Owner=conn;
            clients.add(conn);
            conn.send(server.jsonbuilder.message("System", "Session started"));
        }else{
            if(!clients.contains(conn)){
                this.sendMessage(server.clientName.get(conn)+" has joined!", "System");
                clients.add(conn);
            }
        }
    }
    public void updateTime(WebSocket conn, Float time){
        if(!times.containsKey(conn)){
            times.put(conn, time);
            long currtime = new Date().getTime();
            delays.put(conn, (float) Math.ceil((currtime-this.syncReTime)/2)/1000);
            //System.out.println(server.clientName.get(conn)+" at "+Strin[.valueOf(time)+" in the group "+server.clientGroup.get(conn)+"!");
        }
        if(times.size()==clients.size()){
            this.timeCheck();
        }
    }
    public void timeCheck(){
        ArrayList<String> usersTMP = new ArrayList<String>();
        Float ownerTime = times.get(Owner)+delays.get(this.Owner);
        for(Entry<WebSocket, Float> time : times.entrySet()){
            if(time.getValue()>(ownerTime+delays.get(time.getKey())) || time.getValue()<((ownerTime+delays.get(time.getKey()))-3)){
                //System.out.println(server.clientName.get(time.getKey())+" behind in the group "+server.clientGroup.get(time.getKey())+"!");
                usersTMP.add("2:"+server.clientName.get(time.getKey()));
                try {
                    time.getKey().send(server.jsonbuilder.seek(String.valueOf((ownerTime+1))));
                } catch (NotYetConnectedException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }else{
                usersTMP.add("1:"+server.clientName.get(time.getKey()));
            }
        }
        if(lastAction!=null){
            if(lastAction.equalsIgnoreCase("play")){
                this.sendData(server.jsonbuilder.play());
            }else{
                this.sendData(server.jsonbuilder.pause());
            }
        }else{
            this.sendData(server.jsonbuilder.pause());
        }
        ArrayList<String> sb = new ArrayList<String>();
        for (int i = 0; i < usersTMP.size(); i++) {
            sb.add(usersTMP.get(i));
        }
        this.sendData(server.jsonbuilder.user_list(sb));
        times = new Hashtable<WebSocket, Float>();
    }
    public void removeClient(WebSocket conn){
        if(clients.contains(conn)){
            clients.remove(conn);
            if(this.empty()){
                thread.interrupt();
            }
        }
    }
    public void sendMessage(String message, String from){
        User user = null;
        for(WebSocket conn: clients){
            user = new User(conn, server);
            user.sendMessage(message, from);
        }
    }
    public void sendData(String data){
        User user = null;
        for(WebSocket conn: clients){
            user = new User(conn, server);
            user.sendResponseData(data);
        }
    }
    public WebSocket getOwner(){
        return Owner;
    }
    public void setOwner(WebSocket conn){
        sendMessage(server.clientName.get(conn)+" has become the new owner!", "System");
        Owner = conn;
        sendData(server.jsonbuilder.new_owner(server.clientName.get(Owner)));
    }
    public boolean empty(){
        if(clients.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}