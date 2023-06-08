import Setup.Directory;
import Setup.File;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Directory.setup("Octosil");
        Setup.File file = new File(Directory.return_Appdata_Location() + "Octosil", "Data", "txt");
        file.create();
        file.overwrite("no");
        System.out.println(file.read());
        file.write("crack", ' ');
        System.out.println(file.read());
        }
    }

