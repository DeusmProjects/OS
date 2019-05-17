class Block {

    private BlockState state;
    private int next;

    public Block(int next) {
        this.next = next;

        if (next == -1) {
            state = BlockState.FREE;
        }
        else {
            state = BlockState.OCCUPIED;
        }
    }

    public BlockState getState() {
        return state;
    }

    public void setState(BlockState state) {
        this.state = state;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public boolean isEnd() {
        return next == -1;
    }

}
