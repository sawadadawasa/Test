package com.example.servise;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.domain.Member;
import com.example.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	public void findAll(Model model){
		List<Member> memberList = memberRepository.findAll();
		model.addAttribute("memberList", memberList);
	}
	
	public void findMatchMember(Model model, String code, String group){
		List<Member> memberList = memberRepository.findMatchMember(code, group);
		model.addAttribute("memberList", memberList);
	}
	
	public void getMemberGroupList(Model model){
		List<String> groupList = new ArrayList<>();
		groupList = memberRepository.getMemberGroupList();
		model.addAttribute("groupList", groupList);
	}
	
	public void getMatchCount(Model model, String code, String group) {
		Integer count = memberRepository.getMatchCount(code, group);
		model.addAttribute("count", count);
	}
	

}
