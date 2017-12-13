package com.eling.elcms.membermemorial.service;

import com.eling.elcms.basedoc.model.PersonalInfo;
import com.eling.elcms.core.service.IGenericManager;
import com.eling.elcms.member.model.Member;
import com.eling.elcms.membermemorial.model.MemberMemorial;
import com.eling.elcms.membermemorial.model.view.MemMessageView;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;

public interface IMemberMemorialService extends IGenericManager<MemberMemorial, Long> {
    List<MemMessageView> queryMember(Member member, Long pkOrg);

    MemberMemorial saveMemorial(CommonsMultipartFile file, MemberMemorial cond, PersonalInfo personalInfo) throws IOException;

    MemberMemorial saveMemberMemorial(CommonsMultipartFile file, MemberMemorial cond,PersonalInfo personalInfo) throws IOException;
}
