package com.sim.mybatis;

import org.apache.ibatis.annotations.Param;

import com.sim.user.User;

public interface UserMapper {

	public int insert(User user);

	public User login(@Param("id") String id, @Param("passwd") String passwd);

	public int modify(User user);

	public void changePasswd(User user);

	public void byeMember(User user);
}
