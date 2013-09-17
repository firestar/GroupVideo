/*
 * Synload Development Services
 * September 16, 2013
 * Nathaniel Davidson <nathaniel.davidson@gmail.com>
 * http://synload.com
*/
package com.iohive.server.request;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestGenerator{
    public RequestGenerator(){
    }
    public String seek(String time){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "seek");
            retJSON.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String new_user(String name){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "new_user");
            retJSON.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String left(String user){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "user_left");
            retJSON.put("name", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String pulse(){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "pulse");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String user_accepted(){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "user_accepted");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String user_denied(){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "user_denied");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String user_nick(){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "user_nick");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String new_owner(String owner){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "new_owner");
            retJSON.put("name", owner);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String play(){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "play");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String pause(){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "pause");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String load(String movie_id, String title){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "load");
            retJSON.put("movie_id", movie_id);
            retJSON.put("title", title);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String message(String name, String msg){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "message");
            retJSON.put("name", name);
            retJSON.put("text", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String disconnect(String reason){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "disconnect");
            retJSON.put("reason", reason);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String user_list(Object ulist){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "user_list");
            retJSON.put("users", ulist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
}