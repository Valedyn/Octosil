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
     * @param name_of_directory the name of the directory that is to be created
     */
    public static void setup(String name_of_directory){
        String appdata_directory;

        try{
            appdata_directory = Directory.return_Appdata_Location() + name_of_directory;
            File setup_directory = new File(appdata_directory);

            if(setup_directory.exists()){
                Testing.Validator.println(String.format("Directory: %s | already exists!", setup_directory.getAbsolutePath()));
                if(setup_directory.isDirectory()){
                    Testing.Validator.println(String.format("Confirmed that the directory: %s | is, in fact, a directory!", setup_directory.getAbsolutePath()));
                }
            }else{
                if(setup_directory.mkdirs()){
                   Testing.Validator.println(String.format("Successfully created the directory: %s !", setup_directory.getAbsolutePath()));
                }else {
                    Testing.Validator.println(String.format("It seems that there was an error while creating the directory: %s !", setup_directory.getAbsolutePath()));
                }
            }
        }catch(SecurityException securityException){
            Testing.Error.println("Security manager rejected access to user.home!");
        }catch(NullPointerException nullPointerException){
            Testing.Error.println("Property user.home doesn't exist!");
        }catch(IllegalArgumentException illegalArgumentException){
            Testing.Error.println("A wrong property seems to have been given!");
        }

    }
    /**
     * This method returns the the path to the directory where all necessary data is stored
     * @author Valedyn
     * @since 1.0
     * @returns String containing the location to the directory where the data is stored
     */
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
