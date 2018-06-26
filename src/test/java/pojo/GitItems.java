package pojo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
public class GitItems {
    @JsonProperty("name")
    private String nameLicense;

    @JsonProperty("search")
    List<GitItems> gitItems;

    @JsonProperty("response")
    private String response;

    public List<GitItems> getGitItems() {
        return gitItems;
    }
    public void setGitItems(List<GitItems> gitItems) {
        this.gitItems = gitItems;
    }

    public String getNameLicense() {
        return nameLicense;
    }
    public void setNameLicense(String nameLicense) {
        this.nameLicense = nameLicense;
    }

    public String getResponce() {
        return response;
    }
    public void setResponce(String responce) {
        this.response = responce;
    }
}
