package com.restful_app.persistence;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restful_app.model.Follow;
import com.zaxxer.hikari.HikariDataSource;

@Service
public class FollowPersistenceJDBI {
	FollowDto followDto;
	
	@Autowired
	public FollowPersistenceJDBI(DataSource dataSource) {
		HikariDataSource hikariDataSource = dataSource.getHikariDataSource();
		DBI dbi = new DBI(hikariDataSource);
		followDto = dbi.onDemand(FollowDto.class);
	}
	public void insertFollowers(Follow follow) {
		followDto.insertFollow(follow.getUserId(), follow.getFriendsId());
	}
	
	public int myTotalFoolowers(int userId) {
		return followDto.iAmFollowedBy(userId);
	}
	
	public static class followMapper implements ResultSetMapper<Follow>{

		public Follow map(int index, ResultSet rs, StatementContext context) throws SQLException {
			Follow follow = new Follow();
			follow.setDateOfFollow(rs.getTimestamp("dateOfFollow"));
			follow.setFollowId(rs.getInt("followId"));
			follow.setFriendsId(rs.getInt("friendsId"));
			follow.setUserId(rs.getInt("userId"));
			return follow;
		}
		
	}
	
	
	public static interface FollowDto{
		@SqlUpdate("insert into follow (userId,freindsId) values (:userId,:friendsId)")
		void insertFollow(@Bind("userId") int userId, @Bind("friendsId") int friendsId);

	@SqlUpdate("Select count(friendsId) from follow where friendsId = :userId")
	int iAmFollowedBy(@Bind("userId") int userId);
	
	@SqlQuery("select * from follow where friendsId = :userId")
	@Mapper(followMapper.class)
	List<Follow> iAmFollowedByList(@Bind("userId") int userId);

	@SqlUpdate("select count(userId) from follow where userId = :userId")
	int iAmFollowing(@Bind("userId") int userId);
	
	
	@SqlQuery("select * from follow where userId = :userId")
	@Mapper(followMapper.class)
	List<Follow> iAmFollowingList(@Bind("userId") int userId);
	
	}
	
	
	
}
