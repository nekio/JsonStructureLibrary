package nekio.library.common.structure.model;

/**
 *
 * @author Nekio <nekio@outlook.com>
 */

import java.util.List;

public class DetailContent {
    private String detailDescription;
    private List<Dictionary> detailFields;

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public List<Dictionary> getDetailFields() {
        return detailFields;
    }

    public void setDetailFields(List<Dictionary> detailFields) {
        this.detailFields = detailFields;
    }
}
