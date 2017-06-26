package io.znz.jsite.core.web;

import io.znz.jsite.base.BaseController;
import io.znz.jsite.core.entity.EmployeeEntity;
import io.znz.jsite.core.enums.UserLoginEnum;
import io.znz.jsite.util.StringUtils;
import io.znz.jsite.util.security.Digests;
import io.znz.jsite.util.security.Encodes;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;

/**
 * 登录controller
 *
 * @author Chaly
 */
@Controller
@Qualifier("loginController")
public class LoginController extends BaseController {

	@Autowired
	protected IDbDao dbDao;

	@Autowired
	protected Dao nutDao;

	public static final int HASH_INTERATIONS = 1024;

	/**
	 * 登录页面.这页面不是实际的登录页,而是按照不同的规则进行跳转以达到多登录页的效果
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(@RequestParam(required = false) String module, HttpServletRequest request) {
		String path = request.getServletContext().getContextPath();
		//获取手动设置的跳转
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		//获取Shiro已存储的跳转
		if (savedRequest != null) {
			module = savedRequest.getRequestUrl();
		}
		module = StringUtils.split(module, "[/?#;]")[(StringUtils.isNotBlank(path) && module.startsWith(path)) ? 1 : 0];
		return "redirect:/" + module + "/login";
	}

	/**
	 * 真正登录的POST请求
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String auth(
			@RequestParam String to,
			@RequestParam(required = false) String login,
			@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,
			@RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password,
			@RequestParam(required = false) Integer logintype,
			@RequestParam(value = FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, required = false) boolean rememberMe,
			@RequestParam(required = false) String captcha, HttpServletRequest request, RedirectAttributesModelMap model) {
		//获取Shiro自动保存的跳转
		/*SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		if (savedRequest != null && StringUtils.isNotBlank(savedRequest.getRequestUrl())) {
			to = savedRequest.getRequestUrl();
		}
		try {
			SecurityUtils.getSubject().login(
					new UsernamePasswordCaptchaToken(username, password, rememberMe, captcha, IPUtil
							.getIpAddress(request)));
		} catch (Exception e) {
			if (e instanceof CaptchaException) {
				model.addFlashAttribute("error", "验证码错误，请重试!");
			} else if (e instanceof UnknownAccountException) {
				model.addFlashAttribute("error", "用户名不存在，请重试!");
			} else if (e instanceof IncorrectCredentialsException) {
				model.addFlashAttribute("error", "帐号或密码错误，请重试!");
			} else if (e instanceof LockedAccountException) {
				model.addFlashAttribute("error", "用户已被禁用!");
			} else {
				model.addFlashAttribute("error", "系统异常，请稍后再试！");
			}
			//回显帐号
			if (StringUtils.isNotEmpty(username)) {
				model.addFlashAttribute("username", username);
			}
			//回显密码
			if (StringUtils.isNotEmpty(password)) {
				model.addFlashAttribute("password", password);
			}
			//回显是否记住
			model.addFlashAttribute("remember", rememberMe);
			//以Base64加密的方式回显消息,拼接在url上
			String error = Encodes.encodeBase64(JSON.toJSONString(model.getFlashAttributes()));
			model.addAttribute("e", error);
			if (StringUtils.isBlank(login)) {
				login = request.getHeader("referer");
			}
			return "redirect:" + login;
		}*/
		EmployeeEntity fetch = dbDao.fetch(EmployeeEntity.class, Cnd.where("telephone", "=", username));
		Integer userType = fetch.getUserType();//得到用户类型
		String telephone = fetch.getTelephone();//得到数据库中用户名
		String pwd = fetch.getPassword();//得到数据库中密码
		String slt = fetch.getSalt();//得到数据库中盐值

		byte[] salt = Encodes.decodeHex(slt);
		byte[] password2 = password.getBytes();//页面传来的密码
		byte[] hashPassword = Digests.sha1(password2, salt, HASH_INTERATIONS);
		String newpass = Encodes.encodeHex(hashPassword);//对页面传来的密码进行加密
		System.out.println(newpass);
		if (!username.equals(telephone)) {
			model.addFlashAttribute("error", "用户名不正确，请重新输入！");
		} else if (!newpass.equals(pwd)) {
			model.addFlashAttribute("error", "密码有误,请重新输入！");
		}
		if (!Util.isEmpty(fetch)) {
			request.getSession().setAttribute("fetch", fetch);
			if (username.equals(telephone) && newpass.equals(pwd)) {//username为页面传来的用户名
				if (UserLoginEnum.PERSONNEL.intKey() == logintype && UserLoginEnum.PERSONNEL.intKey() == userType) {//工作人员登录
					return "redirect:" + to + "?auth=23";
				} else if (UserLoginEnum.TOURIST_IDENTITY.intKey() == logintype
						&& UserLoginEnum.TOURIST_IDENTITY.intKey() == userType) {//游客身份登录
					return "redirect:" + to + "?auth=145";
				} else if (UserLoginEnum.SUPERMAN.intKey() == 3 && UserLoginEnum.SUPERMAN.intKey() == userType) {
					return "redirect:" + to + "?auth=0";

				}
			}
		}
		return "redirect:" + login;
	}

	/**
	 * 登出
	 */
	@RequestMapping(value = "logout")
	public String logout(@RequestParam(defaultValue = "/") String to, SessionStatus status, HttpServletRequest request) {
		request.getSession().removeAttribute("fetch");//清除session
		status.setComplete();
		SecurityUtils.getSubject().logout();
		return "redirect:" + to;
	}
}
