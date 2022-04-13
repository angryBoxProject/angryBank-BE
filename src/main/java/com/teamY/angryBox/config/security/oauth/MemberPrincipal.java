package com.teamY.angryBox.config.security.oauth;

import com.teamY.angryBox.vo.MemberVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberPrincipal implements OAuth2User, UserDetails, OidcUser {
    private final int memberId;
    private final String nickname;
    private final String memberEmail;
    private final String password;
    private final String registerType;
//    private final ProviderType providerType;
//    private final RoleType roleType;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;


    public MemberVO getMemberVO() {
        return new MemberVO(memberId, memberEmail, nickname, registerType);
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return memberEmail;
    }

    @Override
    public String getUsername() {
        return memberEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    public static MemberPrincipal create(MemberVO member) {
        return new MemberPrincipal(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getPassword(),
                member.getRegisterType(),
                /*member.getProviderType(),
                RoleType.USER,
                Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode()))*/
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public static MemberPrincipal create(MemberVO member, Map<String, Object> attributes) {
        MemberPrincipal memberPrincipal = create(member);
        memberPrincipal.setAttributes(attributes);

        return memberPrincipal;
    }
}
