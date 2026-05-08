package com.gooroomees.neulbomgil_backend.domain.auth.service;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.AuthToken;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.auth.repository.AuthTokenRepository;
import com.gooroomees.neulbomgil_backend.domain.auth.repository.UserAuthRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final AuthTokenRepository authTokenRepository;
    private final UserAuthService userAuthService;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@example.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public String sendAuthLink(Long userId) {
        UserAuth user = userAuthService.findById(userId);

        String authToken = UUID.randomUUID().toString();

        authTokenRepository.save(new AuthToken(userId, authToken, LocalDateTime.now().plusMinutes(5L)));

        String authLink = "http://localhost:8080/api/auth/verify?token=" + authToken;

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            // true 매개변수는 multipart 지원 및 HTML 텍스트를 의미합니다.
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("회원가입 이메일 인증을 완료해주세요.");

            // HTML 내용 작성 (a 태그를 버튼처럼 스타일링)
            String htmlContent = "<div style='text-align: center; padding: 20px;'>"
                    + "<h2>회원가입 인증 메일입니다.</h2>"
                    + "<p>아래 버튼을 클릭하여 인증을 완료해주세요.</p>"
                    + "<a href='" + authLink + "' style='"
                    + "display: inline-block; padding: 10px 20px; "
                    + "background-color: #007bff; color: #ffffff; "
                    + "text-decoration: none; border-radius: 5px;'>"
                    + "이메일 인증하기</a>"
                    + "</div>";

            helper.setText(htmlContent, true); // true는 HTML 형식임을 지정
            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("메일 발송에 실패했습니다.", e);
        }

        return authToken;
    }
}
