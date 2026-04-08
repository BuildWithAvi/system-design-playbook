package lld.designGoogleDoc.service;

import lld.designGoogleDoc.document.DocumentState;
import lld.designGoogleDoc.model.Operation;

import java.util.concurrent.ConcurrentHashMap;

public class DocumentService {

    private final ConcurrentHashMap<String, DocumentState> docs = new ConcurrentHashMap<>();

    public DocumentState getOrCreate(String docId) {
        return docs.computeIfAbsent(docId, id -> new DocumentState(""));
    }

    public Operation handleOperation(String docId, Operation op) {
        return getOrCreate(docId).applyOperation(op);
    }
}