package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitResponse {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("language")
    private String language;
    @JsonProperty("stargazers_count")
    private String stars;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStars() {
        return stars;
    }
    public void setStars(String stars) {
        this.stars = stars;
    }

}
