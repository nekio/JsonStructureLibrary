package nekio.library.common.structure.helpers;

/**
 *
 * @author Nekio <nekio@outlook.com>
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.json.JSONObject;

public class FileHelper {
    public static boolean writeJsonFile(String path, String filename, JSONObject json){
        return writeFile(path, filename, JsonHelper.formatJson(json.toString()));
    }
    
    public static boolean writeXmlFile(String path, String filename, JSONObject json){
        return writeFile(path, filename, org.json.XML.toString(json));
    }
    
    public static boolean writeFile(String path, String filename, String content){
        boolean fileOk = false;
        
        try{
            File file = new File(path, filename);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            
            bw.write(content);
            
            bw.flush();
            bw.close();
            bw = null;
        }catch(Exception e){}
        
        return fileOk;
    }
    
    
}
