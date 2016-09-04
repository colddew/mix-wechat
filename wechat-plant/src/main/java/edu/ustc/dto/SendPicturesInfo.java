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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PicList")
class PictureList {

    @XmlElement(name = "item")
    private String item;

    @XmlElement(name = "PicMd5Sum")
    private String md5Sum;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMd5Sum() {
        return md5Sum;
    }

    public void setMd5Sum(String md5Sum) {
        this.md5Sum = md5Sum;
    }
}