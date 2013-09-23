/*
 * Synload Development Services
 * September 16, 2013
 * Nathaniel Davidson <nathaniel.davidson@gmail.com>
 * http://synload.com
*/
package com.synload.groupvideo.request;

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
            retJSON.put("name", escapeHTML(name));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String left(String user){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "user_left");
            retJSON.put("name", escapeHTML(user));
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
            retJSON.put("name", escapeHTML(owner));
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
            retJSON.put("movie_url", movie_id);
            retJSON.put("title", escapeHTML(title));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String message(String name, String msg){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "message");
            retJSON.put("name", escapeHTML(name));
            retJSON.put("text", escapeHTML(msg));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJSON.toString();
    }
    public String disconnect(String reason){
        JSONObject retJSON = new JSONObject();
        try {
            retJSON.put("action", "disconnect");
            retJSON.put("reason", escapeHTML(reason));
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
    public String escapeHTML(String s) {
        StringBuffer sb = new StringBuffer();
        int n = s.length();
        for (int i = 0; i < n; i++) {
          char c = s.charAt(i);
          switch (c) {
          case '<':
            sb.append("&lt;");
            break;
          case '>':
            sb.append("&gt;");
            break;
          case '&':
            sb.append("&amp;");
            break;
          case '"':
            sb.append("&quot;");
            break;
          case '�':
            sb.append("&agrave;");
            break;
          case '�':
            sb.append("&Agrave;");
            break;
          case '�':
            sb.append("&acirc;");
            break;
          case '�':
            sb.append("&Acirc;");
            break;
          case '�':
            sb.append("&auml;");
            break;
          case '�':
            sb.append("&Auml;");
            break;
          case '�':
            sb.append("&aring;");
            break;
          case '�':
            sb.append("&Aring;");
            break;
          case '�':
            sb.append("&aelig;");
            break;
          case '�':
            sb.append("&AElig;");
            break;
          case '�':
            sb.append("&ccedil;");
            break;
          case '�':
            sb.append("&Ccedil;");
            break;
          case '�':
            sb.append("&eacute;");
            break;
          case '�':
            sb.append("&Eacute;");
            break;
          case '�':
            sb.append("&egrave;");
            break;
          case '�':
            sb.append("&Egrave;");
            break;
          case '�':
            sb.append("&ecirc;");
            break;
          case '�':
            sb.append("&Ecirc;");
            break;
          case '�':
            sb.append("&euml;");
            break;
          case '�':
            sb.append("&Euml;");
            break;
          case '�':
            sb.append("&iuml;");
            break;
          case '�':
            sb.append("&Iuml;");
            break;
          case '�':
            sb.append("&ocirc;");
            break;
          case '�':
            sb.append("&Ocirc;");
            break;
          case '�':
            sb.append("&ouml;");
            break;
          case '�':
            sb.append("&Ouml;");
            break;
          case '�':
            sb.append("&oslash;");
            break;
          case '�':
            sb.append("&Oslash;");
            break;
          case '�':
            sb.append("&szlig;");
            break;
          case '�':
            sb.append("&ugrave;");
            break;
          case '�':
            sb.append("&Ugrave;");
            break;
          case '�':
            sb.append("&ucirc;");
            break;
          case '�':
            sb.append("&Ucirc;");
            break;
          case '�':
            sb.append("&uuml;");
            break;
          case '�':
            sb.append("&Uuml;");
            break;
          case '�':
            sb.append("&reg;");
            break;
          case '�':
            sb.append("&copy;");
            break;
          case '�':
            sb.append("&euro;");
            break;
          case ' ':
            sb.append("&nbsp;");
            break;

          default:
            sb.append(c);
            break;
          }
        }
        return sb.toString();
      }
}