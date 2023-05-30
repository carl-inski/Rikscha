import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI implements ActionListener {
    JFrame startmenu;
    JFrame game;

    public static void main(String[] args) {
        GUI demo = new GUI();
        demo.createUI();
    }

    public void createUI() {
        startmenu = new JFrame("Saufeeeeeeeeen");
        startmenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startmenu.setPreferredSize(new Dimension(845, 390));

        JPanel panelMenu = new JPanel();

        JButton button = new JButton("Neuer Frame");
        button.addActionListener(this);

        panelMenu.add(button);
        startmenu.add(panelMenu);

        startmenu.pack();
        startmenu.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        startmenu.setVisible(false);

        game = new JFrame("Rikscha");
        game.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        game.setPreferredSize(new Dimension(845, 390));

        JPanel panelGame = new JPanel();

        JButton button = new JButton("Neuer Frame");
        button.addActionListener(this);

        panelGame.add(button);
        startmenu.add(panelGame);

        game.pack();
        game.setVisible(true);

    }
}
