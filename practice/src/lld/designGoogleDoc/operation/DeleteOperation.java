package lld.designGoogleDoc.operation;

class DeleteOperation extends Operation {
    protected int length;

    public DeleteOperation(int position, int length, int version) {
        super(position, version);
        this.length = length;
    }

    @Override
    public void apply(StringBuilder doc) {
        int end = Math.min(positions + length, doc.length());
        if (positions < doc.length()) {
            doc.delete(positions, end);
        }
    }

    @Override
    public Operation transformAgainst(Operation other) {

        if (other instanceof InsertOperation o) {
            if (o.positions <= this.positions) {
                return new DeleteOperation(this.positions + o.getText().length(), this.length, this.version);
            }
        }

        if (other instanceof DeleteOperation o) {

            int thisStart = this.positions;
            int thisEnd = this.positions + this.length;

            int otherStart = o.positions;
            int otherEnd = o.positions + o.length;

            if (otherEnd <= thisStart) {
                return new DeleteOperation(this.positions - o.length, this.length, this.version);
            }

            if (otherStart >= thisEnd) {
                return this;
            }

            int overlapStart = Math.max(thisStart, otherStart);
            int overlapEnd = Math.min(thisEnd, otherEnd);

            int overlap = overlapEnd - overlapStart;

            return new DeleteOperation(Math.min(thisStart, otherStart), Math.max(0, this.length - overlap), this.version
            );
        }

        return this;
    }
}