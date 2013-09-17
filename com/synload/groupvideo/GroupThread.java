/*
 * Synload Development Services
 * September 16, 2013
 * Nathaniel Davidson <nathaniel.davidson@gmail.com>
 * http://synload.com
*/
package com.synload.groupvideo;

import java.util.Date;

public class GroupThread extends Thread {
    private Group group = null;
    private Server server = null; 
    public GroupThread( Server serv, Group group){
        this.group=group;
        this.server=serv;
    }
    @Override
    public void run(){
        while(true){
            group.sendData(server.jsonbuilder.pulse());
            group.syncReTime = new Date().getTime();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}