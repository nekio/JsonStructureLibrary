package nekio.library.common.structure.paths;

/**
 *
 * @author Nekio <nekio@outlook.com>
 */

import java.io.File;
import nekio.library.common.structure.constants.Globals;

public class Paths {    
    private static String main;
    private static String module;
    private static String content;
    private static String structure;
    private static String catalogs;
    private static String definition;

    public static void setMain(String main) {
        Paths.main = main;
        
        Paths.module = main.substring(main.lastIndexOf(File.separator) + 1);
        Paths.content = main + File.separator + Globals.CONTENT;
        Paths.structure = main + File.separator + Globals.STRUCTURE;
        Paths.catalogs = structure + File.separator + Globals.CATALOG;
        Paths.definition = structure + File.separator + Globals.DEFINITION + Globals.EXTENSION;
    }
    
    public static String getMain() {
        return main;
    }

    public static String getModule() {
        return module;
    }

    public static String getContent() {
        return content;
    }

    public static String getStructure() {
        return structure;
    }
    
    public static String getCatalogs() {
        return catalogs;
    }
    
    public static String getDefinition() {
        return definition;
    }
}
