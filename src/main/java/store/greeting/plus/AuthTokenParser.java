package store.greeting.plus;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.security.Principal;
import java.util.Map;

public class AuthTokenParser {
    public static String[] getParseToken(Principal principal) {
        String[] parsedToken = new String[2];
        // [0] = 이메일
        // [1] = loginType

        if (principal instanceof OAuth2AuthenticationToken) { // OAuth2 사용자 요청이면
            OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) principal;

            // role 확인
            parsedToken[1] = authToken.getAuthorities().iterator().next().getAuthority().toLowerCase();
            //System.out.println("!OAuth2 role-loginType! : "+parsedToken[1]);

            // email 확인
            if (parsedToken[1].equals("kakao")) { // 카카오
                parsedToken[0] = ((Map<String, Object>) authToken.getPrincipal().getAttribute("kakao_account")).get("email").toString();
            } else { // 구글, 네이버
                parsedToken[0] = (String) authToken.getPrincipal().getAttributes().get("email");
            }
            //System.out.println("!OAuth2 email! : "+parsedToken[0]);
        } else { //UsernamePasswordAuthenticationToken 사용자 요청이면
            UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) principal;
            parsedToken[1] = "normal"; // UsernamePasswordAuthenticationToken 들어오면 모두 normal
            //System.out.println("!UsernamePasswordAuth role-loginType! : "+parsedToken[1]);

            parsedToken[0] = (String) authToken.getName();
            //System.out.println("!UsernamePasswordAuth email! : "+parsedToken[0]);
        }

        return parsedToken;
    }

//    public static boolean isAdmin(Principal principal) { // 기존 방식
//
//        if (principal instanceof UsernamePasswordAuthenticationToken authToken) { // 일반 사용자 요청이면
//            Object[] role = authToken.getAuthorities().toArray();
//            String role2 = role[0].toString();
//            if (role2.equals("ROLE_ADMIN")) {// ROLE_ADMIN이면
//                return true;
//            }
//        }
//
//        return false;
//    }

    public static boolean isAdmin(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) principal;
            Object[] role = authToken.getAuthorities().toArray();

            if (role.length > 0 && "ROLE_ADMIN".equals(role[0].toString())) {
                return true;
            }
        }

        return false;
    }
}