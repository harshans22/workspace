package org.example.message_service.security;

import io.jsonwebtoken.Jwts;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {
    private final String jwtSecret = "your-jwt-signing-key";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        accessor.setUser(() -> "test-user");//comment this line to disable hardcoded user
        // Uncomment the following block to enable JWT authentication
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            String header = accessor.getFirstNativeHeader("Authorization");
//            if (header != null && header.startsWith("Bearer ")) {
//                String jwt = header.substring(7);
//                String username = Jwts.parser().setSigningKey(jwtSecret)
//                    .parseClaimsJws(jwt).getBody().getSubject();
//                if (username != null) {
//                    Authentication auth = new UsernamePasswordAuthenticationToken(username, null, null);
//                    accessor.setUser(auth);
//                }
//            }
//        }
        return message;
    }
}
