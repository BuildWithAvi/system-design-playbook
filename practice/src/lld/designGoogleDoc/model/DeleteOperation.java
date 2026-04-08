package lld.designGoogleDoc.model;

public class DeleteOperation extends Operation {

    protected int length;

    public DeleteOperation(int position, int length, int version) {
        super(position, version);
        this.length = length;
    }

    @Override
    public void apply(StringBuilder doc) {

        if (position >= doc.length()) return;

        int end = Math.min(position + length, doc.length());

        if (position < end) {
            doc.delete(position, end);
        }
    }

    @Override
    public Operation transformAgainst(Operation other) {

        if (other instanceof InsertOperation o) {

            int insertPos = o.getPosition();
            int insertLen = o.getText().length();

            int newPos = this.position;

            if (insertPos <= this.position) {
                newPos = this.position + insertLen;
            }
            newPos = Math.min(newPos, this.position);

            return new DeleteOperation(newPos, this.length, this.version);
        }

        // Delete vs Delete
        if (other instanceof DeleteOperation o) {

            int thisStart = this.position;
            int thisEnd = this.position + this.length;

            int otherStart = o.position;
            int otherEnd = o.position + o.length;

            if (otherEnd <= thisStart) {
                return new DeleteOperation(
                        this.position - o.length,
                        this.length,
                        this.version
                );
            }

            if (otherStart >= thisEnd) {
                return this;
            }

            // overlap
            int overlapStart = Math.max(thisStart, otherStart);
            int overlapEnd = Math.min(thisEnd, otherEnd);

            int overlap = overlapEnd - overlapStart;

            return new DeleteOperation(
                    this.position,
                    Math.max(0, this.length - overlap),
                    this.version
            );
        }

        return this;
    }

    @Override
    public String toString() {
        return "Delete(pos=" + position + ", length=" + length + ", version=" + version + ")";
    }
}