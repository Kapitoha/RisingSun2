package com.springapp.mvc.repository;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.mvc.domain.AccessRight;
import com.springapp.mvc.domain.Status;
import com.springapp.mvc.domain.UsersEntity;

/**
 * @author kapitoha
 *
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userManager;

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String login)
	    throws UsernameNotFoundException
    {
	UsersEntity user = userManager.getUser(login);
	if (user == null)
	    throw new UsernameNotFoundException("There is no user which has this login");
	else
	{
	    boolean isActive = user.getStatus().equals(Status.ACTIVE);
	    Collection<GrantedAuthority> rights = new ArrayList<GrantedAuthority>();
	    for (AccessRight right : user.getAccessList())
		rights.add(new SimpleGrantedAuthority(right.getDescription()));
	    User securUser = new User(user.getLogin(), user.getPassword(),
		    isActive, isActive, isActive, isActive, rights);
	    return securUser;
	}
    }

}
