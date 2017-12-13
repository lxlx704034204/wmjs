package com.eling.elcms.membermemorial.model;

import com.eling.elcms.basedoc.model.EnumSerializer;
import com.eling.elcms.basedoc.model.PersonalInfo;
import com.eling.elcms.core.dao.annotation.CriteriaEnhancer;
import com.eling.elcms.core.dao.annotation.CriteriaEnhancers;
import com.eling.elcms.core.dao.annotation.In;
import com.eling.elcms.core.dao.hibernate.OrderCriteriaEnhancer;
import com.eling.elcms.core.dao.hibernate.search.EnumBridge;
import com.eling.elcms.core.model.BaseModel;
import com.eling.elcms.member.model.Member;
import com.eling.elcms.system.model.CommonUser;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 长者祭奠
 * Created by zhoulin on 2017/8/1.
 */
@Entity
@Table(name = "mem_membermemorial")
@Indexed
@CriteriaEnhancers(criteriaEnhancers = {
        @CriteriaEnhancer(impl = OrderCriteriaEnhancer.class)})
public class MemberMemorial extends BaseModel {
    private static final long serialVersionUID = 1024776892601533962L;

    @Id
    @In
    @DocumentId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkMemberMemorial;

    /**
     * 长者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pkMember")
    @IndexedEmbedded(depth = 2)
    private Member member;

    /**
     * 被祭奠人类型
     */
    @Enumerated(EnumType.STRING)
    @Field
    @In
    @FieldBridge(impl = EnumBridge.class)
    private MemberMemorialType memberMemorialType;

    /**
     * 被祭奠人（用于平台）
     */
    @OneToOne
    @JoinColumn(name = "pkPersonalInfo")
    @IndexedEmbedded(depth = 1)
    private PersonalInfo personalInfo;

    /**
     * 纪念馆名称
     */
    @Column
    @Field
    private String memorialName;

    /**
     * 献花数
     */
    @Column
    @Field
    private Integer flowerCounts;

    /**
     * 祭拜数
     */
    @Column
    @Field
    private Integer worshipCounts;

    /**
     * 祭日
     */
    @Field
    @Column
    private Date deathDay;

    /**
     * 祈福数
     */
    @Column
    @Field
    private Integer cliffordCounts;

    /**
     * 祭奠悼文
     */
    @OneToMany(mappedBy = "memberMemorial", cascade = CascadeType.ALL)
    @OrderBy("pkMemorialObituary desc")
    private Set<MemorialObituary> memObituarySet;

    /**
     * 创建用户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pkUser")
    @IndexedEmbedded(depth = 1)
    private CommonUser creator;

    /**
     * 创建时间
     */
    @Field
    @Column
    private Date createDate;

    /**
     * 被关注情况
     */
    @OneToMany(mappedBy = "memberMemorial", cascade = CascadeType.ALL)
    @OrderBy("pkCareMemorial desc")
    private Set<CareMemorial> careMemorialSet;

    /**
     * 图片名
     */
    @Field
    @Column
    private String attachName;

    /**
     * 创建者与死者关系
     */
    @Field
    @Column
    private String relationship;

    /**
     * 逝者简介
     */
    @Field
    @Column
    private String introduction;

    /**
     * 逝者职业
     */
    @Field
    @Column
    private String profession;

    /**
     * 场景路径
     */
    @Field
    @Column
    private String scenesPath;


    /**
     * 墓碑
     */
    @Field
    @Column
    private String tombstone;

    @Version
    private Integer version;


    @JsonSerialize(using = EnumSerializer.class)
    public static enum MemberMemorialType {
        Celebrity("名人"),
        Common("普通人");

        private String display;

        MemberMemorialType(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }
    }

    public Date getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(Date deathDay) {
        this.deathDay = deathDay;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Set<CareMemorial> getCareMemorialSet() {
        return careMemorialSet;
    }

    public void setCareMemorialSet(Set<CareMemorial> careMemorialSet) {
        this.careMemorialSet = careMemorialSet;
    }

    public String getMemorialName() {
        return memorialName;
    }

    public void setMemorialName(String memorialName) {
        this.memorialName = memorialName;
    }

    public MemberMemorialType getMemberMemorialType() {
        return memberMemorialType;
    }

    public void setMemberMemorialType(MemberMemorialType memberMemorialType) {
        this.memberMemorialType = memberMemorialType;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public Long getPkMemberMemorial() {
        return pkMemberMemorial;
    }

    public void setPkMemberMemorial(Long pkMemberMemorial) {
        this.pkMemberMemorial = pkMemberMemorial;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getFlowerCounts() {
        return flowerCounts;
    }

    public void setFlowerCounts(Integer flowerCounts) {
        this.flowerCounts = flowerCounts;
    }

    public Integer getWorshipCounts() {
        return worshipCounts;
    }

    public void setWorshipCounts(Integer worshipCounts) {
        this.worshipCounts = worshipCounts;
    }

    public Integer getCliffordCounts() {
        return cliffordCounts;
    }

    public void setCliffordCounts(Integer cliffordCounts) {
        this.cliffordCounts = cliffordCounts;
    }

    public Set<MemorialObituary> getMemObituarySet() {
        return memObituarySet;
    }

    public void setMemObituarySet(Set<MemorialObituary> memObituarySet) {
        this.memObituarySet = memObituarySet;
    }

    public CommonUser getCreator() {
        return creator;
    }

    public void setCreator(CommonUser creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getScenesPath() {
        return scenesPath;
    }

    public void setScenesPath(String scenesPath) {
        this.scenesPath = scenesPath;
    }


    public String getTombstone() {
        return tombstone;
    }

    public void setTombstone(String tombstone) {
        this.tombstone = tombstone;
    }
}
