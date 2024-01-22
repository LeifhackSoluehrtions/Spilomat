import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class main extends JFrame {
    private JLabel[] reels;
    private JButton spinButton;
    private Timer timer;
    private Random random;
    int geld = 100;

    public main() {
        setTitle("Spilomat");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeReels();
        initializeSpinButton();

        setLayout(new BorderLayout());

        JPanel reelsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        for (JLabel reel : reels) {
            reelsPanel.add(reel);
        }
        add(reelsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(spinButton);
        add(buttonPanel, BorderLayout.SOUTH);

        initializeTimer();

        random = new Random();

        setVisible(true);
    }

    private void initializeReels() {
        reels = new JLabel[3];
        for (int i = 0; i < reels.length; i++) {
            reels[i] = new JLabel("0");
            reels[i].setHorizontalAlignment(JLabel.CENTER);
            reels[i].setFont(new Font("Arial", Font.BOLD, 50));
        }
    }

    private void initializeSpinButton() {
        spinButton = new JButton("Spin");
        spinButton.setFont(new Font("Arial", Font.PLAIN, 20));
        spinButton.addActionListener(e -> startAnimation());
    }

    private void initializeTimer() {
        timer = new Timer(100, e -> updateReels());
    }

    private void startAnimation() {
        spinButton.setEnabled(false);
        timer.start();
        Timer stopTimer = new Timer(2000, e -> stopAnimation());
        stopTimer.setRepeats(false);
        stopTimer.start();
    }

    private void stopAnimation() {
        timer.stop();
        spinButton.setEnabled(true);
        displayResult();
    }

    private void updateReels() {
        for (JLabel reel : reels) {

            int randomNumber = random.nextInt(9) + 1;
            reel.setText(String.valueOf(randomNumber));
        }
    }

    private void displayResult() {
        int[] numbers = new int[3];
        for (int i = 0; i < reels.length; i++) {
            numbers[i] = Integer.parseInt(reels[i].getText());
        }

        int gewinn = evaluateNumbers(numbers);
        geld += gewinn;

        String resultText = generateResultText(numbers, gewinn);
        JOptionPane.showMessageDialog(this, resultText, "Ergebnis", JOptionPane.INFORMATION_MESSAGE);
    }

    private int evaluateNumbers(int[] numbers) {
        if (numbers[0] == numbers[1] && numbers[1] == numbers[2]) {
            return 500;
        } else {
            return -10; 
        }
    }

    private String generateResultText(int[] numbers, int gewinn) {
        StringBuilder resultText = new StringBuilder("Spilomat Ergebnis:\n");
        resultText.append("Gewählte Zahlen: ").append(numbers[0]).append(" - ").append(numbers[1]).append(" - ").append(numbers[2]).append("\n");

        if (gewinn > 0) {
            resultText.append("Gewonnen" + gewinn + "Geld \n");
        } else if (gewinn < 0) {
            resultText.append("Veloren" + gewinn + "Geld \n");
        }

        resultText.append(geld).append(" Geld");

        return resultText.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new main());
    }
}
