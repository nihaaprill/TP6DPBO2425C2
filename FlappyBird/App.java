import javax.swing.*;

public class App {
    static void main(String[] args ) {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        //frame.setVisible(true);

        Logic logika = new Logic();
        // instansiasi objek View
        View tampilan = new View(logika);

        logika.setView(tampilan);

        tampilan.requestFocus();

        frame.add(tampilan);
        frame.pack();
        frame.setVisible(true);


    }
}