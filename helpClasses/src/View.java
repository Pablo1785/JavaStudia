import javax.swing.*;
import java.awt.*;

public class View {
    // init swing objects
    public JFrame frame = new JFrame();
    public int width;
    public int height;

    public View(int widthInput, int heightInput) {
        this.width = widthInput;
        this.height = heightInput;

        // meta tools
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        // frame appearance and layout
        frame.setSize(widthInput,heightInput);
        frame.setLayout(null);
        frame.setVisible(true);

        // frame location
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }
}
