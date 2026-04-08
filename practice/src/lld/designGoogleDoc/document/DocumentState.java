package lld.designGoogleDoc.document;

import lld.designGoogleDoc.model.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class DocumentState {

    private final StringBuilder document = new StringBuilder();
    private final List<Operation> history = new ArrayList<>();
    private int version = 0;

    private final ReentrantLock lock = new ReentrantLock();

    public DocumentState(String initialText) {
        document.append(initialText);
    }

    public Operation applyOperation(Operation incomingOp) {

        lock.lock();
        try {
            Operation transformed = incomingOp;

            // Transform against missed operations
            for (int i = incomingOp.getVersion(); i < history.size(); i++) {
                transformed = transformed.transformAgainst(history.get(i));
            }

            transformed.apply(document);

            history.add(transformed);
            version++;

            return transformed;

        } finally {
            lock.unlock();
        }
    }

    public int getVersion() {
        return version;
    }

    public String getDocument() {
        return document.toString();
    }
}