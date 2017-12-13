package com.eling.elcms.memorialuser.model;

import com.eling.elcms.basedoc.model.PersonalInfo;
import com.eling.elcms.core.model.BaseModel;
import com.eling.elcms.system.model.CommonUser;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;

/**
 * Created by Liuyp on 2017/9/8.
 */
@Entity
@Indexed
@Table(name = "sm_generaluser")
public class GeneralUser extends BaseModel{


    /**
	 * 
	 */
	private static final long serialVersionUID = -5255294241351433097L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @DocumentId
    private Long pkGeneralUser;

    /**用户*/
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="pkCommonUser")
    @IndexedEmbedded(depth = 1)
    private CommonUser commonUser;

    /**人员信息*/
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "pkPersonalInfo")
    @IndexedEmbedded(depth = 1)
    private PersonalInfo personalInfo;

    @Version
    private Integer version;

    public Long getPkGeneralUser() {
        return pkGeneralUser;
    }

    public void setPkGeneralUser(Long pkGeneralUser) {
        this.pkGeneralUser = pkGeneralUser;
    }

    public CommonUser getCommonUser() {
        return commonUser;
    }

    public void setCommonUser(CommonUser commonUser) {
        this.commonUser = commonUser;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
