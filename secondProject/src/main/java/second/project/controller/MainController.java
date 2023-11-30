package second.project.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

import second.project.DTO.MemberDTO;
import second.project.entity.Member;
import second.project.oauth2.OAuth2Service;
import second.project.oauth2.UserProfile;
import second.project.repository.MemberRepository;
import second.project.service.MemberService;


@Controller
public class MainController {
	@Autowired
	private OAuth2Service oAuth2Service; // OAuth2Service 주입 추가
	@Autowired
	private MemberRepository memberRepository; // OAuth2Service 주입 추가
	MemberDTO dto = new MemberDTO();

	Member member = new Member();
	MemberService memberService;
	@Autowired
	private PasswordEncoder encoder;
	@GetMapping("/main")
	public String dashboardPage(@AuthenticationPrincipal User user, Model model, HttpSession session) {
		if (user != null) {
			String email = user.getUsername();
			Optional<Member> optionalMember = memberRepository.findByEmail(email);
			 if (optionalMember.isPresent()) {
		            Member dto = optionalMember.get();
		            session.setAttribute("dto", dto);
		            model.addAttribute("dto", dto);
		        }
			
		}else if(user == null) {
			UserProfile userProfile = (UserProfile) session.getAttribute("dto");
			model.addAttribute("dto", userProfile);
			session.setAttribute("dto", userProfile);
		}

		return "main";

	}
	
	@GetMapping("")
	public String getIntro() {
		return "intro";
	}
	
	
	
	
}
