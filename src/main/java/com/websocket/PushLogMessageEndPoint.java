package com.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anbabans on 2/4/2018.
 */
@ServerEndpoint(value="/latestlogs")
public class PushLogMessageEndPoint {
    private Map<String, MessagePublisher> sessionMap = new HashMap<String, MessagePublisher>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        MessagePublisher publisher = new MessagePublisher(session, null);
        sessionMap.put(session.getId(), publisher);
        publisher.startPublishing();
    }

//    @OnMessage
    public void onMessage(Session session, Message message) throws IOException {
//        MessagePublisher publisher = new MessagePublisher(session, message);
//        sessionMap.put(session.getId(), publisher);
//        publisher.startPublishing();
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        MessagePublisher publisher = sessionMap.get(session.getId());
        if(publisher != null) {
            publisher.setTurnOffPublishing(true);
            sessionMap.remove(session.getId());
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        MessagePublisher publisher = sessionMap.get(session.getId());
        if(publisher != null) {
            publisher.setTurnOffPublishing(true);
            sessionMap.remove(session.getId());
        }
        // Do error handling here
    }
}
