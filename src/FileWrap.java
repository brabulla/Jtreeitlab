import java.io.File;

/**
 * Created by Adam on 2015.04.14..
 */
public class FileWrap {
    public File value;

    public FileWrap(File f) {
        value = f;
    }

    public FileWrap(String fp) {
        value = new File(fp);
    }

    public String toString() {
        return value.getName();
    }
}
