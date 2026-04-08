package lld.designGoogleDoc.model;

public class InsertOperation extends Operation {

    private final String text;

    public InsertOperation(int position, String text, int version) {
        super(position, version);
        this.text = text;
    }

    @Override
    public void apply(StringBuilder doc) {
        int pos = Math.min(position, doc.length());
        doc.insert(pos, text);
    }

    @Override
    public Operation transformAgainst(Operation other) {

        // Insert vs Insert
        if (other instanceof InsertOperation o) {
            if (o.position < this.position ||
                    (o.position == this.position)) {
                return new InsertOperation(
                        this.position + o.text.length(),
                        this.text,
                        this.version
                );
            }
        }

        // Insert vs Delete
        if (other instanceof DeleteOperation o) {

            if (this.position > o.position + o.length) {
                return new InsertOperation(
                        this.position - o.length,
                        this.text,
                        this.version
                );
            }

            if (this.position > o.position) {
                return new InsertOperation(
                        o.position,
                        this.text,
                        this.version
                );
            }
        }

        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Insert(pos=" + position + ", text=\"" + text + "\", version=" + version + ")";
    }
}