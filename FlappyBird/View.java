import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
    private Logic logic;
    private JLabel scoreLabel;

    int width = 360;
    int height = 640;

    // Constructor
    public View(Logic logic) {
        this.logic = logic;
        logic.setView(this);

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.cyan);
        setLayout(null); // supaya bisa atur posisi label manual

        // JLabel untuk menampilkan skor
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(10, 10, 200, 30);//Mnentukan posisi label skor di pojok kiri atas
        add(scoreLabel); //Menambah label ke panel

        setFocusable(true);
        addKeyListener(logic);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g); //Menggambbar seluruh elemen game
        updateScore(); // memperbarui tampilan skor di Jlabel
    }

    private void updateScore() {
        // ambil skor terbaru dari Logic dan tampilkan di JLabel
        scoreLabel.setText("Score: " + logic.getScore());
    }

    public void draw(Graphics g) {
        // Gambar player
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);
        }

        // Gambar semua pipa
        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (Pipe pipe : pipes) {
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        //  Kalau game over, tampilkan tulisan di tengah layar
        if (logic.isGameOver()) {
            // Membuat latar hitam transparan agar tulisan lebih terlihat jelas
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(0, 0, 0, 150)); // nilai 150 menentukan tingkat transparansi
            g2d.fillRect(0, 0, width, height); // menutupi seluruh area layar
            g2d.dispose();

            // Menampilkan teks "GAME OVER" dengan sedikit bayangan hitam
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.BLACK);
            g.drawString("GAME OVER", 63, 253); // bayangan teks
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 60, 250); // teks utama

            // Menampilkan total skor yang diperoleh pemain
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.setColor(Color.WHITE);
            g.drawString("Total Score: " + logic.getScore(), 90, 300);

            // Memberi petunjuk kepada pemain untuk memulai ulang permainan
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.setColor(Color.YELLOW);
            g.drawString("Press R to Restart", 100, 340);
        }
}
    }
