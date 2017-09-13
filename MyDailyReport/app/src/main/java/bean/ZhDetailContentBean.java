package bean;

/**
 * Created by Administrator on 2017/9/11 0011.
 * 具体内容
 */
public class ZhDetailContentBean {
    private String body;
    private String title;
    private String image;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ZhDetailContentBean{" +
                "body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
