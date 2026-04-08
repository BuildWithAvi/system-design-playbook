package lld.designGoogleDoc.handler;

// Pseudo Spring WebSocket handler

import lld.designGoogleDoc.model.Operation;
import lld.designGoogleDoc.service.DocumentService;

public class WebSocketHandler {

    private final DocumentService service = new DocumentService();

    public void onMessage(String docId, Operation op) {

        Operation transformed = service.handleOperation(docId, op);
        broadcast(docId, transformed);
    }

    private void broadcast(String docId, Operation op) {
        System.out.println("Broadcasting: " + op);
    }
}