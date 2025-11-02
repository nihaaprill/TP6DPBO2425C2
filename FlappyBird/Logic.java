import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Logic implements ActionListener, KeyListener {

    int frameWidth = 360;
    int frameHeight = 640;

    View view;
    Image birdImage;
    Player player;

    Image lowerPipeImage;
    Image upperPipeImage;
    ArrayList<Pipe> pipes;

    Timer gameLoop;
    Timer pipesCooldown;
    int pipeVelocityX = -2;

    double gravity = 0.3; // ku ganti dari int gravity = 1 supaya ga gampang jatoh
    double jumpPower = -6;
    double maxFallSpeed = 8;

    int playerStartPosX = frameWidth / 4;   // posisi awal lebih ke kiri
    int playerStartPosY = frameHeight / 3;  // agak di atas biar aman
    int playerWidth = 34;
    int playerHeight = 24;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    int score = 0;

    boolean gameOver = false;
    boolean started = false;


    public Logic() {
        // Load gambar burung
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);

        // Load gambar pipa atas dan bawah
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        pipes = new ArrayList<Pipe>();

        // Timer untuk membuat pipa baru setiap 1.5 detik
        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        pipesCooldown.setInitialDelay(2000); // jeda 2 detik sebelum pipa pertama
        pipesCooldown.start(); // cukup start sekali

        // Timer utama untuk game loop (60 FPS)
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        resetPlayerPosition();
    }

    public void setView(View view) {
        this.view = view;
    }
    public void resetPlayerPosition() {
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
        }
        if (view != null) {
            view.repaint();
        }
    }

    public void move() {
        // Jalankan gravitasi hanya kalau game sudah dimulai
        if (started) {
            // gravitasi halus
            player.setVelocityY(player.getVelocityY() + gravity);
            if (player.getVelocityY() > maxFallSpeed) player.setVelocityY(maxFallSpeed);

            player.setPosY(player.getPosY() + player.getVelocityY());
        }

        // Batas atas
        if (player.getPosY() < 0) {
            player.setPosY(0);
        }

        // Batas tanah
        int groundY = frameHeight - 80;
        if (player.getPosY() + player.getHeight() > groundY) {
            player.setPosY(groundY - player.getHeight());
            gameOver();
        }

        // Gerakkan semua pipa
        for (Pipe pipe : pipes) {
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);
        }

        // Cek tabrakan
        boolean collision = false;
        for (Pipe pipe : pipes) {
            Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
            Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());
            if (playerRect.intersects(pipeRect)) {
                collision = true;
            }
        }

        // Naikkan skor jika player lewat pipa bawah (sekali saja per pasangan)
        for (Pipe pipe : pipes) {
            if (!pipe.isScored() && pipe.getPosX() + pipe.getWidth() < player.getPosX()) {
                score++;
                pipe.setScored(true);
            }
        }


        if (collision) {
            gameOver();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (!gameOver) {
            if (key == KeyEvent.VK_SPACE) {
                started = true;
                player.setVelocityY(jumpPower); // terbang ke atas lebih lembut
            }
        }
        // tambahkan ini di bawahnya
        else if (gameOver && key == KeyEvent.VK_R) {
            resetGame(); // restart game saat tekan R
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public Player getPlayer() {
        return this.player;
    }

    public ArrayList<Pipe> getPipes() {
        return this.pipes;
    }

    // Fungsi menambahkan sepasang pipa baru
    public void placePipes() {
        int openingSpace = frameHeight / 4; // jarak antara pipa atas dan bawah

        // Posisi random untuk pipa atas (antara -300 sampai -100)
        int randomPosY = -100 - (int)(Math.random() * 200);

        int pipeStartPosX = frameWidth; // muncul dari kanan

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + pipeHeight + openingSpace, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    // Fungsi untuk menghentikan game saat tabrakan
    public void gameOver() {
        gameOver = true;
        pipesCooldown.stop();

        // tampilkan total skor di pesan Game Over
//        JOptionPane.showMessageDialog(
//                null,
//                "Game Over!\nTotal Score: " + score + "\nTekan R untuk restart.",
//                "Flappy Bird",
//                JOptionPane.INFORMATION_MESSAGE
//        );
    }


    public void resetGame() { // Method untuk menGatur ulang seluruh elemen permainan
        pipes.clear(); // Menghapus semua pipa dari dftar
        score = 0;  // Mengembalikan skor ke nilai awal (0)
        player.setPosX(playerStartPosX); // Mengembalikan posisi X burung ke posisi awal
        player.setPosY(playerStartPosY); // Mengembalikan posisi Y burung ke posisi awal
        player.setVelocityY(0); // Mengatur ulang kecepatan jatuh burung
        started = false; // Menandai permainan belum dimulai
        gameOver = false;// Mengatur agar status gam tidak dalam kondisi game over
        pipesCooldown.restart(); // Memulai ulang timer yang membuat pipa baru
        gameLoop.start(); // Menjalkan kembali loop utama permainan
    }


    public int getScore() {
        return score; }

    public boolean isGameOver() {
        return gameOver;
    }




}
