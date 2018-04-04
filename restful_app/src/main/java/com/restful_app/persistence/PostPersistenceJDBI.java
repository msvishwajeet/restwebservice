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

import com.restful_app.model.Post;
import com.zaxxer.hikari.HikariDataSource;
@Service
public class PostPersistenceJDBI {
	
	PostDto postDto;
	public PostPersistenceJDBI(DataSource dataSource) {
		HikariDataSource hikariDataSource = dataSource.getHikariDataSource();
		DBI dbi =  new DBI(hikariDataSource);
		postDto = dbi.onDemand(PostDto.class);
	}
	
	public void insertPost(Post post) {
		postDto.insertPost(post.getContent(), post.getDateOfPost(), post.getUserId());
	}
	public static class PostMapper implements ResultSetMapper<Post>{
		public Post map(int ids, ResultSet rs, StatementContext context) throws SQLException {
			Post post = new Post();
			post.setContent(rs.getString("content"));
			post.setDateOfPost(rs.getTimestamp("dateOfPOST"));
			post.setPostId(rs.getInt("postId"));
			post.setUserId(rs.getInt("userId"));
			return post;
		}
	}
	
	public static interface PostDto{
		@SqlQuery("select * from user where post = :postId")
		@Mapper(PostMapper.class)
		List<Post> getPost(@Bind("userId") int userId);
		
		
		@SqlUpdate("insert into post (content,dateOfPOST,userId) values (:content,:dateOfPOST,:userId)")
		void insertPost(@Bind("content") String content, @Bind("dateOfPOST")  Timestamp dateOfPost, 
			@Bind("userId")int userId);
		
		
	}
	
}
