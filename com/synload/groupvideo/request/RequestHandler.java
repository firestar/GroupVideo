/*
 * Synload Development Services
 * September 16, 2013
 * Nathaniel Davidson <nathaniel.davidson@gmail.com>
 * http://synload.com
*/
package com.synload.groupvideo.request;


import java.nio.channels.NotYetConnectedException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.*;

import org.java_websocket.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

import com.synload.groupvideo.Disconnect;
import com.synload.groupvideo.Group;
import com.synload.groupvideo.Server;
import com.synload.groupvideo.users.User;

public class RequestHandler{
    public RequestHandler(){
        
    }
    public String translateRequest(WebSocket conn,String req, Server server){
        JSONObject obj = null;
        JSONObject retJSON = new JSONObject();
        try {
            obj = new JSONObject(req);
            if(obj.has("action")){
                if(obj.getString("action").equalsIgnoreCase("group_list")){
                    HashMap<String,HashMap<String,String>> data = new HashMap<String,HashMap<String,String>>();
                    retJSON = new JSONObject();
                    retJSON.put("action", "group_list");
                    for(Entry<String, Group> group: server.activeGroups.entrySet()){
                        if(group.getValue().privateSession==false){
                            HashMap<String,String> tmp = new HashMap<String,String>();
                            tmp.put("title", group.getValue().groupTitle);
                            tmp.put("viewers", String.valueOf(group.getValue().clients.size()));
                            tmp.put("video", group.getValue().groupVideo);
                            tmp.put("owner", server.clientName.get(group.getValue().getOwner()));
                            data.put(group.getKey(),tmp);
                        }
                    }
                    retJSON.put("groups", data);
                    conn.send(retJSON.toString());
                }else if(obj.getString("action").equalsIgnoreCase("login")){
                    boolean foundMatch = false;
                    try {
                        Pattern regex = Pattern.compile("<(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
                        Matcher regexMatcher = regex.matcher(obj.getString("name"));
                        foundMatch = regexMatcher.find();
                    } catch (PatternSyntaxException ex) {
                    }
                    String UName=null;
                    try {
                        UName = obj.getString("name").replaceAll("(?i)<", "&lt;");
                    } catch (PatternSyntaxException ex) {
                    } catch (IllegalArgumentException ex) {
                    } catch (IndexOutOfBoundsException ex) {
                    }
                    if(!server.clientName.containsValue(UName) && !foundMatch ){
                        server.clientName.put(conn, UName);
                        conn.send(server.jsonbuilder.user_accepted());
                    }else{
                        conn.send(server.jsonbuilder.user_denied());
                        conn.send(server.jsonbuilder.message("System", "Someone is already using that nickname!"));
                    }
                }else if(obj.getString("action").equalsIgnoreCase("message")){
                    System.out.println(server.clientName.get(conn)+" sent a message to the group "+server.clientGroup.get(conn));
                    if(server.clientGroup.containsKey(conn)){
                        Group group = server.getGroup(server.clientGroup.get(conn));
                        group.sendData(server.jsonbuilder.message(server.clientName.get(conn), obj.getString("text")));
                    }
                }else if(obj.getString("action").equalsIgnoreCase("load")){
                    if(server.clientGroup.containsKey(conn)){
                        Group group = server.getGroup(server.clientGroup.get(conn));
                        if(group.getOwner()==conn){
                            group.sendData(server.jsonbuilder.load(obj.getString("movie_id"),obj.getString("title")));
                            group.groupTitle = obj.getString("title");
                            group.groupVideo = obj.getString("movie_id");
                            System.out.println(server.clientName.get(conn)+" changed the video to \""+obj.getString("title")+"\"");
                        }else{
                            //add vote here!
                        }
                    }
                }else if(obj.getString("action").equalsIgnoreCase("return_pulse")){
                    if(server.clientGroup.containsKey(conn)){
                        Group group = server.getGroup(server.clientGroup.get(conn));
                        group.updateTime(conn, Float.valueOf(obj.getString("time")));
                    }
                }else if(obj.getString("action").equalsIgnoreCase("change_owner")){
                    if(server.clientGroup.containsKey(conn)){
                        Group group = server.getGroup(server.clientGroup.get(conn));
                        if(group.getOwner()==conn){
                            for(WebSocket cl: group.clients){
                                if(server.clientName.get(cl).equals(obj.getString("new_owner"))){
                                    group.setOwner(cl);
                                }
                            }

                        }
                    }
                }else if(obj.getString("action").equalsIgnoreCase("kick")){
                    if(server.clientGroup.containsKey(conn)){
                        Group group = server.getGroup(server.clientGroup.get(conn));
                        if(group.getOwner()==conn){
                            for(WebSocket cl: group.clients){
                                if(server.clientName.get(cl).equalsIgnoreCase(obj.getString("name"))){
                                    User user = new User(cl,server);
                                    user.sendResponseData(server.jsonbuilder.disconnect(obj.getString("reason")));
                                    (new Disconnect(cl)).start();
                                }
                            }

                        }
                    }
                }else if(obj.getString("action").equalsIgnoreCase("ban")){
                    if(server.clientGroup.containsKey(conn)){
                        Group group = server.getGroup(server.clientGroup.get(conn));
                        if(group.getOwner()==conn){
                            for(WebSocket cl: group.clients){
                                if(server.clientName.get(cl).equals(obj.getString("name"))){
                                    User user = new User(cl,server);
                                    user.sendResponseData(server.jsonbuilder.disconnect("You have been banned by this group!"));
                                    group.bans.add(cl.getRemoteSocketAddress().getAddress().getHostAddress());
                                    (new Disconnect(cl)).start();
                                }
                            }

                        }
                    }
                }else if(obj.getString("action").equalsIgnoreCase("ban_list")){
                    if(server.clientGroup.containsKey(conn)){
                        Group group = server.getGroup(server.clientGroup.get(conn));
                        if(group.getOwner()==conn){
                            User user = new User(conn,server);
                            retJSON = new JSONObject();
                            retJSON.put("action", "banlist");
                            retJSON.put("list", group.bans);
                            user.sendResponseData(retJSON.toString());
                        }
                    }
                }else if(obj.getString("action").equalsIgnoreCase("ban_remove")){
                    if(server.clientGroup.containsKey(conn)){
                        Group group = server.getGroup(server.clientGroup.get(conn));
                        if(group.getOwner()==conn){
                            if(group.bans.contains(obj.getString("ip"))){
                                group.bans.remove(obj.getString("ip"));
                            }
                        }
                    }
                }else if(obj.getString("action").equalsIgnoreCase("group_join")){
                    String userName = server.clientName.get(conn);
                    String groupName = obj.getString("name");
                    System.out.println(userName+" joined the group "+groupName);
                    Group group = server.getGroup(groupName);
                    try {
                        if(!group.bans.contains(conn.getRemoteSocketAddress().getAddress().getHostAddress())){
                            try {
                                retJSON = new JSONObject();
                                retJSON.put("action", "accept");
                                conn.send(retJSON.toString());
                            } catch (NotYetConnectedException e) {
                                e.printStackTrace();
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            }
                        }else if(!userName.matches("(?im)([a-zA-Z0-9]+)")){
                            try {
                                retJSON = new JSONObject();
                                retJSON.put("action", "improper");
                                conn.send(retJSON.toString());
                            } catch (NotYetConnectedException e) {
                                e.printStackTrace();
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            }
                            conn.close(2);
                            return "";
                        }else if(group.bans.contains(conn.getRemoteSocketAddress().getAddress().getHostAddress())){
                            try {
                                retJSON = new JSONObject();
                                retJSON.put("action", "banned");
                                conn.send(retJSON.toString());
                            } catch (NotYetConnectedException e) {
                                e.printStackTrace();
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            }
                            conn.close(2);
                            return "";
                        }else{
                            try {
                                retJSON = new JSONObject();
                                retJSON.put("action", "deny");
                                conn.send(retJSON.toString());
                            } catch (NotYetConnectedException e) {
                                e.printStackTrace();
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            }
                            conn.close(2);
                            return "";
                        }
                    } catch (PatternSyntaxException ex) {
                        try {
                            retJSON = new JSONObject();
                            retJSON.put("action", "deny");
                            conn.send(retJSON.toString());
                        } catch (NotYetConnectedException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                        conn.close(2);
                        return "";
                    }
                    group.sendData(server.jsonbuilder.new_user(userName));
                    group.addClient(conn);
                    server.clientGroup.put(conn, groupName);
                    String users = "";
                    for(WebSocket cl: group.clients){
                        if(users.equals("")){
                            users = server.clientName.get(cl);
                        }else{
                            users += ","+server.clientName.get(cl);
                        }
                    }
                    try {
                        conn.send(server.jsonbuilder.load(group.groupVideo, group.groupTitle));
                        conn.send(server.jsonbuilder.seek(String.valueOf(group.leadPosition)));
                        conn.send(server.jsonbuilder.new_owner(server.clientName.get(group.getOwner())));
                    } catch (NotYetConnectedException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    retJSON = new JSONObject();
                    retJSON.put("action", "player_list");
                    retJSON.put("users", users);
                    conn.send(retJSON.toString());
                }else if(obj.getString("action").equalsIgnoreCase("user_action")){
                    if(obj.getString("video").equalsIgnoreCase("seek")){
                        if(server.clientGroup.containsKey(conn)){
                            Group group = server.getGroup(server.clientGroup.get(conn));
                            if(group.getOwner().equals(conn)){
                                group.sendData(server.jsonbuilder.seek(String.valueOf(Float.valueOf(obj.getString("time"))+group.delays.get(conn)+group.delays.get(group.Owner))));
                                group.lastAction="play";
                                System.out.println(server.clientName.get(conn)+" seeked "+server.clientGroup.get(conn));
                            }
                        }
                    }else if(obj.getString("video").equalsIgnoreCase("play")){
                        if(server.clientGroup.containsKey(conn)){
                            Group group = server.getGroup(server.clientGroup.get(conn));
                            if(group.getOwner().equals(conn)){
                                group.sendData(server.jsonbuilder.play());
                                group.lastAction="play";
                                System.out.println(server.clientName.get(conn)+" sent play command to "+server.clientGroup.get(conn));
                            }
                        }
                    }else if(obj.getString("video").equalsIgnoreCase("pause")){
                        if(server.clientGroup.containsKey(conn)){
                            Group group = server.getGroup(server.clientGroup.get(conn));
                            if(group.getOwner().equals(conn)){
                                group.sendData(server.jsonbuilder.pause());
                                group.lastAction="pause";
                                System.out.println(server.clientName.get(conn)+" sent pause command "+server.clientGroup.get(conn));
                            }
                        }
                    }else if(obj.getString("video").equalsIgnoreCase("buffering")){
                        if(server.clientGroup.containsKey(conn)){
                            Group group = server.getGroup(server.clientGroup.get(conn));
                            if(group.getOwner().equals(conn)){
                                group.sendData(server.jsonbuilder.pause());
                                group.lastAction="pause";
                                System.out.println(server.clientName.get(conn)+" sent pause command due to buffer to "+server.clientGroup.get(conn));
                            }
                        }
                    }
                }else if(obj.getString("action").equalsIgnoreCase("quit")){
                    conn.close(2);
                }
            }
            if(server.clientName.get(conn)!=null){
                System.out.println(server.clientName.get(conn)+" sent "+req);
            }else{
                System.out.println(conn+" sent "+req);
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}