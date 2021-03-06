/**
 * Department.java
 * io.znz.jsite.visa.entity.department
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package io.znz.jsite.core.entity.department;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 部门表
 * @author   崔建斌
 * @Date	 2017年7月10日 	 
 */
@Data
@Table("visa_new_department")
public class DepartmentEntity {
	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;
	@Column
	@Comment("公司id")
	private long comId;
	@Column
	@Comment("部门名称")
	private String deptName;
	@Column
	@Comment("创建时间")
	private Date createTime;
	@Column
	@Comment("更新时间")
	private Date updateTime;
	@Column
	@Comment("备注")
	private String remark;
}
