/**
 * SqsJpTotalController.java
 * io.znz.jsite.visa.web
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package io.znz.jsite.visa.web;

import io.znz.jsite.base.BaseController;
import io.znz.jsite.visa.forms.sqstotal.SqlJpTotalForm;
import io.znz.jsite.visa.service.sqsjptotal.SqsJpTotleService;

import javax.servlet.http.HttpSession;

import org.nutz.dao.pager.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 日本送签社统计
 * @author   崔建斌
 * @Date	 2017年8月22日 	 
 */
@Controller
@RequestMapping("visa/sqsjptotal")
public class SqsJpTotalController extends BaseController {

	@Autowired
	private SqsJpTotleService sqsJpTotleService;//日本送签社统计

	/**
	 * 送签社统计列表查询
	 */
	@RequestMapping(value = "sqsjptotalList")
	@ResponseBody
	public Object sqsjptotalList(@RequestBody SqlJpTotalForm sqlForm, Pager pager, final HttpSession session) {
		return sqsJpTotleService.sqsjptotalList(sqlForm, pager, session);
	}

	//下拉框初始化
	@RequestMapping(value = "compSelectfind")
	@ResponseBody
	public Object compSelectfind(int compType, final HttpSession session) {
		return sqsJpTotleService.compSelectfind(compType, session);
	}
}
