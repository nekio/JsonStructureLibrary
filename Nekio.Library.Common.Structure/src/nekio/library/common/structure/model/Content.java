package nekio.library.common.structure.model;

/**
 *
 * @author Nekio <nekio@outlook.com>
 */

import java.util.List;
import java.util.Map;

public class Content {
    private Map<String, String> singleFields;
    private List<Dictionary> pairFields;
    private List<DetailContent> detailFields;

    public Map<String, String> getSingleFields() {
        return singleFields;
    }

    public void setSingleFields(Map<String, String> singleFields) {
        this.singleFields = singleFields;
    }

    public List<Dictionary> getPairFields() {
        return pairFields;
    }

    public void setPairFields(List<Dictionary> pairFields) {
        this.pairFields = pairFields;
    }

    public List<DetailContent> getDetailFields() {
        return detailFields;
    }

    public void setDetailFields(List<DetailContent> detailFields) {
        this.detailFields = detailFields;
    }
}
