package lld.designGoogleDoc.operation;

import java.io.Serializable;

abstract class Operation implements Serializable {

    protected int positions;
    protected int version;

    public Operation(int positions,int version){
        this.positions = positions;
        this.version = version;
    }

    public int getVersion(){
        return version;
    }

    public abstract void apply(StringBuilder doc);

    public abstract Operation transformAgainst(Operation other);
}
