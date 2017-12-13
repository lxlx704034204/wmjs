package com.eling.elcms.memorialapp.model;

import com.eling.elcms.basedoc.model.EnumSerializer;
import com.eling.elcms.community.model.Organization;
import com.eling.elcms.core.dao.annotation.Between;
import com.eling.elcms.core.dao.annotation.CriteriaEnhancer;
import com.eling.elcms.core.dao.annotation.CriteriaEnhancers;
import com.eling.elcms.core.dao.annotation.In;
import com.eling.elcms.core.dao.hibernate.OrderCriteriaEnhancer;
import com.eling.elcms.core.dao.hibernate.search.EnumBridge;
import com.eling.elcms.core.model.BaseModel;
import com.eling.elcms.privilege.service.impl.CommonPrivilegeCriteriaEnhancer;
import com.eling.elcms.system.model.CommonUser;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.Date;

/**
 * App消息
 * @author xc
 *
 */
@Entity
@Indexed
@Table(name = "web_news")
@CriteriaEnhancers(criteriaEnhancers = {
		@CriteriaEnhancer(impl = OrderCriteriaEnhancer.class),
		@CriteriaEnhancer(impl = CommonPrivilegeCriteriaEnhancer.class, parameter = "organization:organization")})
public class WebNews extends BaseModel{

	private static final long serialVersionUID = 5751830584999545682L;

	@Id
	@In
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pkWebNews;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pkOrganization")
	private Organization organization;
	
	/** 标题 */
	@Column
	@Field
	private String title;
	
	/** 封面图片路径 */
	@Column
	private String coverPicUrl;
	
	/** 预览地址路径 */
	@Column
	private String previewUrl;
	
	/** 内容 */
	@Column
	@Lob
	private String content;
	
	/** 发布日期*/
	@Column
	@Between
	private Date releaseDate;
	
	/** 创建时间*/
	@Column
	@Between
	private Date createDate;
	
	/** 创建人 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pkCreator")
	private CommonUser creator;
	
	/** 是否置顶*/
	@Column
	private Boolean top;
	
	/** 封存*/
	@Column
	private Boolean seal;
	
	/** 点赞数*/
	@Column
	private Integer likeNum;
	
	/** 分享数*/
	@Column
	private Integer shareNum;


	
	/** 发布状态 */
	@Enumerated(EnumType.STRING)
	@Field
	@In
	@FieldBridge(impl = EnumBridge.class)
	private ReleasedStatus releasedStatus;

	/** 分类 */
	@Enumerated(EnumType.STRING)
	@Field
	@In
	@FieldBridge(impl = EnumBridge.class)
	private Classify classify;


	/** 新闻分类 */
	@Enumerated(EnumType.STRING)
	@Field
	@In
	@FieldBridge(impl = EnumBridge.class)
	private ClassifyType classifyType;


	@JsonSerialize(using = EnumSerializer.class)
	public enum ClassifyType {
		Tzgg("通知公告"),
		Mzyw("民政要闻"),
		Bddt("本地动态"),
		CountryPolicy("国家政策法规文件"),
		ProvincePolicy("省政策法规文件"),
		CityPolicy("市政策法规文件");

		private String display;

		ClassifyType(String display) {
			this.display = display;
		}

		public String getDisplay() {
			return display;
		}
	}

	@JsonSerialize(using = EnumSerializer.class)
	public enum Classify {
		News("新闻资讯"),
		Law("政策法规");

		private String display;

		Classify(String display) {
			this.display = display;
		}

		public String getDisplay() {
			return display;
		}
	}

	@Version
	private Integer version;

	public Classify getClassify() {
		return classify;
	}

	public void setClassify(Classify classify) {
		this.classify = classify;
	}

	public Long getPkWebNews() {
		return pkWebNews;
	}

	public WebNews setPkWebNews(Long pkWebNews) {
		this.pkWebNews = pkWebNews;
		return this;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverPicUrl() {
		return coverPicUrl;
	}

	public void setCoverPicUrl(String coverPicUrl) {
		this.coverPicUrl = coverPicUrl;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public CommonUser getCreator() {
		return creator;
	}

	public void setCreator(CommonUser creator) {
		this.creator = creator;
	}

	public Boolean getSeal() {
		return seal;
	}

	public void setSeal(Boolean seal) {
		this.seal = seal;
	}

	public ReleasedStatus getReleasedStatus() {
		return releasedStatus;
	}

	public void setReleasedStatus(ReleasedStatus releasedStatus) {
		this.releasedStatus = releasedStatus;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Boolean getTop() {
		return top;
	}
	
	public void setTop(Boolean top) {
		this.top = top;
	}

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}

	public Integer getShareNum() {
		return shareNum;
	}

	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}

	@JsonSerialize(using = EnumSerializer.class)
	public enum ReleasedStatus {
		UnReleased("未发布"),
		Released("已发布");

		private String display;

		ReleasedStatus(String display) {
			this.display = display;
		}

		public String getDisplay() {
			return display;
		}
	}

	public ClassifyType getClassifyType() {
		return classifyType;
	}

	public WebNews setClassifyType(ClassifyType classifyType) {
		this.classifyType = classifyType;
		return this;
	}
}
