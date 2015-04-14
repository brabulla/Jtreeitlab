import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Adam on 2015.04.14..
 */
public class FileTreeModel implements TreeModel {
    private ArrayList<TreeModelListener> listeners;
    private FileWrap root;

    public FileTreeModel() {
        listeners = new ArrayList<TreeModelListener>();
        //root = new FileWrap(File.listRoots()[0]);
    }

    public void setRoot(FileWrap ruti) {
        root = ruti;
        TreeModelEvent e = new TreeModelEvent(this, new TreePath(this.getRoot()));
        for (TreeModelListener lis : listeners)
            lis.treeStructureChanged(e);

    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        FileWrap fw = (FileWrap) parent;
        File[] children = fw.value.listFiles();
        Arrays.sort(children);
        return new FileWrap(children[index]);
    }

    @Override
    public int getChildCount(Object parent) {
        FileWrap fw = (FileWrap) parent;
        return fw.value.list().length;
    }

    @Override
    public boolean isLeaf(Object node) {
        FileWrap fw = (FileWrap) node;
        return fw.value.isFile();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        FileWrap oldFile = (FileWrap) path.getLastPathComponent();
        String fileParentPath = oldFile.value.getParent();
        String newFileName = (String) newValue;
        File targetFile = new File(fileParentPath, newFileName);
        oldFile.value.renameTo(targetFile);
        File parent = new File(fileParentPath);
        int[] changedChildrenIndices = {getIndexOfChild(parent, targetFile)};
        Object[] changedChildren = {targetFile};
        fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);
    }

    private void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
        TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
        Iterator iterator = listeners.iterator();
        TreeModelListener listener;
        while (iterator.hasNext()) {
            listener = (TreeModelListener) iterator.next();
            listener.treeNodesChanged(event);
        }
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        FileWrap par = (FileWrap) parent;
        FileWrap ch = (FileWrap) child;
        ArrayList<File> listOfFiles = new ArrayList<File>();
        for (File item : par.value.listFiles())
            listOfFiles.add(item);
        Collections.sort(listOfFiles);

        return listOfFiles.indexOf(ch.value);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }
}
