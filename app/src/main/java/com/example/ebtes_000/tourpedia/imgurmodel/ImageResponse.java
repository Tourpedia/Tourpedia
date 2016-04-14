package com.example.ebtes_000.tourpedia.imgurmodel;

/**
 * this code is reused and it is created by AKiniyalocts
 * class responsible for response from imgur when uploading to the server.
 */
public class ImageResponse {
  public boolean success;
  public int status;
  public UploadedImage data;

  public static class UploadedImage{
    public String id, title,description, type, vote, account_url, deletehash,name, link  ;
    public boolean animated, favorite;
    public int width, height, size, views, bandwidth ;

    @Override
    public String toString() {
      return "UploadedImage{" +
          "id='" + id + '\'' +
          ", title='" + title + '\'' +
          ", description='" + description + '\'' +
          ", type='" + type + '\'' +
          ", animated=" + animated +
          ", width=" + width +
          ", height=" + height +
          ", size=" + size +
          ", views=" + views +
          ", bandwidth=" + bandwidth +
          ", vote='" + vote + '\'' +
          ", favorite=" + favorite +
          ", account_url='" + account_url + '\'' +
          ", deletehash='" + deletehash + '\'' +
          ", name='" + name + '\'' +
          ", link='" + link + '\'' +
          '}';
    }
  }

  @Override
  public String toString() {
    return "ImageResponse{" +
        "success=" + success +
        ", status=" + status +
        ", data=" + data.toString() +
        '}';
  }
}
