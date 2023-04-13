package Setup;

import java.io.File;
import java.util.Locale;

/**
 * This class is meant to set up the directory for the main program.
 * @author Valedyn
 * @version 1.0
 */

public abstract class Directory {

    /**
     * This method creates a directory in the directory where app data is usually stored.
     * Windows: %userprofile%\Appdata\Local
     * Linux: $HOME
     * Mac: Library/Application Support/
     * @author Valedyn
     * @since 1.0
     * @param name_of_directory tne name of the directory that is to be created
     */
    public static void setup(String name_of_directory){
        String appdata_directory;

        try{
            appdata_directory = Directory.return_Appdata_Location() + name_of_directory;
            File setup_directory = new File(appdata_directory);

            if(setup_directory.exists()){
                Testing.Test.println("File/directory already exists!");
                if(setup_directory.isDirectory()){
                    Testing.Test.println("Confirmed that the directory is, in fact, a directory!");
                }
            }else{
                if(setup_directory.mkdirs()){
                   Testing.Test.println("Successfully created the directory!");
                }else {
                    Testing.Test.println("It seems that there was an error while creating the directory!");
                }
            }
            Testing.Test.println(String.format("%s is the path of the directory which was meant to be created.", appdata_directory));
        }catch(SecurityException securityException){
            Testing.Error.println("Security manager rejected access to user.home!");
        }catch(NullPointerException nullPointerException){
            Testing.Error.println("Property user.home doesn't exist!");
        }catch(IllegalArgumentException illegalArgumentException){
            Testing.Error.println("A wrong property seems to have been given!");
        }

    }

    public static String return_Appdata_Location(){

        String operating_system = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        String appdata_directory = System.getProperty("user.home");
        if(operating_system.contains("win")){
            appdata_directory += "\\Appdata\\Local\\";
        }else if(operating_system.contains("mac")){
            appdata_directory += "Library/Application Support/";
        }else {
            appdata_directory += "/";
        }

        return appdata_directory;
    }


}
