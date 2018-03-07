/*package spring.controll.waffle;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.sertek.framework.spring.dao.SpringMybatisUtil;
import com.sertek.framework.spring.log.EnableLogger;
import spring.form.INDEXForm;
import spring.web.CommonConstant;
import spring.web.UserSession;

*//**
 * WAFFLE SSO Controller
 * @author 1610018
 *//*
@Controller
@RequestMapping("/")
public class WAFFLEINDEXController {
	


	*//**
	 * 取得Single Sign On使用者ID及明細資料
	 * (取不到時，導回原來的登入機制)
	 *  
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 *//*
	@RequestMapping("waffleLogin")
	public ModelAndView waffleLogin(HttpServletRequest request) throws IOException {
		String me = getClass().getName() + ".waffleLogin(HttpServletRequest request)";
		
		String userName = request.getRemoteUser();
		if(userName == null) {
			ModelAndView view = new ModelAndView("INDEX");
			view.addObject("errorMessage", "AD驗證失敗!使用原來登入方式");
			return view;
		}
		return new ModelAndView( new RedirectView("MAINPAGE.htm"));

		logger.info(me + ":usrid=" + userName);
		INDEXForm form = new INDEXForm();
		form.setUsrid(StringUtils.substringAfter(userName, "\\"));
		List<S03Bean> ls = db.selectList("SYS.LoginCheckHavePswd", form);
		if (ls.size() > 0) {
			UserSession user = new UserSession(ls.get(0), request, db);
			user.setSso(true);
			request.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
			return new ModelAndView( new RedirectView("MAINPAGE.htm"));
		}else{
			ModelAndView view = new ModelAndView("INDEX");
			view.addObject("errorMessage", "資料庫無對應使用者資料!");
			return view;
		}		
	}
}
*/