/**
 * EmployeeSqlForm.java
 * io.znz.jsite.visa.forms.employeeform
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package io.znz.jsite.visa.forms.employeeform;

import io.znz.jsite.visa.enums.UserDeleteStatusEnum;
import io.znz.jsite.visa.form.KenDoParamForm;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.common.util.Util;

/**
 * 员工管理sqlForm
 * @author   崔建斌
 * @Date	 2017年6月12日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeSqlForm extends KenDoParamForm {
	//主键
	private long id;
	//公司id
	private long comId;
	//用户姓名
	private String fullName;
	//用户名/手机号码
	private String telephone;
	//密码
	private String password;
	//用户类型
	private Integer userType;
	//联系QQ
	private String qq;
	//座机号码
	private String landline;
	//电子邮箱
	private String email;
	//所属部门
	private String department;
	//用户职位
	private String job;
	//用户是否禁用
	private Integer disableUserStatus;
	//状态
	private Integer status;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//备注
	private String remark;
	//盐值
	private String salt;
	//国家类型
	private Integer countryType;
	//父id
	private long pId;

	@Override
	public Sql sql(SqlManager paramSqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = paramSqlManager.get("employee_list_data");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(pId) && pId != 0) {
			cnd.and("e.pId", "=", id);
		}
		cnd.and("e.comId", "=", comId);
		cnd.and("e.disableUserStatus", "=", UserDeleteStatusEnum.NO.intKey());//在职(未删除)
		cnd.and("d.deptName", "!=", "公司管理部");
		cnd.orderBy("e.createTime", "DESC");
		return cnd;
	}
}
