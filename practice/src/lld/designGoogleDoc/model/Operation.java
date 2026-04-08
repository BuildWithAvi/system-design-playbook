package lld.designGoogleDoc.model;

import java.io.Serializable;

public abstract class Operation implements Serializable {

    protected int position;
    protected int version;

    public Operation(int position, int version) {
        this.position = position;
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public int getPosition() {
        return position;
    }

    public abstract void apply(StringBuilder doc);

    public abstract Operation transformAgainst(Operation other);
}