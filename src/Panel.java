import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Panel extends JPanel {

    Disk disk = Disk.getInstance();

    private int rowsCount;

    private int columnsCount;

    int sizeCell = 15;

    public Panel(int rows, int columns) {
        rowsCount = rows;
        columnsCount = columns;
    }

    @Override
    protected void paintComponent(Graphics gh) {
        Graphics2D drp = (Graphics2D)gh;

        ArrayList<Block> blocks = disk.getBlocks();

        for (int i = 0; i < blocks.size(); i++) {
        int rowIndex = i / columnsCount;
        int columnIndex = i % columnsCount;

        int y = sizeCell * rowIndex;
        int x = sizeCell * columnIndex;

            drawCell(drp, x, y, blocks.get(i).getState());
        }

        drawGrid(drp, sizeCell);
    }

    private void drawGrid(Graphics2D drp, int sizeCell) {
        drp.setColor(Color.BLACK);

        for (int i = 0; i < columnsCount; i++) {
            drp.drawLine(i * sizeCell, 0, i * sizeCell, rowsCount * sizeCell);
        }

        for (int i = 0; i < rowsCount; i++) {
            drp.drawLine(0, i * sizeCell, columnsCount * sizeCell,i * sizeCell);
        }
    }

    private void drawCell(Graphics2D drp, int x, int y, BlockState state) {
        switch (state) {
            case FREE:
                drp.setColor(new Color(238, 230, 231));
                break;
            case OCCUPIED:
                drp.setColor(new Color(255, 29, 51));
                break;
            case SELECTED:
                drp.setColor(new Color(13, 195, 7));
        }

        drp.fillRect(x, y, sizeCell, sizeCell);
    }
}
