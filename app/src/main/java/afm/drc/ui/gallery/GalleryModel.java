package afm.drc.ui.gallery;

public class GalleryModel {
    private String name,description,imageURL;
    private Integer id;

    public GalleryModel(String name, String description, String imageURL, Integer id) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.id = id;
    }
    public String getName() {return name;}
    public String getDescription() { return description; }
    public String getImageURL() { return imageURL; }
    public Integer getId(){return id;}
}