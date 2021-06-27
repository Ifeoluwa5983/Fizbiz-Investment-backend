package com.fizbiz.backend.security;

import com.fizbiz.backend.models.ApplicationUser;
import com.fizbiz.backend.models.Role;
import com.fizbiz.backend.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepository.findByEmailAddress(s);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        return new User(user.getEmailAddress(), user.getPassword(), getAuthorities(user.getRole()));

    }

    private Collection<GrantedAuthority> getGrantedAuthorities(Role role) {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(role)));

        return grantedAuthorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Role role){
            return getGrantedAuthorities(role);
        }
}
