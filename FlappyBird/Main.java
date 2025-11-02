import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Membuat jendela utama (frame) dengan judul "Flappy Bird Clone"
        JFrame frame = new JFrame("Flappy Bird Clone");

        // Membuat objek Logic yang berisi logika permainan
        Logic logic = new Logic();

        // Membuat objek View yang menampilkan tampilan game, sekaligus menghubungkannya dengan Logic
        View view = new View(logic);
        logic.setView(view);

        // Mengatur aksi saat jendela ditutup agar program benar-benar berhenti
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menentukan ukuran jendela game
        frame.setSize(360, 640);

        // Agar ukuran jendela tidak bisa diubah oleh pengguna
        frame.setResizable(false);

        // Menambahkan panel View (tampilan game) ke dalam frame
        frame.add(view);

        // Menempatkan jendela di tengah layar
        frame.setLocationRelativeTo(null);

        // Menampilkan jendela game
        frame.setVisible(true);

        // Memberikan fokus ke panel View agar bisa langsung menerima input keyboard (spasi dan R)
        view.requestFocus();
    }
}