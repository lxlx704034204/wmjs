package com.eling.elcms.membermemorial.model.view;

import com.eling.elcms.member.model.view.MemberMessage;

import java.util.Date;

/**
 * 家人档案祭奠视图
 * Created by zhoulin on 2017/8/1.
 */
public class MemMessageView extends MemberMessage{

    private Long pkMemberMemorial;

    private Boolean isMemorial;

    private Date deathDay;

    public Long getPkMemberMemorial() {
        return pkMemberMemorial;
    }

    public void setPkMemberMemorial(Long pkMemberMemorial) {
        this.pkMemberMemorial = pkMemberMemorial;
    }

    public Boolean getMemorial() {
        return isMemorial;
    }

    public void setMemorial(Boolean memorial) {
        isMemorial = memorial;
    }

    public Date getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(Date deathDay) {
        this.deathDay = deathDay;
    }
}
