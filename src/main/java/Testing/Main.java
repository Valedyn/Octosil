package Testing;

import Setup.Directory;
import Setup.File;
import Vital.Calendar;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Directory.setup("Octosil");
        Setup.File file = new File(Directory.return_Appdata_Location() + "Octosil", "Data", "txt");
        file.create();
        file.overwrite("no");
        System.out.println(file.read());
        file.write("crack", ' ');
        System.out.println(file.read());

        Calendar calendar = new Calendar();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(calendar.getCurrent_date());
        }
    }

