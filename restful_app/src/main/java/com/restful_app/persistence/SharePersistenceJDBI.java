package com.restful_app.persistence;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.springframework.stereotype.Service;

import com.restful_app.model.Share;
import com.zaxxer.hikari.HikariDataSource;

@Service
public class SharePersistenceJDBI {
	ShareDto shareDto;
	public SharePersistenceJDBI(DataSource dataSource) {
		HikariDataSource hikariDataSource = dataSource.getHikariDataSource();
		DBI dbi = new DBI(hikariDataSource);
		shareDto = dbi.onDemand(ShareDto.class);
	}
	
	public void sharePost(int postId,int userId ) {
		shareDto.shareStaus(userId, postId);
	}
	
	
	public static class ShareMapper implements ResultSetMapper<Share>{

		public Share map(int idx, ResultSet rs, StatementContext context) throws SQLException {
			Share share = new Share();
			share.setDateOfShare(rs.getTimestamp("dateOfShare"));
			share.setPostId(rs.getInt("postId"));
			share.setShareId(rs.getInt("shareID"));
			return share;
		}
		
	}
	
	public static interface ShareDto{
		@SqlUpdate ("insert into share (userId,postId) vlaues (:userId,:postId)")
		void shareStaus(@Bind("userId") int userID, @Bind("postId") int postId);
		
		@SqlUpdate("select count(postId) from post where postId=:postId")
		int shareCount(@Bind("postId") int postId);
	}

	
}
