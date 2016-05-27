package nekio.library.common.structure.model;

/**
 *
 * @author Nekio <nekio@outlook.com>
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nekio.library.common.structure.enums.DataType;

public class StructureDefinition {
    private String module;
    private List<String> catalogs;
    private Map<String, DataType> fields;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public List<String> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<String> catalogs) {
        this.catalogs = catalogs;
    }

    public Map<String, DataType> getFields() {
        return fields;
    }

    public void setFields(Map<String, DataType> fields) {
        Map<String, DataType> castedMap = new HashMap<String, DataType>();
        DataType dataType = null;
        for(Map.Entry set: fields.entrySet()){
            if(set.getValue() instanceof String){
                dataType = DataType.valueOf(set.getValue().toString());
            }
            
            castedMap.put(set.getKey().toString(), dataType);
        }
        
        if(castedMap.size() > 0)
            this.fields = castedMap;
        else
            this.fields = fields;
    }
}
