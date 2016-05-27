package nekio.library.common.structure.paths;

/**
 *
 * @author Nekio <nekio@outlook.com>
 */

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import nekio.library.common.structure.model.DateValues;
import nekio.library.common.structure.constants.Globals;
import static nekio.library.common.structure.constants.Globals.EXTENSION;
import nekio.library.common.structure.helpers.FileHelper;
import org.json.JSONObject;

public class ContentNamer {
    private static String currentFolder;
    
    public static String getCurrentFolder() {
        return currentFolder;
    }
    
    public static void createFile(String jsonText){
        createFile(new JSONObject(jsonText));
    }
    
    public static void createFile(JSONObject json){
        checkFolders();
        
        String filename = getFileName() + Globals.EXTENSION;
        FileHelper.writeJsonFile(currentFolder, filename, json);
    }
    
    public static void createFile(JSONObject json, String folder, String filename){
        FileHelper.writeJsonFile(folder, filename, json);
    }
    
    public static DateValues getFileDate(File file){
        String fileName = file.getName();
        fileName = fileName.replaceAll(EXTENSION, "");  
        
        DateValues dateValues = new DateValues();
        
        String[] values = fileName.split("-");
        String date = values[1];
        String time = values[2];
        
        String[] dateArray = date.split("_");
        dateValues.getDay().setYear(Integer.parseInt(dateArray[0]));
        dateValues.getDay().setMonth(Integer.parseInt(dateArray[1]));
        dateValues.getDay().setDay(Integer.parseInt(dateArray[2]));
        
        String[] timeArray = time.split("_");
        dateValues.setHour(Integer.parseInt(timeArray[0]));
        dateValues.setMinute(Integer.parseInt(timeArray[1]));
        dateValues.setSecond(Integer.parseInt(timeArray[2]));
        dateValues.setMillisecond(Integer.parseInt(timeArray[3]));
        
        return dateValues;
    }
    
    private static void checkFolders(){
        String contentFolder = Paths.getContent();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        File folderYear = new File(contentFolder + File.separator + year);
        if(!folderYear.exists())
            folderYear.mkdir();
        
        File folderMonth = new File(folderYear.getPath() + File.separator + format(month, 2));
        if(!folderMonth.exists())
            folderMonth.mkdir();
        
        File folderDay = new File(folderMonth.getPath() + File.separator + format(day, 2));
        if(!folderDay.exists())
            folderDay.mkdir();
        
        currentFolder = folderDay.getPath();
    }
    
    public static String getFileName(){
        String fileName = null;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int millisec = calendar.get(Calendar.MILLISECOND);
        
        fileName = Paths.getModule() + "-" + 
                year + "_" + format(month, 2) + "_" + format(day, 2) + "-" + 
                format(hour, 2) + "_" + format(minutes, 2) + "_" + format(seconds, 2) + "_" + format(millisec, 3);
        
        return fileName;
    }
    
    public static String format(int number, int padding){
        return format("" + number, padding);
    }
    
    public static String format(String text, int padding){
        if(text.length() < padding){            
            text = format("0" + text, padding);
        }
        
        return text;
    }
}
