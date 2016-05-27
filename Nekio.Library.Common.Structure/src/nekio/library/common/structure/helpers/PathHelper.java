package nekio.library.common.structure.helpers;

/**
 *
 * @author Nekio <nekio@outlook.com>
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import nekio.library.common.structure.model.Day;
import nekio.library.common.structure.paths.ContentNamer;

public class PathHelper {    
    private int daysBack;
    private List<Day> days;
    
    public PathHelper(){
        this(new Date(), 40);
    }
    
    public PathHelper(Date date, int daysBack){
        this.daysBack = daysBack;
        
        days = new ArrayList<Day>();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for(int i = 0; i < daysBack; i++){
            days.add(PathHelper.calendarToDay(calendar));
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
        }
        
        calendar = null;
    }
    
    public int getDaysBack() {
        return daysBack;
    }
    
    public List<Day> getDays() {
        return days;
    }
    
    public List<String> getPaths(){
        List<String> paths = new ArrayList<String>();
        for(Day day : days)
            paths.add(
                    File.separator + day.getYear() + File.separator + 
                    ContentNamer.format(day.getMonth(), 2) + File.separator + 
                    ContentNamer.format(day.getDay(), 2) + File.separator
            );
        
        return paths;
    }
    
    public List<String> getAvailablePaths(String directory, List<String> rawPaths){
        List<String> availablePaths = new ArrayList<String>();
        
        File file = null;
        for(String path : rawPaths){
            String folder = directory + path;
            file = new File(folder);
            
            if(file.exists()){
                availablePaths.add(folder);
            }
        }
        
        return availablePaths;
    }
    
    public List<String> getAvailableFiles(String availablePath){
        List<String> availableFiles = new ArrayList<String>();
        
        File file = null;
        String[] files = null;

        file = new File(availablePath);
        files = file.list();
        if(files.length != 0){
            for(String jsonFile : files){
                availableFiles.add(jsonFile);
            }
        }
        
        return availableFiles;
    }
    
    public static Day calendarToDay(Calendar calendar){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        return new Day(year, month, day);
    }
    
    public static Calendar dayToCalendar(Day day){
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.YEAR, day.getYear());
        calendar.set(Calendar.MONTH, day.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, day.getDay());
        
        return calendar;
    }
}
