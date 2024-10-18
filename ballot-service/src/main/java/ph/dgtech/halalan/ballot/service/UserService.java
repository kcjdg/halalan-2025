package ph.dgtech.halalan.ballot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.dgtech.halalan.ballot.exception.NotFoundException;
import ph.dgtech.halalan.ballot.model.secondary.User;
import ph.dgtech.halalan.ballot.repo.secondary.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User findUserWithAttributes(String id){
       return userRepository.findUserWithAttributes(id).orElseThrow(()->new NotFoundException("User does not exist"));
    }


}
