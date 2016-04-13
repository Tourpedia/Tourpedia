package com.example.ebtes_000.tourpedia.CloudSight;

/**
 * Created by bashayer on 16/02/2016.
 */

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.MultipartContent;
import java.io.File;

/*
* this class for image post configuration
* this code is reused *
* */
public class CSPostConfig {
    private static final String MULTIPART_FORM_BOUNDARY_KEY = "boundary" ,
            MULTIPART_FORM_BOUNDARY_VALUE = "__END_OF_PART__", CONTENT_DISPOSITION = "Content-Disposition" ,
            FORM_DATA_FORMAT = "form-data; name=\"%s\"" ,FORM_DATA_FORMAT_FILE = "form-data; name=\"%s\"; filename=\"%s\"",
            IMAGE_REQUEST_FORM_KEY = "image_request[image]",REMOTE_URL_REQUEST_FORM_KEY = "image_request[remote_image_url]",
            LOCALE_FORM_KEY = "image_request[locale]" ,LANGUAGE_FORM_KEY = "image_request[language]",
            DEVICE_ID_FORM_KEY = "image_request[device_id]" ,LATITUDE_FORM_KEY = "image_request[latitude]",
            LONGITUDE_FORM_KEY = "image_request[longitude]" ,ALTITUDE_FORM_KEY = "image_request[altitude]",
            TTL_FORM_KEY = "image_request[ttl]" , FOCUS_X_FORM_KEY = "focus[X]" ,FOCUS_Y_FORM_KEY = "focus[Y]";

    // First thing to call
    public static Builder newBuilder() {
        System.out.println("newBuilder!");
        return new Builder();
    }

    private final File mImage;
    private final String RemoteImageUrl , Locale, mLanguage, mDeviceId;
    private final Double mLatitude , mLongitude, mAltitude;
    private final Integer mTtl ,mFocusX, mFocusY;
    private final MultipartContent mContent;

    // constructor to initialize the variables
    private CSPostConfig( File image, String remoteImageUrl, String locale, String language, String deviceId, Double latitude,
                          Double longitude, Double altitude, Integer ttl, Integer focusX, Integer focusY, MultipartContent content) {
       // System.out.println("Hey 2!");
        mImage = image;
        RemoteImageUrl = remoteImageUrl;
        Locale = locale;
        mLanguage = language;
        mDeviceId = deviceId;
        mLatitude = latitude;
        mLongitude = longitude;
        mAltitude = altitude;
        mTtl = ttl;
        mFocusX = focusX;
        mFocusY = focusY;
        mContent = content;
    }


    /*
    * get methods
    * */
    public String getRemoteImageUrl() {
        return RemoteImageUrl;
    }

    public MultipartContent getContent() {
        return mContent;
    }


    // inner class that really build the image and post it
    public static class Builder {

        public static final String DEFAULT_LOCALE = "en";
        private File mImage;
        private String RemoteImageUrl, mLocale, mLanguage, mDeviceId;
        private Double mLatitude, mLongitude, mAltitude;
        private Integer mTtl, mFocusX, mFocusY;


        private Builder() {
            super();
        }

        public Builder withRemoteImageUrl(final String url) {
            RemoteImageUrl = url;
            return this;
        }


        // add image content
        private void addPart( final MultipartContent content, final String formKey, final Object formValue) {
            if (null != formValue) {
                final MultipartContent.Part part = new MultipartContent.Part(
                        ByteArrayContent.fromString(null, formValue.toString()) );
                part.setHeaders( new HttpHeaders().set( CONTENT_DISPOSITION, String.format(FORM_DATA_FORMAT, formKey)));
                content.addPart(part);
            }
        }


        // build the image
        public CSPostConfig build() throws IllegalStateException {
           // System.out.println("Hey one i don't find vars content!");
            if ((null == mImage && null == RemoteImageUrl) || (null != mImage && null != RemoteImageUrl) ) {
                throw new IllegalStateException("Exactly one of image or remote image url must be provided.");
            }

            final MultipartContent content = new MultipartContent().setMediaType( new HttpMediaType(CSApi.CONTENT_TYPE)
                    .setParameter(MULTIPART_FORM_BOUNDARY_KEY, MULTIPART_FORM_BOUNDARY_VALUE) );


            if (null != mImage) {
                final FileContent fileContent = new FileContent(null, mImage);
                final MultipartContent.Part image = new MultipartContent.Part(fileContent);
                image.setHeaders(
                        new HttpHeaders().set( CONTENT_DISPOSITION,
                                String.format(FORM_DATA_FORMAT_FILE, IMAGE_REQUEST_FORM_KEY, mImage.getName()) ) );
                content.addPart(image);
            } else {
                addPart(content, REMOTE_URL_REQUEST_FORM_KEY, RemoteImageUrl);
            }

            if (null == mLocale) {
                mLocale = DEFAULT_LOCALE;
            }

            addPart(content, LOCALE_FORM_KEY, mLocale);
            addPart(content, LANGUAGE_FORM_KEY, mLanguage);
            addPart(content, DEVICE_ID_FORM_KEY, mDeviceId);
            addPart(content, LATITUDE_FORM_KEY, mLatitude);
            addPart(content, LONGITUDE_FORM_KEY, mLongitude);
            addPart(content, ALTITUDE_FORM_KEY, mAltitude);
            addPart(content, TTL_FORM_KEY, mTtl);
            addPart(content, FOCUS_X_FORM_KEY, mFocusX);
            addPart(content, FOCUS_Y_FORM_KEY, mFocusY);

            return new CSPostConfig( mImage, RemoteImageUrl, mLocale, mLanguage, mDeviceId, mLatitude, mLongitude, mAltitude,
                    mTtl, mFocusX, mFocusY, content );
        }

    }
}
