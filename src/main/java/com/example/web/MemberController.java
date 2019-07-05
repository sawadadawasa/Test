package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.servise.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping("/")
	public String index(Model model) {
		
		memberService.getMemberGroupList(model);
		memberService.findAll(model);
	
		return "member";
	}
	
	@RequestMapping("/search")
	public String search(Model model, @RequestParam String code, @RequestParam String group) {
		
		memberService.getMemberGroupList(model);
		memberService.findMatchMember(model, code, group);
		memberService.getMatchCount(model, code, group);
	
		return "member";
		
	}
	
}
