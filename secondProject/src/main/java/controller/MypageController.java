package second.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import second.project.entity.Member;
import second.project.oauth2.UserProfile;
import second.project.repository.QuaryRepository;
import second.project.service.MemberService;

@Transactional
@Controller
@RequestMapping("/my")
public class MypageController {
	@Autowired
	private QuaryRepository query;
	@Autowired
	private MemberService memberService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/mypage")
	public String myPage(Model model, HttpSession session) {
		Object dtoObject = session.getAttribute("dto");
		if (dtoObject instanceof Member) {
			Member dto = (Member) dtoObject;
			model.addAttribute("dto", dto);
			session.setAttribute("dto", dto);

		} else if (dtoObject instanceof UserProfile) {
			UserProfile userProfile = (UserProfile) dtoObject;
			model.addAttribute("dto", userProfile);
			session.setAttribute("dto", userProfile);

		}
		return "mypage";
	}

	@GetMapping("/myInfo")
	public String myInfo(Model model, HttpSession session) {
		Object dtoObject = session.getAttribute("dto");
		if (dtoObject instanceof Member) {
			Member dto = (Member) dtoObject;
			model.addAttribute("dto", dto);
			session.setAttribute("dto", dto);

		} else if (dtoObject instanceof UserProfile) {
			UserProfile userProfile = (UserProfile) dtoObject;
			model.addAttribute("dto", userProfile);
			session.setAttribute("dto", userProfile);

		}
		return "myInfo";
	}

	@GetMapping("/changepw")
	public String changepw(Model model, HttpSession session) {
		Object dtoObject = session.getAttribute("dto");
		if (dtoObject instanceof Member) {
			Member dto = (Member) dtoObject;
			model.addAttribute("dto", dto);

			session.setAttribute("dto", dto);

		} else if (dtoObject instanceof UserProfile) {
			UserProfile userProfile = (UserProfile) dtoObject;
			model.addAttribute("dto", userProfile);
			session.setAttribute("dto", userProfile);

		}
		return "changepw";
	}

	@PostMapping("/changepw")
	public String changePw(@RequestParam("password") String password, @RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		Object dtoObject = session.getAttribute("dto");

		if (dtoObject instanceof Member) {
			Member dto = (Member) session.getAttribute("dto");

			// 현재 비밀번호와 저장된 비밀번호를 비교하여 일치하는지 확인
			if (passwordEncoder.matches(password, dto.getPassword())) {
				// 새 비밀번호와 확인 비밀번호가 일치하는지 확인
				if (newPassword.equals(confirmPassword)) {
					// 새 비밀번호를 암호화하여 저장
					String hashedNewPassword = passwordEncoder.encode(newPassword);

					// QuaryRepository를 통해 데이터베이스에 비밀번호 업데이트
					query.updatePassword(hashedNewPassword, dto.getPassword());

					// 업데이트된 엔터티를 다시 불러옴
					Member updatedMember = query.findById(dto.getId()).orElse(null);

					// 업데이트된 엔터티를 세션에 저장
					session.setAttribute("dto", updatedMember);

					// 비밀번호 변경 성공 시 로그아웃 후 메인 페이지로 리다이렉트
					redirectAttributes.addAttribute("message", "비밀번호변경완료");
					return "redirect:/login";
				} else {
					model.addAttribute("dto", dto);
					// 비밀번호가 일치하지 않는 경우 다시 변경 페이지로 이동
					return "changepw";
				}
			}
		}
		// 비밀번호 변경 실패 시 변경 페이지로 이동
		return "changepw";
	}

	@GetMapping("/withdraw")
	public String withdraw(Model model, HttpSession session) {
		Object dtoObject = session.getAttribute("dto");
		if (dtoObject instanceof Member) {
			Member dto = (Member) dtoObject;
			model.addAttribute("dto", dto);
			session.setAttribute("dto", dto);

		} else if (dtoObject instanceof UserProfile) {
			UserProfile userProfile = (UserProfile) dtoObject;
			model.addAttribute("dto", userProfile);
			session.setAttribute("dto", userProfile);

		}
		return "withdraw";
	}

	@DeleteMapping("/withdraw/{memberId}")
	@ResponseBody
	public String deleteMember(@RequestParam("pw") String pw, @PathVariable Long memberId, Model model,
			HttpSession session) {
		try {
			// 회원을 삭제하는 비즈니스 로직 수행
			Member dto = (Member) session.getAttribute("dto");
			
			// 현재 비밀번호와 저장된 비밀번호를 비교하여 일치하는지 확인
			if (passwordEncoder.matches(pw, dto.getPassword())) {

				memberService.deleteMemberById(memberId);

			}
			return "success";
		} catch (Exception e) {
			System.err.println("삭제 중 예외 발생 : " + e.getMessage());
			return "error";
		}
	}

}
