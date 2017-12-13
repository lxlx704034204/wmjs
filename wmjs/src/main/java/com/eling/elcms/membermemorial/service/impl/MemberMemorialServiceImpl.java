package com.eling.elcms.membermemorial.service.impl;

import com.eling.elcms.basedoc.model.ContactAddress;
import com.eling.elcms.basedoc.model.PersonalInfo;
import com.eling.elcms.basedoc.service.IContactAddressManager;
import com.eling.elcms.basedoc.service.IPersonalInfoManager;
import com.eling.elcms.community.model.Organization;
import com.eling.elcms.core.AppContext;
import com.eling.elcms.core.exception.AppException;
import com.eling.elcms.core.service.impl.GenericManagerImpl;
import com.eling.elcms.core.util.PropertyUtils;
import com.eling.elcms.fm.service.IFileService;
import com.eling.elcms.member.dao.IContactsDao;
import com.eling.elcms.member.dao.IMemberDao;
import com.eling.elcms.member.dao.IMemberPointDao;
import com.eling.elcms.member.model.Contacts;
import com.eling.elcms.member.model.Member;
import com.eling.elcms.member.model.MemberContacts;
import com.eling.elcms.member.model.MemberPoint;
import com.eling.elcms.member.service.IMemberContactsManager;
import com.eling.elcms.member.service.IMemberManager;
import com.eling.elcms.membermemorial.dao.IMemberMemorialDao;
import com.eling.elcms.membermemorial.model.MemberMemorial;
import com.eling.elcms.membermemorial.model.MemorialDetail;
import com.eling.elcms.membermemorial.model.view.MemMessageView;
import com.eling.elcms.membermemorial.service.IMemberMemorialService;
import com.eling.elcms.membermemorial.service.IMemorialDetailService;
import com.eling.elcms.util.sensitiveword.SensitivewordFilter;
import com.eling.elcms.ordermanager.service.IServicePointManager;
import com.eling.elcms.system.dao.IAppUserDao;
import com.eling.elcms.system.model.AppUser;
import com.eling.elcms.system.model.CommonUser;
import com.eling.elcms.system.service.ICommonUserManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberMemorialServiceImpl extends GenericManagerImpl<MemberMemorial, Long> implements IMemberMemorialService {

    IMemberMemorialDao memberMemorialDao;
    @Autowired
    private IMemberManager memberManager;
    @Autowired
    private IMemberDao memberDao;
    @Autowired
    private IContactsDao contactsDao;
    @Autowired
    private IPersonalInfoManager personalInfoManager;
    @Autowired
    private IMemberContactsManager memberContactsManager;
    @Autowired
    private IAppUserDao appUserDao;
    @Autowired
    private IMemberPointDao memberPointDao;
    @Autowired
    private IContactAddressManager contactAddressDao;
    @Autowired
    private IServicePointManager servicePointManager;
    @Autowired
    private ICommonUserManager commonUserManager;
    @Autowired
    private IFileService fileService;
    @Autowired
    private IMemorialDetailService memorialDetailService;

    @Autowired
    public void setDao(IMemberMemorialDao dao) {
        this.dao = dao;
        this.memberMemorialDao = dao;
    }

    @Override
    public List<MemMessageView> queryMember(Member mem, Long pkOrg) {
        CommonUser curUser = (CommonUser) AppContext.curUser();
        List<MemMessageView> list = new ArrayList<>();
        //查询手机号与当前用户一致的长者
        List<Member> members = new ArrayList<>();
        Member member = new Member();
        PropertyUtils.setProperty(member, "validatePrivilege", false);
        PropertyUtils.setProperty(member, "personalInfo.mobilePhone", curUser.getPhone1());
        if (pkOrg != null) {
            PropertyUtils.setProperty(member, "organization.pkOrganization", pkOrg);
        }
        members = memberManager.query(member);
        //查询联系人手机号与当前用户一致的长者
        Contacts ca = new Contacts();
        PropertyUtils.setProperty(ca, "personalInfo.mobilePhone", curUser.getPhone1());
        List<Contacts> contacts = contactsDao.query(ca);

        MemberContacts mc = new MemberContacts();
        PropertyUtils.setProperty(mc, "contactsIn", contacts);
        if (pkOrg != null) {
            PropertyUtils.setProperty(mem, "organization.pkOrganization", pkOrg);
        }
        mc.setMember(mem);
        List<MemberContacts> mcs = memberContactsManager.query(mc);//app用户绑定长者
        for (MemberContacts memContact : mcs) {
            getMemberMessage(list, memContact);
        }
        List<Long> pkMembers = list.stream().map(a -> a.getPkMember()).collect(Collectors.toList());

        //查询App用户创建的长者
        Member creatMem = new Member();
        creatMem.setCreater(curUser);
        PropertyUtils.setProperty(member, "validatePrivilege", false);
        if (pkOrg != null) {
            PropertyUtils.setProperty(member, "organization.pkOrganization", pkOrg);
        }
        members.addAll(memberManager.query(creatMem));

        for (int i = 0; i < members.size(); i++) {
            if (!pkMembers.contains(members.get(i).getPkMember())) {
                Contacts cs = new Contacts();
                cs.setSosContact(true);
                AppUser au = new AppUser();
                PropertyUtils.setProperty(au, "commonUser.pkUser", curUser.getPkUser());
                cs.setPersonalInfo(appUserDao.query(au).get(0).getPersonalInfo());
                MemberContacts memContact = new MemberContacts();
                memContact.setMember(members.get(i));
                memContact.setContacts(contacts.size() > 0 ? contacts.get(0) : cs);
                if (memberContactsManager.query(memContact).size() < 1) {
                    memberContactsManager.save(memContact);
                }
                getMemberMessage(list, memContact);
            }
        }
        // 查询该长者是否在祭奠档案中
        if (CollectionUtils.isNotEmpty(list)) {
            List<Long> pkMemberList = list.stream().map(a -> a.getPkMember()).collect(Collectors.toList());
            MemberMemorial memorialCond = new MemberMemorial();
            PropertyUtils.setProperty(memorialCond, "member.pkMemberIn", pkMemberList);
            List<MemberMemorial> memMemorialList = query(memorialCond);
            if (CollectionUtils.isNotEmpty(memMemorialList)) {
                Iterator<MemMessageView> iterator = list.iterator();
                while (iterator.hasNext()){
                    MemMessageView mmView = iterator.next();
                    for (MemberMemorial memberMemorial : memMemorialList) {
                        if (mmView.getPkMember().equals(memberMemorial.getMember().getPkMember())) {
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public MemberMemorial saveMemorial(CommonsMultipartFile file, MemberMemorial cond, PersonalInfo personalInfo) throws IOException {
        // 新增附默认值
        if (null == cond.getPkMemberMemorial()) {
            cond.setCliffordCounts(0);
            cond.setFlowerCounts(0);
            cond.setWorshipCounts(0);
            cond.setCreateDate(new Date());
            cond.setCreator((CommonUser)AppContext.curUser());
        }
        // 更新死亡日期
        if (null != personalInfo){
            Member member = memberManager.get(cond.getMember().getPkMember());
            PersonalInfo memPI = personalInfoManager.get(member.getPersonalInfo().getPkPersonalInfo());
            // 更新家人信息
            memPI.setName(personalInfo.getName());
            memPI.setSex(personalInfo.getSex());
            memPI.setBirthday(personalInfo.getBirthday());
            memPI.setDeathday(personalInfo.getDeathday());
            memPI = personalInfoManager.save(memPI);
            member.setPersonalInfo(memPI);
            member.setStatus(Member.Status.Late);
            member = memberDao.save(member);
            cond.setMember(member);
        }
        if (null != file) {
            try {
                String storage = file.getFileItem().getName();
                String attachName = fileService.save(file.getInputStream(), "/membermemorial/photo/" + cond.getPkMemberMemorial() + "_" + storage);
                cond.setAttachName("api/attachment" + attachName);
            } catch (AppException e) {
                throw new AppException("上传失败!");
            }
        }

        SensitivewordFilter filter = new SensitivewordFilter();
        cond.setIntroduction(filter.replaceSensitiveWord(cond.getIntroduction(),1,"*"));
        cond.setIntroduction(filter.replaceSensitiveWord(cond.getMemorialName(),1,"*"));
        if(cond.getPersonalInfo() != null && cond.getPersonalInfo().getName()!=null){
            cond.getPersonalInfo().setName(filter.replaceSensitiveWord(cond.getPersonalInfo().getName(),1,"*"));
        }

        personalInfo.setName(filter.replaceSensitiveWord(personalInfo.getName(),1,"*"));


        return save(cond);
    }

    @Override
    public MemberMemorial saveMemberMemorial(CommonsMultipartFile file, MemberMemorial cond,PersonalInfo personalInfo) throws IOException {
        // 新增附默认值
        if (null == cond.getPkMemberMemorial()) {
            cond.setCliffordCounts(0);
            cond.setFlowerCounts(0);
            cond.setWorshipCounts(0);
            cond.setCreateDate(new Date());
            //设置创建人（就是用户自己）
            cond.setCreator(commonUserManager.get(cond.getCreator().getPkUser()));
        }
        if (null != file) {
            try {
                String storage = file.getFileItem().getName();
                String attachName = fileService.save(file.getInputStream(), "/membermemorial/photo/" + cond.getPkMemberMemorial() + "_" + storage);
                cond.setAttachName("api/attachment" + attachName);
            } catch (AppException e) {
                throw new AppException("上传失败!");
            }
        }
        //设置被祭奠人
        PersonalInfo person = new PersonalInfo();
        if(null != personalInfo.getPkPersonalInfo()){
            person = personalInfoManager.get(personalInfo.getPkPersonalInfo());
        }
        person.setName(personalInfo.getName());
        person.setSex(personalInfo.getSex());
        person.setBirthday(personalInfo.getBirthday());
        person = personalInfoManager.save(person);
        cond.setPersonalInfo(person);


        SensitivewordFilter filter = new SensitivewordFilter();
        cond.setIntroduction(filter.replaceSensitiveWord(cond.getIntroduction(),1,"*"));
        cond.setMemorialName(filter.replaceSensitiveWord(cond.getMemorialName(),1,"*"));
        if(cond.getPersonalInfo() != null && cond.getPersonalInfo().getName()!=null){
            cond.getPersonalInfo().setName(filter.replaceSensitiveWord(cond.getPersonalInfo().getName(),1,"*"));
        }

        return save(cond);
    }

    @Override
    public void remove(Long pkMemberMemorial){
        MemorialDetail memorialDetail = new MemorialDetail();
        PropertyUtils.setProperty(memorialDetail, "memberMemorial.pkMemberMemorial", pkMemberMemorial);
        List<MemorialDetail> detailList = memorialDetailService.query(memorialDetail);
        // 删除子表
        if (CollectionUtils.isNotEmpty(detailList)){
            memorialDetailService.remove(detailList);
        }
        // 删除主表
        super.remove(pkMemberMemorial);
    }

    public void getMemberMessage(List<MemMessageView> list, MemberContacts memContact) {
        Member m = memContact.getMember();
        MemMessageView mm = new MemMessageView();
        mm.setCardNumber(m.getCard() != null ? m.getCard().getCardNumber() : "");
        mm.setAge(commonUserManager.getAge(m.getPersonalInfo().getBirthday()));//年龄
        mm.setSex(m.getPersonalInfo().getSex().getDisplay());//性别
        mm.setBirthday(m.getPersonalInfo().getBirthdayString());
        mm.setDeathDay(m.getPersonalInfo().getDeathday());
        PropertyUtils.copyProperties(mm, m);
        mm.setName(m.getPersonalInfo().getName());
        ContactAddress cas = new ContactAddress();
        cas.setDeleteFlag(false);
        cas.setAddressStatus(ContactAddress.AddressStatus.DefaultAddress);
        cas.setPersonalInfo(m.getPersonalInfo());
        List<ContactAddress> addressList = contactAddressDao.query(cas);
        for (Iterator<ContactAddress> it = addressList.iterator(); it.hasNext(); ) {
            ContactAddress contact = it.next();
            if (contact.getAddressStatus() == ContactAddress.AddressStatus.DefaultAddress &&
                    !contact.getDeleteFlag()) {
                mm.setCode(contact.getAddress().getCode());
                mm.setAddress(contact.getAddress().getFullName());
                mm.setPkAddress(contact.getPkContactAddress());
                String detailAddress = contact.getCommunityData() != null ? contact.getCommunityData().getName() : "";
                if (StringUtils.isBlank(contact.getDetailAddress())) {
                    detailAddress += (StringUtils.isNotBlank(contact.getBuildingNumber()) ? contact.getBuildingNumber() : "") +
                            (StringUtils.isNotBlank(contact.getUnitNumber()) ? contact.getUnitNumber() : "") +
                            (StringUtils.isNotBlank(contact.getDoorNumber()) ? contact.getDoorNumber() : "");
                } else {
                    detailAddress += contact.getDetailAddress();
                }
                mm.setDetailAddress(detailAddress);
                break;
            }
        }

        MemberPoint mp = new MemberPoint();
        mp.setPkMember(m.getPkMember());
        List<MemberPoint> mpList = memberPointDao.query(mp);
        if (mpList.size() > 0) {
            Long pkServicePoint = mpList.get(0).getPkServicePoint();
            mm.setPkServicePoint(pkServicePoint);
            String name = servicePointManager.get(pkServicePoint).getName();
            mm.setServicePointName(name);
        }
        if (m.getOrganization() != null) {
            mm.setPkOrg(m.getOrganization().getPkOrganization());
            mm.setOrgName(m.getOrganization().getName());
        } else {
            Organization org = ((CommonUser) AppContext.curUser()).getOrganization();
            if (org != null && org.getPkOrganization() != null) {
                m.setOrganization(org);
            }
        }

        mm.setIdNumber(m.getPersonalInfo().getIdNumber());
        mm.setPhone(m.getPersonalInfo().getMobilePhone());
        mm.setPkPersonalInfo(m.getPersonalInfo().getPkPersonalInfo());
        mm.setMemberVersion(m.getVersion());
        mm.setPersonalVersion(m.getPersonalInfo().getVersion());
        mm.setNickName(memContact.getNickName());
        list.add(mm);
    }
}
