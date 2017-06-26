package io.znz.jsite.visa.entity.japan;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("visa_new_recentlyintojp_jp")
public class NewRecentlyintojpJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("客户管理id")
	private Long customer_jp_id;

	@Column
	@Comment("进美时间")
	private Date intousadate;

	@Column
	@Comment("停留时间")
	private Integer stayday;

	@Column
	@Comment("停留单位")
	private String stayunit;

}