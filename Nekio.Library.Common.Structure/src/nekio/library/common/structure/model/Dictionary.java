package nekio.library.common.structure.model;

/**
 *
 * @author Nekio <nekio@outlook.com>
 */

import java.util.Map;

public class Dictionary{
    private String title;
    private String description;
    private Map content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Map getContent() {
        return content;
    }

    public void setContent(Map content) {
        this.content = content;
    }
}
