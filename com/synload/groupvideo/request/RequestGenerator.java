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
          case 'à':
            sb.append("&agrave;");
            break;
          case 'À':
            sb.append("&Agrave;");
            break;
          case 'â':
            sb.append("&acirc;");
            break;
          case 'Â':
            sb.append("&Acirc;");
            break;
          case 'ä':
            sb.append("&auml;");
            break;
          case 'Ä':
            sb.append("&Auml;");
            break;
          case 'å':
            sb.append("&aring;");
            break;
          case 'Å':
            sb.append("&Aring;");
            break;
          case 'æ':
            sb.append("&aelig;");
            break;
          case 'Æ':
            sb.append("&AElig;");
            break;
          case 'ç':
            sb.append("&ccedil;");
            break;
          case 'Ç':
            sb.append("&Ccedil;");
            break;
          case 'é':
            sb.append("&eacute;");
            break;
          case 'É':
            sb.append("&Eacute;");
            break;
          case 'è':
            sb.append("&egrave;");
            break;
          case 'È':
            sb.append("&Egrave;");
            break;
          case 'ê':
            sb.append("&ecirc;");
            break;
          case 'Ê':
            sb.append("&Ecirc;");
            break;
          case 'ë':
            sb.append("&euml;");
            break;
          case 'Ë':
            sb.append("&Euml;");
            break;
          case 'ï':
            sb.append("&iuml;");
            break;
          case 'Ï':
            sb.append("&Iuml;");
            break;
          case 'ô':
            sb.append("&ocirc;");
            break;
          case 'Ô':
            sb.append("&Ocirc;");
            break;
          case 'ö':
            sb.append("&ouml;");
            break;
          case 'Ö':
            sb.append("&Ouml;");
            break;
          case 'ø':
            sb.append("&oslash;");
            break;
          case 'Ø':
            sb.append("&Oslash;");
            break;
          case 'ß':
            sb.append("&szlig;");
            break;
          case 'ù':
            sb.append("&ugrave;");
            break;
          case 'Ù':
            sb.append("&Ugrave;");
            break;
          case 'û':
            sb.append("&ucirc;");
            break;
          case 'Û':
            sb.append("&Ucirc;");
            break;
          case 'ü':
            sb.append("&uuml;");
            break;
          case 'Ü':
            sb.append("&Uuml;");
            break;
          case '®':
            sb.append("&reg;");
            break;
          case '©':
            sb.append("&copy;");
            break;
          case '€':
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