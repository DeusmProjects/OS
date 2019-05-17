class File {

    private String fileName;

    private int startIndex;

    private int size;

    public File(String name, int size) {
        fileName = name;
        this.size = size;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String getFileName() {
        return fileName;
    }

    public int getSize() {
        return size;
    }
}
