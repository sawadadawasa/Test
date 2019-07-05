package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Member;

@Repository
@Transactional
public class MemberRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Member> MEMBER_ROW_MAPPER = (rs, i) -> {

		Member member = new Member();
		member.setId(rs.getInt("id"));
		member.setName(rs.getString("name"));
		member.setNameKana(rs.getString("name_kana"));
		member.setMailAddress(rs.getString("mail_address"));
		member.setArea(rs.getString("area"));
		member.setGroup(rs.getString("group"));
		member.setJoinDate(rs.getDate("join_date"));
		return member;

	};

	public List<Member> findAll(){
		String sql = "SELECT member.id, member.name, member.name_kana, member.mail_address,"
				+ " member.area, groups.name AS group, member.join_date FROM"
				+ " member INNER JOIN groups ON member.group_id = groups.id ORDER BY member.id";
		List<Member> memberList = template.query(sql, MEMBER_ROW_MAPPER);
		return memberList;	
	}

	public List<String>getMemberGroupList(){
		String sql = "SELECT DISTINCT name FROM groups ORDER BY name";
		SqlParameterSource param = new MapSqlParameterSource();
		List<String> groupList = template.queryForList(sql, param, String.class);
		return groupList;
	}

	public List<Member> findMatchMember(String code, String group){
		String sql = "";
		SqlParameterSource param = new MapSqlParameterSource();

		if(group.equals("0")) {
			//グループ未選択検索時
			sql = "SELECT member.id, member.name, member.name_kana, member.mail_address,"
					+ " member.area, groups.name AS group, member.join_date"
					+ " FROM member INNER JOIN groups ON member.group_id = groups.id" 
					+ " WHERE member.name_kana like :code ORDER BY member.id";
			param = new MapSqlParameterSource()
					.addValue("code", "%"+code+"%");

		}else if(!group.equals("0")) {
			//グループ選択検索時
			sql = "SELECT member.id, member.name, member.name_kana, member.mail_address,"
					+ " member.area, groups.name AS group, member.join_date "
					+ " FROM member INNER JOIN groups ON member.group_id = groups.id" 
					+ " WHERE member.name_kana like :code AND groups.name = :group ORDER BY member.id";
			param = new MapSqlParameterSource()
					.addValue("code", "%"+code+"%")
					.addValue("group", group);
		}

		List<Member>memberList = template.query(sql, param, MEMBER_ROW_MAPPER);
		return memberList;
	}

	public Integer getMatchCount(String code, String group) {
		String sql = "";
		SqlParameterSource param = new MapSqlParameterSource();
		if(group.equals("0")) {
			//グループ未選択検索時
			sql = "SELECT COUNT (*)"
					+ " FROM member INNER JOIN groups ON member.group_id = groups.id" 
					+ " WHERE member.name_kana like :code";
			param = new MapSqlParameterSource()
					.addValue("code", "%"+code+"%");
		}else if(!group.equals("0")) {
			//グループ選択検索時
			sql = "SELECT COUNT (*)"
					+ " FROM member INNER JOIN groups ON member.group_id = groups.id" 
					+ " WHERE member.name_kana like :code AND groups.name = :group";
			param = new MapSqlParameterSource()
					.addValue("code", "%"+code+"%")
					.addValue("group", group);

		}
		return template.queryForObject(sql, param, Integer.class);

	}
}
