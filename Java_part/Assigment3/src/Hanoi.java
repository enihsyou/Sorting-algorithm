import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hanoi extends JPanel implements ActionListener, ChangeListener {
  private static final int WINDOW_SIZE = 400;

  private static final int BASELINE = 150;

  private final JButton startButton;

  private int numDisks = 5;

  private HanoiPanel game = new HanoiPanel(numDisks, WINDOW_SIZE, BASELINE);

  private final JTextField numField ;

  private final JSlider speedSlider;

  private Thread gameThread;

  /**
   * Draw the initial screen
   */
  public Hanoi() {
    setLayout(new BorderLayout());

    final JPanel topPanel = new JPanel(); // 上部的控制栏
    final Box topBox = new Box(BoxLayout.Y_AXIS);

    final JPanel speedPanel = new JPanel(); // 速度滑块区
    speedSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 50);
    speedSlider.addChangeListener(this);
    speedPanel.add(new JLabel("动画速度:", JLabel.RIGHT));
    speedPanel.add(speedSlider);
    topBox.add(speedPanel);

    final JPanel diskPanel = new JPanel(); // 碟片数量输入区
    numField = new JTextField(String.valueOf(numDisks), 7);
    diskPanel.add(new JLabel("盘子数量:", JLabel.RIGHT));
    diskPanel.add(numField);
    topBox.add(diskPanel);

    topPanel.add(topBox);

    final JPanel bottomPanel = new JPanel(); // 下部控制按钮
    startButton = new JButton("播放");
    startButton.addActionListener(this);
    bottomPanel.add(startButton);

    add(topPanel, BorderLayout.NORTH);
    add(game, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
  }

  /**
   * 在用户拉动速度条滑块的时候调用，调整动画进行速度。
   */
  public void stateChanged(ChangeEvent evt) {
    if (game != null) {
      game.setSpeed(speedSlider.getValue());
    }
  }

  /**
   * 当用户点击开始按钮的时候调用。
   * 如果当前没有进行动画的话，开始新动画；
   * 如果当前正在步进模式，则全速播放动画。
   */
  public void actionPerformed(ActionEvent evt) {
    if (game != null) {
      game.clear();
    }

    if (gameThread != null) {
      gameThread.interrupt();
    }

    String stringValue = numField.getText();
    try {
      numDisks = Integer.parseInt(stringValue);
    } catch (NumberFormatException e) {
      // Leave it unchanged if the user does not type an integer
    }

    game.setDisks(numDisks);
    game.setSpeed(speedSlider.getValue());

    gameThread = new Thread(game);
    gameThread.start();
  }

  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.setTitle("汉诺塔");
    f.setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));

    Hanoi hanoi = new Hanoi();
    f.add(hanoi, BorderLayout.CENTER);

    f.setVisible(true);
  }
}
