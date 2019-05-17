import java.util.ArrayList;

class Disk {
    private final int DISK_SIZE = 400;

    private final int BLOCK_SIZE = 4;

    private static Disk instance = new Disk();

    private int freeBlocks;

    private ArrayList<Block> blocks;

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    private ArrayList<File> files;

    public static Disk getInstance() {
        return instance;
    }

    private Disk() {

        files = new ArrayList<>();

        blocks = new ArrayList<>();

        freeBlocks = DISK_SIZE / BLOCK_SIZE;

        for (int i = 0; i < DISK_SIZE / BLOCK_SIZE; i++) {
            blocks.add(new Block(-1));
        }
    }

    public int getDiskSize() {
        return DISK_SIZE;
    }

    public int getBlockSize() {
        return BLOCK_SIZE;
    }

    public int getCountBlocks() {
        return blocks.size();
    }

    public void addFile(File file) throws Exception {
        int fileBlocks = (file.getSize() / BLOCK_SIZE) + (file.getSize() % BLOCK_SIZE != 0 ? 1 : 0);

        if (freeBlocks < fileBlocks) {
            throw new Exception("Недостаточно места на диске");
        }

        freeBlocks -= fileBlocks;

        int addedBlocks = 0;
        int lastAddedBlock = -1;
        for (int i = 0; i < blocks.size(); i++) {

            if (addedBlocks == fileBlocks) {
                break;
            }

            if (blocks.get(i).getState() == BlockState.FREE) {

                if (addedBlocks == 0) {
                    for (var f : files) {
                        if(f.getFileName().equals(file.getFileName())) {
                            throw new Exception("Уже есть такой файл");
                        }
                    }
                    file.setStartIndex(i);
                    files.add(file);
                }
                else {
                    blocks.get(lastAddedBlock).setNext(i);
                }

                blocks.get(i).setState(BlockState.OCCUPIED);

                lastAddedBlock = i;
                addedBlocks++;
            }
        }
    }

    public void deleteFile(String name) {

        File curFile = null;

        for(var file : files) {
            if(file.getFileName().equals(name)) {
                curFile = file;
            }
        }

        files.remove(curFile);

        int index = curFile.getStartIndex();

        while (true) {
            int next =  blocks.get(index).getNext();

            blocks.get(index).setState(BlockState.FREE);
            blocks.get(index).setNext(-1);

            freeBlocks++;

            if (next == -1) {
                break;
            }

            index = next;
        }
    }

    public void selectFile(String name) {

        File curFile = null;

        for(var file : files) {
            if(file.getFileName().equals(name)) {
                curFile = file;
            }
        }

        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getState() == BlockState.SELECTED) {
                blocks.get(i).setState(BlockState.OCCUPIED);
            }
        }

        int index = curFile.getStartIndex();
        while (true) {
            blocks.get(index).setState(BlockState.SELECTED);

            if (blocks.get(index).isEnd()) {
                break;
            }

            index = blocks.get(index).getNext();
        }
    }
}
