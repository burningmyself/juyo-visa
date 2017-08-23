/**
 * SqsJpTotleService.java
 * io.znz.jsite.visa.service.sqsjptotal
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package io.znz.jsite.visa.service.djstotal;

import io.znz.jsite.base.NutzBaseService;
import io.znz.jsite.core.entity.companyjob.CompanyJobEntity;
import io.znz.jsite.core.util.Const;
import io.znz.jsite.visa.entity.japan.NewComeBabyJpDjsEntity;
import io.znz.jsite.visa.entity.japan.NewComeBabyJpEntity;
import io.znz.jsite.visa.forms.djstotal.DjsJpTotalForm;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

/**
 * 日本送签社统计
 * @author   崔建斌
 * @Date	 2017年8月22日 	 
 */
@Service
public class DjsJpTotleService extends NutzBaseService {
	public Object djsjptotalList(DjsJpTotalForm sqlForm, Pager pager, final HttpSession session) {
		//通过session获取公司的id
		CompanyJobEntity company = (CompanyJobEntity) session.getAttribute(Const.USER_COMPANY_KEY);
		long comId = company.getComId();//得到公司的id
		sqlForm.setComId(comId);
		pager = new Pager();
		pager.setPageNumber(sqlForm.getPageNumber());
		pager.setPageSize(sqlForm.getPageSize());
		//List<TotalJpBean> list = Lists.newArrayList();
		return this.listPage(sqlForm, pager);
	}

	//下拉列表
	public Object compSelectfind(int compType, final HttpSession session) {
		//通过session获取公司的id
		CompanyJobEntity company = (CompanyJobEntity) session.getAttribute(Const.USER_COMPANY_KEY);
		long comId = company.getComId();//得到公司的id
		List<NewComeBabyJpEntity> sqsCompList = Lists.newArrayList();
		List<NewComeBabyJpDjsEntity> disCompList = Lists.newArrayList();
		if (compType == 1) {
			sqsCompList = dbDao.query(NewComeBabyJpEntity.class, Cnd.where("comId", "=", comId), null);
			return sqsCompList;
		}
		if (compType == 2) {
			disCompList = dbDao.query(NewComeBabyJpDjsEntity.class, null, null);
			return disCompList;
		}
		return new ArrayList();
	}
}