import Setup.Directory;
import Setup.File;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Directory.setup("Octosil");
        Setup.File file = new File(Directory.return_Appdata_Location() + "Octosil", "Data", "txt");

        }
    }

