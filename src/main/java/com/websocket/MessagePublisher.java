package com.websocket;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;

/**
 * Created by anbabans on 2/4/2018.
 */
public class MessagePublisher {
    private Session session;
    private Message message;

    public void setTurnOffPublishing(boolean turnOffPublishing) {
        this.turnOffPublishing = turnOffPublishing;
    }

    private boolean turnOffPublishing;

    public MessagePublisher(Session session, Message message) {
        this.session = session;
        this.message = message;
    }

    public void startPublishing() {
        Runnable runnable = new Runnable() {
            public void run() {
                while(!turnOffPublishing) {
                    try {
                        Thread.sleep(5000);
                        session.getBasicRemote().sendText("Current time: " + new Date().getTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

}
