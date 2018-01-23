package com.xlf.common.po;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 图片表
 * Created by Administrator on 2017/8/17.
 */
@Table(name = "use_image")
public class UseImagePo {

    /**
     * '主键编号'
     */
    @Id
    private String id;
    /**
     * '关联主键编号'
     */
    private String relationId;
    /**
     * '图片路径'
     */
    private String imgPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
