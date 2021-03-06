package io.znz.jsite.visa.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;

/**
 * 签证统计列表
 * @author   彭辉
 * @Date	 2017年08月22日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NewSysStatisticSqlForm extends KenDoParamForm {

	//检索信息
	private Date start_time;
	private Date end_time;
	private String keywords;
	private String sqs_id;
	private String djs_id;
	private int sqsCount;
	private int djsCount;
	private int allCount;

	@Override
	public Sql sql(SqlManager paramSqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = paramSqlManager.get("system_statistic_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(end_time)) {
			end_time = DateUtil.addDay(end_time, 1);
		}
		if (!Util.isEmpty(start_time) && !Util.isEmpty(end_time)) {
			SqlExpressionGroup e1 = Cnd.exps("vnoj.createtime", ">=", start_time)
					.and("vnoj.createtime", "<=", end_time);
			SqlExpressionGroup e2 = Cnd.exps("vnoj.createtime", ">=", start_time)
					.and("vnoj.createtime", "<=", end_time);
			cnd.and(e1.or(e2));
		} else if (Util.isEmpty(start_time) && !Util.isEmpty(end_time)) {
			SqlExpressionGroup e1 = Cnd.exps("vnoj.createtime", "<=", end_time);
			SqlExpressionGroup e2 = Cnd.exps("vnoj.createtime", "<=", end_time);
			cnd.and(e1.or(e2));
		} else if (!Util.isEmpty(start_time) && Util.isEmpty(end_time)) {
			SqlExpressionGroup e1 = Cnd.exps("vnoj.createtime", ">=", start_time);
			SqlExpressionGroup e2 = Cnd.exps("vnoj.createtime", ">=", start_time);
			cnd.and(e1.or(e2));
		}
		if (!Util.isEmpty(keywords)) {
			SqlExpressionGroup e1 = Cnd.exps("vnoj.ordernumber", "like", "%" + keywords + "%");
			SqlExpressionGroup e2 = Cnd.exps("vnoj.completedNumber", "like", "%" + keywords + "%");
			SqlExpressionGroup e3 = Cnd.exps("mm.chinesefullname", "like", "%" + keywords + "%");
			SqlExpressionGroup e4 = Cnd.exps("eee.fullName", "like", "%" + keywords + "%");
			cnd.and(e1.or(e2).or(e3).or(e4));
		}
		if (!Util.isEmpty(sqs_id) && !sqs_id.equals("-1")) {
			/*cnd.and("c.id", "=", sqs_id);*/
			cnd.and("vnoj.sendComId", "=", sqs_id);
		}
		if (!Util.isEmpty(djs_id) && !djs_id.equals("-1")) {
			if (djs_id.equals("0")) {
				//株式会社金通商事   特殊账号 不存在公司
				SqlExpressionGroup e1 = Cnd.exps("vnoj.comId", "=", djs_id);
				SqlExpressionGroup e2 = Cnd.exps("vnoj.landComId", "=", 1);
				SqlExpressionGroup e3 = Cnd.exps("c.comType", "=", 1);
				cnd.and(e1.or(e2.and(e3)));
			} else {
				/*cnd.and("c.id", "=", djs_id);*/
				cnd.and("vnoj.landComId", "=", djs_id);
			}
		}
		//只展示有关系的单子
		cnd.and("vnoj.sendComId", ">", "0");
		cnd.and("vnoj.landComId", ">", "0");
		cnd.orderBy("vnoj.createTime", "DESC");
		return cnd;
	}

}
