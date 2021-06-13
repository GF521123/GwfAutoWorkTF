package com.gwf.menu.son;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwf.controller.MemberRetrievalController;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 *
 */
@Component
public class MemberRetrievalMenu {
	private static final Logger log = LoggerFactory.getLogger(MemberRetrievalMenu.class);
	
	@Autowired
	private MemberRetrievalController memberRetrievalController;
	public String MemberRetrievalMenu() {
		Map<String, String> result = memberRetrievalController.getMemberRetrievalResult();
		if(Integer.parseInt(result.get("status"))==0) {
			if(Integer.parseInt(result.get("totalRows"))==0) {
				log.info("【待审会员】检索：无待审会员,不发送邮件通知");
	            return "";
			}else {
	            log.info("【待审会员】检索："+result.get("totalRows")+"");
	            return "<div style='color:red;font-size:20px;'>【待审会员】检索："+result.get("totalRows")+"</div>";
			}
		}else {
            log.info("【待审会员】检索：接口异常，请联系开发工程师");
            return "<div style='color:red;font-size:20px;'>接口异常</div>";

		}
	}
}
