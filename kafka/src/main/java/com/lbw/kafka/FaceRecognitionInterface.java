package com.lbw.kafka;

/**
 * @author LBW
 * @Classname FaceRecognitionInterface
 * @Description TODO
 * @Date 2021/7/2 9:45
 */
public class FaceRecognitionInterface {
    private String Name;
    private String Faceimageurl;
    private String Pictureurl;
    private String Personid;
    private String Gender;
    private String Bornyear;
    private String Faceimageid;
    private String Similarity;


    public void setName(String name) {
        Name = name;
    }

    public void setFaceimageurl(String faceimageurl) {
        Faceimageurl = faceimageurl;
    }

    public void setPictureurl(String pictureurl) {
        Pictureurl = pictureurl;
    }

    public void setPersonid(String personid) {
        Personid = personid;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setBornyear(String bornyear) {
        Bornyear = bornyear;
    }

    public void setFaceimageid(String faceimageid) {
        Faceimageid = faceimageid;
    }

    public void setSimilarity(String similarity) {
        Similarity = similarity;
    }
}
