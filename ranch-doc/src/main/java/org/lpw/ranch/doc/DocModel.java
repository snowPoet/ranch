package org.lpw.ranch.doc;

import org.lpw.tephra.dao.model.Jsonable;
import org.lpw.ranch.audit.AuditModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.sql.Timestamp;

/**
 * @author lpw
 */
@Component(DocModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = DocModel.NAME)
@Table(name = "t_doc")
public class DocModel extends AuditModelSupport {
    static final String NAME = "ranch.doc";

    private String key; // 类型KEY
    private String owner; // 所有者ID
    private String author; // 作者ID
    private int scoreMin; // 最小分值
    private int scoreMax; // 最大分值
    private int sort; // 顺序
    private String subject; // 标题
    private String image; // 主图URI地址
    private String thumbnail; // 缩略图URI地址
    private String summary; // 摘要
    private String label; // 标签
    private String source; // 内容源
    private String content; // 内容
    private int read; // 阅读次数
    private int favorite; // 收藏次数
    private int comment; // 评论次数
    private int praise; // 点赞数
    private int score; // 得分
    private Timestamp time; // 更新时间

    @Jsonable
    @Column(name = "c_key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Jsonable
    @Column(name = "c_owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Jsonable
    @Column(name = "c_author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Jsonable
    @Column(name = "c_score_min")
    public int getScoreMin() {
        return scoreMin;
    }

    public void setScoreMin(int scoreMin) {
        this.scoreMin = scoreMin;
    }

    @Jsonable
    @Column(name = "c_score_max")
    public int getScoreMax() {
        return scoreMax;
    }

    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
    }

    @Jsonable
    @Column(name = "c_sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Jsonable
    @Column(name = "c_subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Jsonable
    @Column(name = "c_image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Jsonable
    @Column(name = "c_thumbnail")
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Jsonable
    @Column(name = "c_summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Jsonable
    @Column(name = "c_label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(name = "c_source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "c_content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Jsonable
    @Column(name = "c_read")
    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    @Jsonable
    @Column(name = "c_favorite")
    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    @Jsonable
    @Column(name = "c_comment")
    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    @Jsonable
    @Column(name = "c_praise")
    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    @Jsonable
    @Column(name = "c_score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Jsonable
    @Column(name = "c_time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
