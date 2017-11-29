import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * 1）编程实现残缺棋盘问题，并用图形方式输出棋盘。
 */
public class Q1 {
  private Grid grid;
  private JFrame window;
  private boolean start;

  public Q1() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
    }
    final int i = Integer.parseInt(JOptionPane.showInputDialog(window, "点击格子区域进行计算\n贴片数量", 4));
    init(i);
  }

  private void init(int level) {
    window = new JFrame();
    grid = new Grid(level);
    grid.addMouseListener(new MyMouseListener());
    window.add(grid);
    window.pack();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setLocationRelativeTo(null);
    window.setVisible(true);
  }

  public static void main(String[] a) {
    SwingUtilities.invokeLater(Q1::new);
  }

  private static Grid.MODE getMode(final int x, final int y, final int blockX, final int blockY, int size) {
    size /= 2;
    if (blockX < x + size)
      if (blockY < y + size) {
        return Grid.MODE.Q;
      } else {
        return Grid.MODE.A;
      }
    else {
      if (blockY < y + size) {
        return Grid.MODE.W;
      } else {
        return Grid.MODE.S;
      }
    }
  }

  public void fill() {
    final int cellX = grid.getBlockedCellX();
    final int cellY = grid.getBlockedCellY();
    final int gridN = grid.getN();
    start = true;
    cover(0, 0, cellX, cellY, gridN);
    start = false;
  }

  private void cover(final int leftUpperX, final int leftUpperY, final int blockX, final int blockY, final int size) {
    final Grid.MODE mode = getMode(leftUpperX, leftUpperY, blockX, blockY, size);
    if (size == 2) {
      grid.fillCell(mode, leftUpperX, leftUpperY);
      return;
    }
    final int blocks = size / 2;
    grid.fillCell(mode, leftUpperX + blocks - 1, leftUpperY + blocks - 1);
    switch (mode) {
      case Q: // 空缺在左上角
        cover(leftUpperX, leftUpperY, blockX, blockY, blocks);
        coverW(leftUpperX, leftUpperY, blocks);
        coverA(leftUpperX, leftUpperY, blocks);
        coverS(leftUpperX, leftUpperY, blocks);
        break;
      case W: // 空缺在右上角
        coverQ(leftUpperX, leftUpperY, blocks);
        cover(leftUpperX + blocks, leftUpperY, blockX, blockY, blocks);
        coverA(leftUpperX, leftUpperY, blocks);
        coverS(leftUpperX, leftUpperY, blocks);
        break;
      case A: // 空缺在左下角
        coverQ(leftUpperX, leftUpperY, blocks);
        coverW(leftUpperX, leftUpperY, blocks);
        cover(leftUpperX, leftUpperY + blocks, blockX, blockY, blocks);
        coverS(leftUpperX, leftUpperY, blocks);
        break;
      case S: // 空缺在右下角
        coverQ(leftUpperX, leftUpperY, blocks);
        coverW(leftUpperX, leftUpperY, blocks);
        coverA(leftUpperX, leftUpperY, blocks);
        cover(leftUpperX + blocks, leftUpperY + blocks, blockX, blockY, blocks);
        break;
    }
  }

  private void coverQ(final int leftUpperX, final int leftUpperY, final int blocks) {
    // 右下角已经填上
    cover(leftUpperX, leftUpperY, leftUpperX + blocks - 1, leftUpperY + blocks - 1, blocks);
  }

  private void coverW(final int leftUpperX, final int leftUpperY, final int blocks) {
    // 左下角已经填上
    cover(leftUpperX + blocks, leftUpperY, leftUpperX, leftUpperY + blocks - 1, blocks);
  }

  private void coverA(final int leftUpperX, final int leftUpperY, final int blocks) {
    // 右上角已经填上
    cover(leftUpperX, leftUpperY + blocks, leftUpperX + blocks - 1, leftUpperY, blocks);
  }

  private void coverS(final int leftUpperX, final int leftUpperY, final int blocks) {
    // 左上角已经填上
    cover(leftUpperX + blocks, leftUpperY + blocks, leftUpperX, leftUpperY, blocks);
  }

  public static class Grid extends JPanel {
    private static int INTERVAL = 100;
    private final IntSupplier randomColorComp = () -> ThreadLocalRandom.current().nextInt(50, 230);
    private final Supplier<Color> randomColor =
        () -> new Color(randomColorComp.getAsInt(), randomColorComp.getAsInt(), randomColorComp.getAsInt());
    private Color[][] filledCells;
    private int N; // 横向格子数
    private int cellSize = 10; // 每个正方形格子的边长
    private int panelHeight; // 基本就是整个窗体的高度
    private int panelWidth; // 窗体宽度
    private int leftPadding = cellSize;
    private int topPadding = cellSize;
    private int rightPadding = cellSize;
    private int bottomPadding = cellSize;
    private int blockedCellX;
    private int blockedCellY;


    public Grid(int level) {
      N = BigInteger.TWO.pow(level).intValue();
      System.out.println("N = " + N);
      final int boardHeight = N * cellSize;
      final int boardWidth = N * cellSize;
      filledCells = new Color[N][N];

      panelHeight = boardHeight + leftPadding + rightPadding;
      panelWidth = boardWidth + topPadding + bottomPadding;
      blockedCellX = -cellSize; // 最开始不画出来
      blockedCellY = -cellSize;

      setPreferredSize(new Dimension(panelWidth, panelHeight));
    }

    public static void setINTERVAL(final int INTERVAL) {
      Grid.INTERVAL = INTERVAL;
    }

    private static void sleep() {
      try {
        TimeUnit.MILLISECONDS.sleep(INTERVAL);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    public Point getCell(final int x, final int y) {
      if (x < leftPadding || x > panelWidth - rightPadding || y < topPadding || y > panelHeight - bottomPadding) {
        return null;
      }
      return new Point((x - leftPadding) / cellSize, (y - topPadding) / cellSize);
    }

    public int getN() {
      return N;
    }

    public int getBlockedCellX() {
      return blockedCellX;
    }

    public void setBlockedCellX(final int blockedCellX) {
      if (blockedCellX > N || blockedCellX < 0)
        throw new IllegalArgumentException();
      this.blockedCellX = blockedCellX;
    }

    public int getBlockedCellY() {
      return blockedCellY;
    }

    public void setBlockedCellY(final int blockedCellY) {
      if (blockedCellY > N || blockedCellY < 0)
        throw new IllegalArgumentException();
      this.blockedCellY = blockedCellY;
    }

    @Override
    protected void paintComponent(final Graphics g) {
      super.paintComponent(g);
      for (int x = 0; x < N; x++) {
        for (int y = 0; y < N; y++) {
          Color cell = filledCells[x][y];
          if (cell == null)
            continue;
          final int cellX = leftPadding + (x * cellSize);
          final int cellY = topPadding + (y * cellSize);
          g.setColor(cell);
          g.fillRect(cellX, cellY, cellSize, cellSize);
        }
      }
      g.setColor(Color.BLACK);
      drawBlockedCell(g);
      g.setColor(Color.DARK_GRAY);
      drawVerticalGirdLine(g);
      drawHorizontalGirdLine(g);
    }

    private void drawBlockedCell(final Graphics g) {
      final int cellX = leftPadding + (blockedCellX * cellSize);
      final int cellY = topPadding + (blockedCellY * cellSize);
      g.fillRect(cellX, cellY, cellSize, cellSize);
    }

    private void drawVerticalGirdLine(final Graphics g) {
      final int start_from = topPadding;
      final int end_at = panelHeight - bottomPadding;
      final int gird_size = cellSize;
      for (int i = leftPadding; i <= panelWidth - rightPadding; i += gird_size) {
        g.drawLine(i, start_from, i, end_at);
      }
    }

    private void drawHorizontalGirdLine(Graphics g) {
      final int start_from = leftPadding;
      final int end_at = panelWidth - rightPadding;
      final int gird_size = cellSize;
      for (int i = topPadding; i <= panelHeight - bottomPadding; i += gird_size) {
        g.drawLine(start_from, i, end_at, i);
      }
    }

    public void fillCell(MODE mode, int leftUpperX, int leftUpperY) {
      final Color color = randomColor.get();
      switch (mode) {
        case Q:
          fillW(leftUpperX, leftUpperY, color);
          fillA(leftUpperX, leftUpperY, color);
          fillS(leftUpperX, leftUpperY, color);
          break;
        case W:
          fillQ(leftUpperX, leftUpperY, color);
          fillA(leftUpperX, leftUpperY, color);
          fillS(leftUpperX, leftUpperY, color);
          break;
        case A:
          fillQ(leftUpperX, leftUpperY, color);
          fillW(leftUpperX, leftUpperY, color);
          fillS(leftUpperX, leftUpperY, color);
          break;
        case S:
          fillQ(leftUpperX, leftUpperY, color);
          fillW(leftUpperX, leftUpperY, color);
          fillA(leftUpperX, leftUpperY, color);
          break;
      }
      repaint();
      // sleep();
    }

    private void fillW(final int leftUpperX, final int leftUpperY, final Color color) {
      fillCell(leftUpperX + 1, leftUpperY, color);
    }

    private void fillA(final int leftUpperX, final int leftUpperY, final Color color) {
      fillCell(leftUpperX, leftUpperY + 1, color);
    }

    private void fillS(final int leftUpperX, final int leftUpperY, final Color color) {
      fillCell(leftUpperX + 1, leftUpperY + 1, color);
    }

    private void fillQ(final int leftUpperX, final int leftUpperY, final Color color) {
      fillCell(leftUpperX, leftUpperY, color);
    }

    private void fillCell(int x, int y, Color color) {
      filledCells[x][y] = color;
    }

    enum MODE {
      Q(0, 0), W(1, 0), A(0, 1), S(1, 1);
      final int x, y;

      MODE(int x, int y) {
        this.x = x;
        this.y = y;
      }
    }
  }

  private class MyMouseListener extends MouseAdapter {
    @Override
    public void mouseClicked(final MouseEvent e) {
      if (start)
        return;
      final Point cell = grid.getCell(e.getX(), e.getY());
      if (cell != null) {
        System.out.println("cell = " + cell);
        grid.setBlockedCellX(cell.x);
        grid.setBlockedCellY(cell.y);
        fill();
      }
    }
  }
}
