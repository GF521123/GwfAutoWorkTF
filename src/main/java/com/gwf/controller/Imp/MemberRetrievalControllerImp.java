package com.gwf.controller.Imp;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.gwf.controller.MemberRetrievalController;
import com.gwf.service.MemberRetrievalService;
/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 */
@Controller
public class MemberRetrievalControllerImp  implements MemberRetrievalController{

	@Autowired
	private MemberRetrievalService memberRetrievalService;
	public Map<String, String> getMemberRetrievalResult() {
		return  memberRetrievalService.getMemberRetrievalResult();
		
	}
}
