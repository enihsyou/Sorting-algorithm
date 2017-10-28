import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Stack;

public class DiskStack {
  // 柱子的高度
  public static final int NEEDLE_HEIGHT = 100;

  // 碟子的高度
  private static final int DISK_HEIGHT = 10;

  // 碟子的存储结构
  private Stack<Rectangle2D> disks = new Stack<>();

  // 最下面碟子的左上角y坐标
  private double baseline;

  // 柱子的x坐标
  private int x;

  // 最上面碟子的左上角坐标，如果栈是空的，这个值和baseline值相同。
  private double y;

  // 碟子之间的间隔宽度
  private static final int DISK_GAP = 2;

  // 柱子，只是一根线
  private Line2D needle;

  // 柱子的名字
  private final String name;

  /**
   * 创建一个空的柱子
   */
  public DiskStack(int x, int y, String name) {
    this.x = x;
    this.y = y;
    baseline = y;
    this.name = name;
    needle = new Line2D.Double(x, y, x, y - NEEDLE_HEIGHT);
  }

  /**
   * 向柱子添加一块盘子
   */
  public void pushDisk(int width) {
    Rectangle2D newDisk = new Rectangle2D.Double(0, 0, width, DISK_HEIGHT);
    pushDisk(newDisk);
  }

  /**
   * 向柱子顶上添加一块盘子
   */
  public void pushDisk(Rectangle2D newDisk) {
    double diskLeft = x - newDisk.getWidth() / 2;
    double diskTop = y - DISK_HEIGHT - DISK_GAP;
    y = diskTop;
    newDisk.setFrame(diskLeft, diskTop, newDisk.getWidth(), newDisk.getHeight());
    disks.push(newDisk);
  }

  /**
   * 从柱子顶上移除一块盘子
   */
  public Rectangle2D pop() {
    y = y + disks.peek().getHeight() + DISK_GAP;
    return disks.pop();
  }

  /**
   * 清空柱子上的所有盘子
   */
  public void clear() {
    disks.clear();
    y = baseline;
  }

  /**
   * 绘制柱子和它上面的盘子
   */
  public void paint(Graphics2D g) {
    g.draw(needle);

    disks.forEach(g::fill);
  }

  @Override
  public String toString() {
    return name;
  }
}
