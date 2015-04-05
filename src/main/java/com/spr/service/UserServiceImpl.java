package com.spr.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spr.exception.UserNotFound;
import com.spr.model.User;
import com.spr.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserRepository userRepository;

	@Override
	@Transactional
	public User create(User user) {
		User createdUser = user;
		return userRepository.save(createdUser);
	}
	
	@Override
	@Transactional
	public User findById(int id) {
		return userRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=UserNotFound.class)
	public User delete(int id) throws UserNotFound {
		User deletedUser = userRepository.findOne(id);
		
		if (deletedUser == null)
			throw new UserNotFound();
		
		userRepository.delete(deletedUser);
		return deletedUser;
	}

	@Override
	@Transactional
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=UserNotFound.class)
	public User update(User user) throws UserNotFound {
		User updatedUser = userRepository.findOne(user.getId());
		
		if (updatedUser == null)
			throw new UserNotFound();

        updatedUser.setName(user.getName());
        updatedUser.setAge(user.getAge());
        updatedUser.setIsAdmin(user.getIsAdmin());
		return updatedUser;
	}

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> findAll(String name, Pageable pageable) {
        return userRepository.findAll(name, pageable);
    }

    @Override
    public Long getCountAllRows(String name) {
        return userRepository.getCountAllRows(name);
    }
}
