package com.bumble.model.promotional;

/**
 * Created by gurmail on 2020-01-10.
 *
 * @author gurmail
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomAttributes {
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("deeplink")
    @Expose
    private String deeplink;
    @SerializedName("data")
    @Expose
    private Object data;
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public class Image {

        @SerializedName("image_url")
        @Expose
        private String imageUrl;
        @SerializedName("thumbnail_url")
        @Expose
        private String thumbnailUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

    }
}