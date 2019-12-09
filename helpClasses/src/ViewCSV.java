import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ViewCSV extends View {
    public ViewCSV(int widthInput, int heightInput) {
        super(widthInput, heightInput);

        // add file choosing
        JButton but = new JButton("Choose CSV file");
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        this.frame.add(but);

        // button function and appearance
        but.setBounds(100,100,300,40);
        but.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int returnValue = jfc.showOpenDialog(frame);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    JOptionPane.showMessageDialog(jfc, selectedFile.getName(), "This is your file's name", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}
