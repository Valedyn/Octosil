package Setup;

import java.io.IOException;

/**
 * This class is meant to allow the team to create a file and then commit various operations on it, such as writing to it or reading from it.
 * @author Valedyn
 * @version 1.0
 */
public class File {
    private java.io.File file;
    private String path;
    private String full_path;
    private String name;
    private String extension;

    /**
     * @author Valedyn
     * @since 1.0
     * @param path String of the path where a file is going to be created.
     * @param name A String of the name of the file which is going to be created
     * @param extension A String of the extension of the file which is going to be created
     * @throws IOException throws the Exception when both the file doesn't exist and the method to create the file results in an error.
     */
    public File(String path, String name, String extension) throws IOException {
        this.full_path = String.format("%s\\%s.%s", path, name, extension);
        this.file = new java.io.File(full_path);
        this.name = name;
        this.extension = extension;
        if (!file.exists()) {
            if(file.createNewFile()){
                Testing.Test.println(String.format("Created the file %s.%s in the following directory: %s", name, extension, path));
            }else {
                throw new IOException("ERR: There's been an error creating the file! Either the file already exists, or something was blocking the file from being created!");
            }
        }else {
            Testing.Test.println(String.format("File %s.%s already exists in the following directory:%s", name, extension, path));
        }

    }

    /**
     * Returns the absolute path to the parent directory of the file as a String.
     * @return String of the path to the parent directory of the file.
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns the absolute path of the file as a String.
     * @return String of the path of the file.
     */
    public String getFull_path() {
        return full_path;
    }

    /**
     * Returns the name of the file.
     * @return String of the name of the file.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the extension of the file.
     * @return String of the extension of the file.
     */
    public String getExtension() {
        return extension;
    }
}
