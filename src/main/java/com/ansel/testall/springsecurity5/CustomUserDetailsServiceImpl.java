package com.ansel.testall.springsecurity5;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ansel.testall.mybatis.mapper.RoleMapper;
import com.ansel.testall.mybatis.mapper.UserMapper;
import com.ansel.testall.mybatis.model.User;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserMapper userMapper;
	private final RoleMapper roleMapper;

	public CustomUserDetailsServiceImpl(UserMapper userMapper, RoleMapper roleMapper) {
		this.userMapper = userMapper;
		this.roleMapper = roleMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> userList = userMapper.getUserByUsername(username);
		if (userList.size() > 0) {
			User user = userList.get(0);
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			List<String> roleTypeList = roleMapper.listRoleTypeByUsername(username);
			for (String roleType : roleTypeList) {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + roleType));
			}
			user.setEnabled(true);
			user.setAuthorities(authorities);
			return user;

		}

		throw new UsernameNotFoundException("用户名 '" + username + "'没有找到！");
	}
}
