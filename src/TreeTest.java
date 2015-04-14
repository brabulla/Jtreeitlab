import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

/**
 * Created by Adam on 2015.04.14..
 */
public class TreeTest implements ItemListener {
    JComboBox combo;
    FileTreeModel fileTreeModel;
    JTree tree;
    JScrollPane scrollPane;

    static public void main(String args[]) {
        (new TreeTest()).run();
    }

    public void run() {
        JFrame f = new JFrame("JTree Example");
        combo = new JComboBox();
        for(File item : File.listRoots())
            if(item.listFiles()!=null)
                combo.addItem(item);

        fileTreeModel = new FileTreeModel();
        fileTreeModel.setRoot(new FileWrap((File) combo.getSelectedItem()));
        tree = new JTree(fileTreeModel);
        ToolTipManager.sharedInstance().registerComponent(tree);
        tree.setCellRenderer(new FileTreeRenderer());
        scrollPane = new JScrollPane(tree);
        combo.addItemListener(this);
        f.add(combo, BorderLayout.NORTH);
        f.add(scrollPane, BorderLayout.CENTER);
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        f.pack();
        f.show();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            fileTreeModel.setRoot(new FileWrap((File) e.getItem()));
        }
    }
}
