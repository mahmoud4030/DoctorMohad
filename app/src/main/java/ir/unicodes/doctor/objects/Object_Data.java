package ir.unicodes.doctor.objects;

public class Object_Data {
    public String sid;
    public String faction;
    public String title;
    public String content;
    public String image_url;
    public String favorite;
    public String Category;
    public String CategoryT;
    public String Eng;

    public Object_Data(
            String sid,
            String faction,
            String title,
            String content,
            String image_url,
            String favorite,
            String Category,
            String CategoryT,
            String Eng)
    {
        this.sid=sid;
        this.faction=faction;
        this.title=title;
        this.content=content;
        this.image_url=image_url;
        this.favorite=favorite;
        this.Category=Category;
        this.CategoryT=CategoryT;
        this.Eng = Eng;

    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCategoryT() {
        return CategoryT;
    }

    public void setCategoryT(String categoryT) {
        CategoryT = categoryT;
    }

    public String getEng() {
        return Eng;
    }

    public void setEng(String eng) {
        Eng = eng;
    }

}// end class
