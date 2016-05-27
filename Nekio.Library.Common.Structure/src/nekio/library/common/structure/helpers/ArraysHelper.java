package nekio.library.common.structure.helpers;

/**
 *
 * @author Nekio <nekio@outlook.com>
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nekio.library.common.structure.model.Dictionary;
import nekio.library.common.json.JsonConverter;
import nekio.library.common.structure.constants.Globals;
import nekio.library.common.structure.model.Content;
import nekio.library.common.structure.model.DetailContent;
import nekio.library.common.structure.model.StructureDefinition;
import org.json.JSONArray;
import org.json.JSONObject;

public class ArraysHelper {
    public StructureDefinition getStructure(String jsonFile) throws Exception{
        StructureDefinition structure = new StructureDefinition();
        
        String jsonText = JsonConverter.fileToText(jsonFile);
        JSONObject json = new JSONObject(jsonText);
        
        for(String key : json.keySet()){            
            Object jsonKey = json.get(key);
            
            if(jsonKey instanceof String){
                structure.setModule((String)jsonKey);
            }if(jsonKey instanceof JSONObject){
                structure.setFields(getDictionary(key,(JSONObject)jsonKey).getContent());
            }if(jsonKey instanceof JSONArray){
                JSONArray innerKeys = (JSONArray)jsonKey;
                
                List<String> catalogs = new ArrayList<String>();
                for(int i = 0; i < innerKeys.length(); i++){
                    catalogs.add((String)innerKeys.get(i));
                }
                
                structure.setCatalogs(catalogs);
            }
        }
        
        return structure;
    }
    
    public List<Dictionary> getCatalogs(String path) throws Exception{        
        List<Dictionary> catalogs = new ArrayList<Dictionary>();
        
        String jsonText = null;
        JSONObject json = null;
        
        for(String jsonFile : getCatalogItems(path)){            
            jsonText = JsonConverter.fileToText(path + File.separator + jsonFile);
            json = new JSONObject(jsonText);
            
            catalogs.add(getCatalogDictionary(json, jsonFile));
        }       
        
        return catalogs;
    }
    
    public Map<String, Boolean> getVisibleCatalogs(List<Dictionary> catalogs, StructureDefinition structure){
        Map<String, Boolean> visibleCatalogs = new HashMap<String, Boolean>();
        
        List<String> physicalCatalogs = new ArrayList<String>();
        for(Dictionary element : catalogs){
            physicalCatalogs.add(element.getTitle().replaceAll(Globals.EXTENSION, ""));
        }
        
        for(Object catalog : structure.getCatalogs().toArray()){
            String logicalCatalog = (String)catalog;

            if(physicalCatalogs.contains(logicalCatalog)){
                visibleCatalogs.put(logicalCatalog, true);
            }else{
                visibleCatalogs.put(logicalCatalog, false);
            }
        }

        return visibleCatalogs;
    }
    
    private List<String> getCatalogItems(String path){
        List<String> jsonFiles = new ArrayList<String>();
        
        File folder = new File(path);        
        for(String file : folder.list())
            jsonFiles.add(file);
        
        return jsonFiles;
    }
    
    private Dictionary getDictionary(String title, JSONObject json){
        return getDictionary(title, null, json);
    }
    
    private Dictionary getDictionary(String title, String description, JSONObject json){
        Dictionary dictionary = new Dictionary();
        Map<String, String> values = new HashMap<String, String>();
        for(String innerKey : json.keySet()){
            values.put(innerKey, String.valueOf(json.get(innerKey)));
        }

        dictionary.setTitle(title);
        dictionary.setDescription(description);
        dictionary.setContent(values);
        
        return dictionary;
    }
    
    private Dictionary getCatalogDictionary(JSONObject json, String jsonFile){
        Dictionary dictionary = new Dictionary();
        Map<Integer, String> mapList = new HashMap<Integer, String>();
        
        int key = 0;
        String  value = null;

        String mapTitle = json.keySet().iterator().next();
        JSONArray mainMap = (JSONArray)json.get(mapTitle);
        JSONObject rawList = (JSONObject)mainMap.get(0);

        JSONObject jsonId = null;
        for(Object elements : rawList.keySet()){
            value = (String) elements;
            
            jsonId = (JSONObject)rawList.get(value);
            key = (int)jsonId.get("id");
            
            mapList.put(key, value);
        }
        
        dictionary.setTitle(jsonFile);
        dictionary.setDescription(mapTitle);
        dictionary.setContent(mapList);
        
        return dictionary;
    }
    
    public Content getContent(String path) throws Exception{
        Content content = new Content();
        
        String jsonText = JsonConverter.fileToText(path);
        JSONObject json = new JSONObject(jsonText);
        
        Map<String, String> singleFields = new HashMap<String, String>();
        List<Dictionary> pairFields = new ArrayList<Dictionary>();
        List<DetailContent> detailFields = new ArrayList<DetailContent>();
        
        for(String key : json.keySet()){            
            Object jsonKey = json.get(key);
            
            if(jsonKey instanceof String){
                singleFields.put(key, (String)jsonKey);
            }if(jsonKey instanceof JSONObject){                
                pairFields.add(getDictionary(key, (JSONObject)jsonKey));
            }if(jsonKey instanceof JSONArray){
                JSONArray innerKeys = (JSONArray)jsonKey;
                DetailContent detail = new DetailContent();
                
                List<Dictionary> innerDictionary = new ArrayList<Dictionary>();
                for(int i = 0; i < innerKeys.length(); i++){
                    Object element = innerKeys.get(i);
                    
                    if(element instanceof JSONObject){
                        JSONObject innerJson = (JSONObject)element;
                        
                        for(String innerKey : innerJson.keySet()){
                            innerDictionary.add(getDictionary(innerKey, (JSONObject)innerJson.get(innerKey)));
                        }
                    }else if(element instanceof JSONArray){
                        // TO DO
                    }
                }
                
                detail.setDetailDescription(key);
                detail.setDetailFields(innerDictionary);
                
                detailFields.add(detail);
            }
        }
        
        content.setSingleFields(singleFields);
        content.setPairFields(pairFields);
        content.setDetailFields(detailFields);
        
        return content;
    }
}
