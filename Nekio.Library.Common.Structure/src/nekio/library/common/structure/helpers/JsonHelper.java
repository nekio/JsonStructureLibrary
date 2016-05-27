package nekio.library.common.structure.helpers;

/**
 *
 * @author Nekio <nekio@outlook.com>
 */

import java.util.List;
import java.util.Map;
import nekio.library.common.structure.constants.Globals;
import nekio.library.common.structure.model.Content;
import nekio.library.common.structure.model.DetailContent;
import nekio.library.common.structure.model.Dictionary;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonHelper {
    public static JSONObject getJsonFromContent(Content content){
        JSONObject json = new JSONObject();       
        
        // Single Fields
        Map<String, String> singleFields = content.getSingleFields();
        for(Map.Entry set: singleFields.entrySet()){
            json.put(set.getKey().toString(), set.getValue());
        }
        
        // Pair Fields
        List<Dictionary> pairFields = content.getPairFields();
        for(Dictionary pairField : pairFields){
            Map element = pairField.getContent();
            json.put(pairField.getTitle(), element);
        }
        
        // Detail Fields
        List<DetailContent> detailFields = content.getDetailFields();
        for(DetailContent detailField: detailFields){
            List<Dictionary> listElements = detailField.getDetailFields();
            
            JSONObject jsonInner = new JSONObject(); 
            for(Dictionary pairField : listElements){
                jsonInner.put(pairField.getTitle(), pairField.getContent());
            }
            
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonInner);
            
            json.put(detailField.getDetailDescription(), jsonArray);            
        }
        
        return json;
    }
    
    public static String formatJson(String jsonText){
        StringBuilder formattedText = new StringBuilder();
        
        int tabSpaces = Globals.TAB_SPACES;
        int level = 0;
        char index;
        String pad = null;
        
        for(int i = 0; i < jsonText.length(); i++){
            index = jsonText.charAt(i);
            
            if(index == '{' || index == '['){
                level++;
                pad = padding(level * tabSpaces);
                
                formattedText.append(index);
                formattedText.append("\n");
                formattedText.append(pad);
            }else if(index == '}' || index == ']'){
                level--;
                pad = padding(level * tabSpaces);
                
                formattedText.append("\n");
                formattedText.append(pad);
                formattedText.append(index);
            }else if(index == ','){                
                formattedText.append(index);
                
                if(level == 1)
                    formattedText.append("\n\n");
                else
                    formattedText.append("\n");
                
                formattedText.append(pad);
            }else{
                formattedText.append(index);
            }
        }
        
        return formattedText.toString();
    }
    
    private static String padding(int pad){
        String padding = "";
        
        for(int i = 0; i < pad; i++)
            padding += " ";
            
        return padding;
    }
}
