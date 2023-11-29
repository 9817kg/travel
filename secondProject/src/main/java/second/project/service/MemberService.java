package second.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import second.project.entity.Member;
import second.project.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
	private final MemberRepository repository;

	@Autowired
	public MemberService(MemberRepository repository) {
		this.repository = repository;
	}

	public Optional<Member> findOne(String userId) {
		return repository.findByEmail(userId);
	}

	public List<Member> getAllMembers() {
		return repository.findAll();
	}

	public void deleteMemberById(Long id) {
		repository.deleteById(id);
	}

	
}
