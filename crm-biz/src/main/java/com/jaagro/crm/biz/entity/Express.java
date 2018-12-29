package com.jaagro.crm.biz.entity;

import java.util.Date;

public class Express {
    /**
     * 智能直通车id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 类别:1智库类；2直通车
     */
    private Integer categoryId;

    /**
     * 子类别id
     */
    private Integer childCategoryId;

    /**
     * 附件url
     */
    private String attachUrl;

    /**
     * 归档标记
     */
    private String toDocument;

    /**
     * 标签
     */
    private String tags;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 是否有效：1-有效 0-无效
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Integer modifyUserId;

    /**
     * 智能直通车id
     * @return id 智能直通车id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 智能直通车id
     * @param id 智能直通车id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 标题
     * @return title 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 标题
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 内容
     * @return content 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 内容
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 类别:1智库类；2直通车
     * @return category_id 类别:1智库类；2直通车
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 类别:1智库类；2直通车
     * @param categoryId 类别:1智库类；2直通车
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 子类别id
     * @return child_category_id 子类别id
     */
    public Integer getChildCategoryId() {
        return childCategoryId;
    }

    /**
     * 子类别id
     * @param childCategoryId 子类别id
     */
    public void setChildCategoryId(Integer childCategoryId) {
        this.childCategoryId = childCategoryId;
    }

    /**
     * 附件url
     * @return attach_url 附件url
     */
    public String getAttachUrl() {
        return attachUrl;
    }

    /**
     * 附件url
     * @param attachUrl 附件url
     */
    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl == null ? null : attachUrl.trim();
    }

    /**
     * 归档标记
     * @return to_document 归档标记
     */
    public String getToDocument() {
        return toDocument;
    }

    /**
     * 归档标记
     * @param toDocument 归档标记
     */
    public void setToDocument(String toDocument) {
        this.toDocument = toDocument == null ? null : toDocument.trim();
    }

    /**
     * 标签
     * @return tags 标签
     */
    public String getTags() {
        return tags;
    }

    /**
     * 标签
     * @param tags 标签
     */
    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    /**
     * 发布时间
     * @return publish_time 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 发布时间
     * @param publishTime 发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
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

    /**
     * 创建人
     * @return create_user_id 创建人
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人
     * @param createUserId 创建人
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 修改时间
     * @return modify_time 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 修改时间
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 修改人
     * @return modify_user_id 修改人
     */
    public Integer getModifyUserId() {
        return modifyUserId;
    }

    /**
     * 修改人
     * @param modifyUserId 修改人
     */
    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }
}