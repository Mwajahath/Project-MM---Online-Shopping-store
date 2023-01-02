package com.massmutual.demo.service;


import com.massmutual.demo.entity.Role;
import com.massmutual.demo.entity.User;
import com.massmutual.demo.enums.UserRole;
import com.massmutual.demo.exceptions.AppException;
import com.massmutual.demo.exceptions.NoRecordFoundException;
import com.massmutual.demo.register.RegisterRequest;
import com.massmutual.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Validated
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Cacheable("user")
    public User findByUserName(String userName) {

        return userRepository.findByUserName(userName);
    }

    public Optional<User> findUserById(long Id){
        return userRepository.findById(Id);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User findByPhone(String phone){
        return userRepository.findByPhoneNumber(phone);
    }

    public User saveInDatabase(User newUser){
        try{
            return userRepository.save(newUser);
        }catch (DataIntegrityViolationException e){
            throw new AppException("User with same data Already exists, Email/Phone should be unique");
        }
    }

    public void validateUserWithSameDataExists(RegisterRequest user) {

        if(null != userRepository.findByUserName(user.getUserName()));
            throw new AppException("Username already exists " + user.getUserName());
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public User addAdmin(RegisterRequest user) {

         User user1 = new User();
         user1.setUserName(user.getUserName());
         user1.setName(user.getName());
         user1.setPassword(toEncrypted(user.getPassword()));
         user1.setPhoneNumber(user.getPhoneNumber());
         user1.setCreated(LocalDateTime.now());
         user1.setEmail(user.getEmail());
         user1.setRoles(getRoleFor(UserRole.ADMIN));
         user1.setAddress(user.getAddress());
         return userRepository.save(user1);
    }

    public Set<Role> getRoleFor(UserRole userRole) {
        return getRolesForUser(roleService.findByRole(userRole));
    }


    private Set<Role> getRolesForUser(Role role) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }


    public String toEncrypted(String password) {

        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public List<User> fetchCustomerList() {
        return userRepository.findAll();
    }

    @Override
    public User fetchCustomerById(Long userId) {

        try{
            User user = userRepository.findByUser_id(userId);
            if (!user.equals(null))
                return user;
        } catch (NullPointerException e){
            throw new AppException("No record found with id: "+userId);

        }
        return null;
    }

    @Override
    public void deleteCustomerById(Long userId) {

        User customer= userRepository.findById(userId).get();
        customer.setStatus("Inactive");
        userRepository.save(customer);

    }

    @Override
    public User updateCustomer(Long customerID, User customer) {

            User cust = userRepository.findById(customerID).get();
            if (cust.getStatus().equalsIgnoreCase("active")) {
                if (Objects.nonNull(customer.getName()) && !"".equalsIgnoreCase(customer.getName())) {
                    cust.setName(customer.getName());

                }
                if (Objects.nonNull(customer.getPhoneNumber()) && !"".equalsIgnoreCase(customer.getPhoneNumber())) {
                    cust.setPhoneNumber(customer.getPhoneNumber());

                }
                if (Objects.nonNull(customer.getEmail()) && !"".equalsIgnoreCase(customer.getEmail())) {
                    cust.setEmail(customer.getEmail());

                }
            } else {
                System.out.println("Since the customer is inactive, Customer details will not get updated");
            }
            return userRepository.save(cust);

    }

    @Override
    public User fetchCustomerByName(String name) {
        try{
             User user =userRepository.findByName(name);
             if(!user.equals(null))
                 return user;
        }catch (NullPointerException e){
            throw new NoRecordFoundException("No record found with name: "+name);
        }
        return null;

    }


}
