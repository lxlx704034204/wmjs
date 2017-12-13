package com.eling.elcms.membermemorial.model;

import com.eling.elcms.basedoc.model.EnumSerializer;
import com.eling.elcms.basedoc.model.PersonalInfo;
import com.eling.elcms.core.dao.annotation.Between;
import com.eling.elcms.core.dao.annotation.CriteriaEnhancer;
import com.eling.elcms.core.dao.annotation.CriteriaEnhancers;
import com.eling.elcms.core.dao.annotation.In;
import com.eling.elcms.core.dao.hibernate.OrderCriteriaEnhancer;
import com.eling.elcms.core.dao.hibernate.search.EnumBridge;
import com.eling.elcms.core.model.BaseModel;
import com.eling.elcms.system.model.CommonUser;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;

/**
 * 长者祭奠详细
 * Created by zhoulin on 2017/8/1.
 */
@Entity
@Table(name = "mem_memorialdetail")
@Indexed
@CriteriaEnhancers(criteriaEnhancers = {
        @CriteriaEnhancer(impl = OrderCriteriaEnhancer.class)})
public class MemorialDetail extends BaseModel {
    private static final long serialVersionUID = 4966902978290589202L;

    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkMemorialDetail;

    /**
     * 被祭奠人
     */
    @ManyToOne
    @JoinColumn(name = "pkMemberMemorial")
    @IndexedEmbedded(depth = 1)
    private MemberMemorial memberMemorial;

    /**
     * 祭奠人
     */
    @ManyToOne
    @JoinColumn(name = "pkPersonalInfo")
    @IndexedEmbedded(depth = 1)
    private PersonalInfo personalInfo;

    /**
     * 祭奠人（廊坊平台）
     */
    @ManyToOne
    @JoinColumn(name = "pkUser")
    @IndexedEmbedded(depth = 1)
    private CommonUser commonUser;

    /**
     * 祭奠时间
     */
    @Column
    @Between
    private Date memorialDate;

    /**
     * 数量
     */
    @Column
    @Field
    private Integer counts;

    /**
     * 祭奠类型
     */
    @Enumerated(EnumType.STRING)
    @Field
    @In
    @FieldBridge(impl = EnumBridge.class)
    private MemorialType memorialType;

    /**
     * 坐标
     */
    @Column
    @Field
    private String coordinate;

    /**
     * 图片路径
     */
    @Column
    @Field
    private String imagePath;

    /**
     * 有效期
     */
    @Column
    @Between
    private Date validPeriod;


    @Version
    private Integer version;

    @JsonSerialize(using = EnumSerializer.class)
    public static enum MemorialType {
        Flower("献花"),
        Worship("祭拜"),
        Censer("香炉"),
        Candle("香烛"),
        Tribute("贡品"),
        Scenes("场景"),
        Clifford("祈福");

        private String display;

        MemorialType(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }
    }

    public CommonUser getCommonUser() {
        return commonUser;
    }

    public void setCommonUser(CommonUser commonUser) {
        this.commonUser = commonUser;
    }

    public Long getPkMemorialDetail() {
        return pkMemorialDetail;
    }

    public void setPkMemorialDetail(Long pkMemorialDetail) {
        this.pkMemorialDetail = pkMemorialDetail;
    }

    public MemberMemorial getMemberMemorial() {
        return memberMemorial;
    }

    public void setMemberMemorial(MemberMemorial memberMemorial) {
        this.memberMemorial = memberMemorial;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public Date getMemorialDate() {
        return memorialDate;
    }

    public void setMemorialDate(Date memorialDate) {
        this.memorialDate = memorialDate;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public MemorialType getMemorialType() {
        return memorialType;
    }

    public void setMemorialType(MemorialType memorialType) {
        this.memorialType = memorialType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(Date validPeriod) {
        this.validPeriod = validPeriod;
    }
}
