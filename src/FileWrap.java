import java.io.File;

/**
 * Created by Adam on 2015.04.14..
 */
public class FileWrap {
    public File value;

    FileWrap(File f) {
        value = f;
    }

    FileWrap(String fp) {
        value = new File(fp);
    }

    public String toString() {
        return value.getName();
    }
}
