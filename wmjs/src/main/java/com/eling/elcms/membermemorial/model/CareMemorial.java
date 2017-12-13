package com.eling.elcms.membermemorial.model;

import com.eling.elcms.core.dao.annotation.In;
import com.eling.elcms.core.model.BaseModel;
import com.eling.elcms.system.model.CommonUser;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.Date;

/**
 * 关注祭奠馆（关注被祭奠人）
 * Created by Liuyp on 2017/9/11.
 */
@Entity
@Table(name = "mem_carememorial")
@Indexed
public class CareMemorial extends BaseModel{

    private static final long serialVersionUID = -9087787355288869684L;

    @Id
    @In
    @DocumentId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkCareMemorial;

    //关注时间
    @Field
    @Column
    private Date careTime;

    //关注的馆（人）
    @ManyToOne
    @JoinColumn(name = "pkMemberMemorial")
    @IndexedEmbedded(depth = 1)
    private MemberMemorial memberMemorial;


    /**
     * 关注的人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pkUser")
    @IndexedEmbedded(depth = 1)
    private CommonUser careUser;


    @Version
    private Integer version;

    public Long getPkCareMemorial() {
        return pkCareMemorial;
    }

    public void setPkCareMemorial(Long pkCareMemorial) {
        this.pkCareMemorial = pkCareMemorial;
    }

    public Date getCareTime() {
        return careTime;
    }

    public void setCareTime(Date careTime) {
        this.careTime = careTime;
    }

    public MemberMemorial getMemberMemorial() {
        return memberMemorial;
    }

    public void setMemberMemorial(MemberMemorial memberMemorial) {
        this.memberMemorial = memberMemorial;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public CommonUser getCareUser() {
        return careUser;
    }

    public void setCareUser(CommonUser careUser) {
        this.careUser = careUser;
    }
}
