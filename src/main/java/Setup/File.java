package Setup;

import java.io.*;

/**
 * This class is meant to allow the team to create a file and then commit various operations on it, such as writing to it or reading from it.
 *
 * @author Valedyn
 * @version 1.0
 */
public class File {
    private java.io.File file;
    private String full_path;
    private String name;
    private String extension;

    /**
     * @param path      String of the path where the file is supposed to be located
     * @param name      A String of the name of the file
     * @param extension A String of the extension of the file
     * @throws IOException throws the Exception when both the file doesn't exist and the method to create the file results in an error.
     * @author Valedyn
     * @since 1.0
     */
    public File(String path, String name, String extension) {
        this.full_path = String.format("%s\\%s.%s", path, name, extension);
        this.file = new java.io.File(full_path);
        this.name = name;
        this.extension = extension;
    }

    /**
     * Creates the file
     * @author Valedyn
     * @since 1.2
     * @throws IOException
     */
    public boolean create() throws IOException {
        if (!file.exists()) {
            if (file.createNewFile()) {
                Testing.Validator.println(String.format("Created the file %s.%s in the following directory: %s", name, extension, full_path));
                return true;
            } else {
                throw new IOException(String.format("ERR: There's been an error creating the file: %s ! Either the file already exists, or something was blocking the file from being created!", getFull_path()));

            }
        } else {
            Testing.Validator.println(String.format("File %s.%s already exists in the following directory: %s", name, extension, full_path));
            return false;
        }
    }

    /**
     * Reads the file and returns the contents.
     * @author Valedyn
     * @since 1.2
     * @return String containing the full contents of the read file
     */
    public String read() {
        if (exists()) {
            try (FileReader readFile = new FileReader(file); BufferedReader buffer = new BufferedReader(readFile)) {
                StringBuilder final_string = new StringBuilder();
                String one_line = "";
                String old_line = "";
                while ((one_line = buffer.readLine()) != null) {
                    if(!old_line.isEmpty()){
                        final_string.append("\n");
                    }
                    final_string.append(one_line);
                    old_line = one_line;
                }
                buffer.close();
                readFile.close();
                return final_string.toString();
            } catch (IOException e) {
                Testing.Error.println(String.format("There was an error reading the following file: %s", getFull_path()));
            }
        } else {
            Testing.Validator.println(String.format("File: %s | doesn't exist.", getFull_path()));
        }
        return null;
    }

    /**
     * Overwrites the file's contents
     * @author Valedyn
     * @since 1.2
     * @param data String to be written to the file
     * @return boolean, true or false depending on whether the writing to the file went successfully or not
     */
    public boolean overwrite(String data) {
        StringBuilder data_builder = new StringBuilder(data);
        if (exists()) {
            try (FileWriter writeFile = new FileWriter(file); BufferedWriter buffer = new BufferedWriter(writeFile)) {
                buffer.write(data_builder.toString());

               buffer.close();
               writeFile.close();
               return true;
            } catch (IOException e) {
                Testing.Error.println(String.format("There was an error writing to the following file: %s", getFull_path()));
            }
        } else {
            Testing.Validator.println(String.format("File: %s | doesn't exist", getFull_path()));
        }
        return false;
    }
    /**
     * Appends/ writes data to the file
     * @author Valedyn
     * @since 1.2
     * @param data String to be written to the file
     * @return boolean, true or false depending on whether the writing to the file went successfully or not
     */
    public boolean write(String data){
            String read_data = read();
            if(read_data == null){
                return false;
            }else{
                return overwrite(read_data + data);
            }
    }
    /**
     * Appends data to the file, data will be surrounded by the delimiter character
     * @author Valedyn
     * @since 1.2
     * @param data String to be written to the file
     * @param delimiter char that will surround the data String
     * @return boolean, true or false depending on whether the writing to the file went successfully or not
     */
    public boolean write(String data, char delimiter) {
        String read_data = read();
        if(read_data == null){
            return false;
        }else{
            return overwrite(read_data + delimiter + data + delimiter);
        }
    }

    /**
     * Returns true or false, depending on whether the file still exists.
     * @author Valedyn
     * @since 1.2
     * @return boolean
     */
    public boolean exists() {
        return file.exists();
    }

    /**
     * Returns the absolute path to the parent directory of the file as a String.
     * @author Valedyn
     * @since 1.0
     * @return String of the path to the parent directory of the file.
     */
    public String getPath() {
        return full_path;
    }

    /**
     * Returns the absolute path of the file as a String.
     * @author Valedyn
     * @since 1.0
     * @return String of the path of the file.
     */
    public String getFull_path() {
        return full_path;
    }

    /**
     * Returns the name of the file.
     * @author Valedyn
     * @since 1.0
     * @return String of the name of the file.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the extension of the file.
     * @author Valedyn
     * @since 1.0
     * @return String of the extension of the file.
     */
    public String getExtension() {
        return extension;
    }
}
