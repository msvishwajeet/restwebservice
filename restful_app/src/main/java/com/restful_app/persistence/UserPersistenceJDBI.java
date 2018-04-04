package com.restful_app.persistence;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.springframework.stereotype.Service;

import com.restful_app.model.User;
import com.zaxxer.hikari.HikariDataSource;
@Service
public class UserPersistenceJDBI {
	UserDto userDto;
	public UserPersistenceJDBI(DataSource dataSource) {
		HikariDataSource hikariDataSource = dataSource.getHikariDataSource();
		DBI dbi =  new DBI(hikariDataSource);
		userDto = dbi.onDemand(UserDto.class);
	}
	
	public User getuser(int userId) {
		return userDto.getUser(userId);//.get(0);
	}
	public void insertUser(User user) {
	userDto.insertUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),user.getMobileNumber());
	}
	public int emailExistence(String email) {
		return userDto.emailVerifier(email);
	}
	
	public static class UserMapper implements ResultSetMapper<User>{
		public User map(int ids, ResultSet rs, StatementContext context) throws SQLException {
			User user = new User();
			user.setEmail(rs.getString("email"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setPassword(rs.getString("password"));
			user.setMobileNumber(rs.getString("mobileNumber"));
			user.setTimeStamp(rs.getTimestamp("dateOfJoin"));
			return user;
		}
	}
	
	public static interface UserDto{
		@SqlQuery("select * from user where userId = :userId")
		@Mapper(UserMapper.class)
		User getUser(@Bind("userId") int userId);
		
		@SqlUpdate("insert into user (firstName,lastName,email,password,mobileNumber) values"
				+ "(:firstName,:lastName,:email,:password,:mobileNumber)")
		void insertUser(@Bind("firstName") String firstName, @Bind("lastName")  String lastName, 
			@Bind("email")String email,@Bind("password") String password,@Bind("mobileNumber") String mobileNumber);
				//,@Bind("dateOfJoin") Timestamp dateOfJoin);
		
		
		@SqlQuery("select count(email) from user where email= :email")
		int emailVerifier(@Bind("email") String email);
	}
	

}
