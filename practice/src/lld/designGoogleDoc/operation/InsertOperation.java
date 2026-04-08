package lld.designGoogleDoc.operation;

class InsertOperation extends Operation {
    private final String text;

    public InsertOperation(int position, String text, int version){
        super(position,version);
        this.text = text;
    }

    @Override
    public void apply(StringBuilder doc){
        doc.insert(positions,text);
    }

    @Override
    public Operation transformAgainst(Operation other){
        if(other instanceof InsertOperation o){
            if(o.positions < this.positions || (o.positions == this.positions)){
                return new InsertOperation(this.positions + o.text.length(),this.text,this.version);
            }
        }
        if(other instanceof DeleteOperation o){
            if(this.positions > o.positions + o.length) {
                return new InsertOperation(
                        this.positions - o.length,
                        this.text,
                        this.version
                );
            }
            if(this.positions > o.positions){
                return new InsertOperation(o.positions,this.text,this.version);
            }
        }
        return this;
    }

    public String getText() {
        return text;
    }
}
