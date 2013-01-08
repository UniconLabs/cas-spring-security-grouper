package net.unicon.iamlabs.springsecuritygrouper;

import edu.internet2.middleware.grouperClient.api.GcGetGroups;
import edu.internet2.middleware.grouperClient.ws.beans.WsGetGroupsResult;
import edu.internet2.middleware.grouperClient.ws.beans.WsGroup;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Spring Security's <code>UserDetailsService</code> implementation that fetches groups from Grouper back-end data store for an authenticated principal
 * and exposes them as this User's collection of <code>GrantedAuthority</code> objects, so that Spring Security could do authorization checks for this application.
 *
 * @author Dmitriy Kopylenko
 */
public class GrouperUserDetailsService implements UserDetailsService {

	private static final String EMPTY_PASSWORD = "";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		final GcGetGroups groupsClient = new GcGetGroups().addSubjectId(username);

		//Load groups from Grouper back end.
		for (WsGetGroupsResult groupsResult : groupsClient.execute().getResults()) {
			for (WsGroup group : groupsResult.getWsGroups()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(group.getName()));
			}
		}
		return new User(username, EMPTY_PASSWORD, grantedAuthorities);
	}
}
