package edu.ustc.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SendPicsInfo")
public class SendPicturesInfo {

    @XmlElement(name = "Count")
    private Integer count;

    private PictureList pictureList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public PictureList getPictureList() {
        return pictureList;
    }

    public void setPictureList(PictureList pictureList) {
        this.pictureList = pictureList;
    }
}
