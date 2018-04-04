package com.restful_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restful_app.model.Follow;
import com.restful_app.persistence.FollowPersistenceJDBI;

@Service
public class FollowController {
@Autowired
FollowPersistenceJDBI followPersistenceJDBI;
 

public void satrtFollow(int userId,int friendsId) {
	Follow follow = new Follow();
	follow.setFriendsId(friendsId);
	follow.setUserId(userId);
}


public void getTotalFollowers(int userId) {
	int count = followPersistenceJDBI.myTotalFoolowers(userId);
	System.out.println("Total Number of Followers is :"+ count);
}
}
