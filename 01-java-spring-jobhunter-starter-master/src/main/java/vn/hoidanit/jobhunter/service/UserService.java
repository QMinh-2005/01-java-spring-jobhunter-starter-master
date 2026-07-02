package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createUser(User user) {
        return this.userRepository.save(user);
    }
    public void delete(long userId) {
        this.userRepository.deleteById(userId);
    }
    public User getInfo(long userId) {
        // Viết hàm như này sẽ tránh được lỗi NullPointerException
        Optional<User> user = this.userRepository.findById(userId);
        if(user.isPresent())
        {
            return user.get();
        }else{
            return null;
        }
    }
    public List<User> getAllUser(){
        return this.userRepository.findAll();
    }
    public boolean isExisUser (long id)
    {
        return this.userRepository.existsById(id);
    }
    public User updateUser(User user)
    {
        User check = this.getInfo(user.getId());
        if(check == null)
        {
            return null;
        }
        check.setName(user.getName());
        check.setEmail(user.getEmail());
        check.setPassword(user.getPassword());
        return this.userRepository.save(check);
    }
    public User handleGetUserByEmail(String email)
    {
        return this.userRepository.findByEmail(email);
    }
}
