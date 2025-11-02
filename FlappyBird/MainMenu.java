import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        // Mengatur judul jendela menu
        setTitle("Flappy Bird Menu");

        // Menentukan ukuran jendela menu utama
        setSize(300, 200);

        // Agar program berhenti sepenuhnya ketika jendela ditutup
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Menggunakan layout null supaya posisi komponen diatur secara manual dengan setBounds()
        setLayout(null);

        // Menempatkan jendela di tengah layar
        setLocationRelativeTo(null); // biar muncul di tengah layar

        // Mengatur warna latar belakang jendela menjadi biru langit
        getContentPane().setBackground(new Color(135, 206, 250)); // biru langit

        // Membuat tombol "Play Game"
        JButton playBtn = new JButton("Play Game");
        playBtn.setBounds(80, 50, 140, 30); // posisi dan ukuran tombol
        playBtn.setBackground(Color.GREEN); // warna tombol hijau
        playBtn.setForeground(Color.WHITE); // warna teks tombol putih

        // Menambahkan aksi ketika tombol "Play Game" diklik
        playBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Membuat objek logika dan tampilan game
                Logic logic = new Logic();
                View view = new View(logic);

                // Membuat jendela baru untuk game
                JFrame gameFrame = new JFrame("Flappy Bird");
                gameFrame.add(view); // menambahkan panel game ke frame
                gameFrame.pack(); // menyesuaikan ukuran frame dengan panel
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setResizable(false); // tidak bisa diubah ukurannya
                gameFrame.setLocationRelativeTo(null); // posisi di tengah layar
                gameFrame.setVisible(true); // menampilkan jendela game

                // Menutup jendela menu utama setelah game dimulai
                dispose(); // tutup menu
            }
        });

        // Membuat tombol "Exit" untuk keluar dari aplikasi
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(80, 100, 140, 30); // posisi dan ukuran tombol
        exitBtn.setBackground(Color.RED); // warna tombol merah
        exitBtn.setForeground(Color.WHITE); // warna teks putih
        exitBtn.addActionListener(e -> System.exit(0)); // menutup program saat diklik

        // Menambahkan kedua tombol ke jendela menu
        add(playBtn);
        add(exitBtn);

        // Menampilkan jendela menu di layar
        setVisible(true);
    }

    // Method main untuk menjalankan program dari menu utama
    public static void main(String[] args) {
        new MainMenu();
    }
}
