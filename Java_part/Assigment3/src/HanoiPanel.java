import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HanoiPanel extends JComponent implements Runnable {
  private static final int SPEED_CONST = 12000;

  // 最小的盘子的宽度
  private static final int SMALLEST_DISK_WIDTH = 6;

  // 每块盘子之间的宽度差
  private static final int DISK_WIDTH_UNIT = 15;

  // pause between moves
  private int delay;

  // 用户选择的盘子数量
  private int numberOfDisks;

  // 柱子数量
  private static final int NUM_NEEDLES = 3;

  private DiskStack[] needles = new DiskStack[NUM_NEEDLES];

  private boolean printing = true;


  public HanoiPanel(int numDisks, int windowWidth, int baseLine) {
    int firstNeedleX = windowWidth / 6;

    for (int i = 0; i < NUM_NEEDLES; i++) {
      int x = firstNeedleX + 2 * i * firstNeedleX;
      needles[i] = new DiskStack(x, baseLine, String.format("Disk#%d", i));
    }
    setDisks(numDisks);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // 白色背景
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight());

    // 黑色盘子和柱子
    g.setColor(Color.BLACK);
    for (int i = 0; i < NUM_NEEDLES; i++) {
      needles[i].paint((Graphics2D) g);
    }
  }
  public void run() {
    try {
      Thread.sleep(2 * delay);
      recHanoi(numberOfDisks, needles[0], needles[2], needles[1]);
    } catch (InterruptedException e) {
      // Just return
    }
  }

  private void recHanoi(int numDisks, DiskStack first, DiskStack last, DiskStack helper) throws InterruptedException {
    if (numDisks == 1) {
      moveDisk(first, last);
    } else {
      recHanoi(numDisks - 1, first, helper, last);

      moveDisk(first, last);

      recHanoi(numDisks - 1, helper, last, first);
    }
  }


  private void moveDisk(DiskStack first, DiskStack last) throws InterruptedException {
    if (printing) {
      System.out.println("Move disk from needle " + first + " to needle " + last + '.');
    }

    Rectangle2D diskToMove = first.pop();
    last.pushDisk(diskToMove);

    repaint();
    Thread.sleep(delay);
  }

  public void setSpeed(int speed) {
    delay = SPEED_CONST / speed;
  }


  public void clear() {
    for (int i = 0; i < NUM_NEEDLES; i++) {
      needles[i].clear();
    }
    repaint();
  }

  public void setDisks(int numDisks) {
    assert numDisks > 0;
    this.numberOfDisks = numDisks;

    for (int diskNum = numberOfDisks; diskNum > 0; diskNum--) {
      int diskOffset = SMALLEST_DISK_WIDTH + (diskNum * DISK_WIDTH_UNIT) / 2;
      needles[0].pushDisk(2 * diskOffset);
    }
    repaint();
  }
}
