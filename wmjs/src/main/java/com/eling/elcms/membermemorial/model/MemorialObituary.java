package com.eling.elcms.membermemorial.model;

import com.eling.elcms.basedoc.model.PersonalInfo;
import com.eling.elcms.core.dao.annotation.CriteriaEnhancer;
import com.eling.elcms.core.dao.annotation.CriteriaEnhancers;
import com.eling.elcms.core.dao.hibernate.OrderCriteriaEnhancer;
import com.eling.elcms.core.model.BaseModel;
import com.eling.elcms.system.model.CommonUser;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.Date;

/**
 * 祭奠悼文
 * Created by zhoulin on 2017/8/1.
 */
@Entity
@Indexed
@Table(name = "mem_memorialobituary")
@CriteriaEnhancers(criteriaEnhancers = {
        @CriteriaEnhancer(impl = OrderCriteriaEnhancer.class)})
public class MemorialObituary extends BaseModel {
    private static final long serialVersionUID = -1603121251450796939L;

    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkMemorialObituary;

    /**
     * 被祭奠人
     */
    @ManyToOne(fetch = FetchType.LAZY)
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
    @Field
    private Date memorialDate;

    /**
     * 祭奠内容
     */
    @Column
    @Field
    private String content;

    @Version
    private Integer version;

    public Long getPkMemorialObituary() {
        return pkMemorialObituary;
    }

    public void setPkMemorialObituary(Long pkMemorialObituary) {
        this.pkMemorialObituary = pkMemorialObituary;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public CommonUser getCommonUser() {
        return commonUser;
    }

    public void setCommonUser(CommonUser commonUser) {
        this.commonUser = commonUser;
    }
}
