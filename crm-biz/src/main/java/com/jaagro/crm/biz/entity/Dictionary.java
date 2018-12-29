package com.jaagro.crm.biz.entity;

import java.util.Date;

public class Dictionary {
    /**
     * 通用字典表id
     */
    private Integer id;

    /**
     * 类型英文名
     */
    private String type;

    /**
     * 类型中文名称
     */
    private String typeName;

    /**
     * 展示顺序,值越小越靠前
     */
    private Integer displayOrder;

    /**
     * 
     */
    private String category;

    /**
     * 所属的类别
     */
    private String categoryName;

    /**
     * 是否有效：1-有效 0-无效
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 通用字典表id
     * @return id 通用字典表id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 通用字典表id
     * @param id 通用字典表id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 类型英文名
     * @return type 类型英文名
     */
    public String getType() {
        return type;
    }

    /**
     * 类型英文名
     * @param type 类型英文名
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 类型中文名称
     * @return type_name 类型中文名称
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 类型中文名称
     * @param typeName 类型中文名称
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    /**
     * 展示顺序,值越小越靠前
     * @return display_order 展示顺序,值越小越靠前
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    /**
     * 展示顺序,值越小越靠前
     * @param displayOrder 展示顺序,值越小越靠前
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * 
     * @return category 
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category 
     */
    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    /**
     * 所属的类别
     * @return category_name 所属的类别
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 所属的类别
     * @param categoryName 所属的类别
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    /**
     * 是否有效：1-有效 0-无效
     * @return enable 是否有效：1-有效 0-无效
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * 是否有效：1-有效 0-无效
     * @param enable 是否有效：1-有效 0-无效
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}