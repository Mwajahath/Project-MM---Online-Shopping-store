package com.massmutual.demo.config;

import com.massmutual.demo.entity.Address;
import com.massmutual.demo.entity.User;
import com.massmutual.demo.enums.UserRole;
import com.massmutual.demo.register.RegisterRequest;
import com.massmutual.demo.register.RegisterService;
import com.massmutual.demo.service.AddressService;
import com.massmutual.demo.service.RoleService;
import com.massmutual.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppInitializationService implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private RoleService roleService;


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RegisterService registerService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        if(shouldInitialize()){

        initialize();}
    }

    public void initialize(){
        roleService.saveRoleFor(UserRole.USER);
        roleService.saveRoleFor(UserRole.ADMIN);

        addDefaultUserData();
    }

    private void addDefaultUserData() {

        Address address = new Address("121","MM Online Shopping Complex","Hyderabad","T.S","India","500000");

        RegisterRequest request = new RegisterRequest();
        request.setUserName("Admin");
        request.setPassword("password");
        request.setEmail("admin@gmail.com");
        request.setPhoneNumber("9876543210");
        request.setName("Admin");
        request.setAddress(address);
        userService.addAdmin(request);

        Address address1 = new Address("281","BSR building","Hyderabad","T.S","India","790293");

        RegisterRequest request1 = new RegisterRequest();
        request1.setUserName("User");
        request1.setPhoneNumber("9831283912");
        request1.setName("User");
        request1.setPassword("password");
        request1.setEmail("user@gmail.com");
        request1.setAddress(address1);
        registerService.addUser(request1);

    }

    public boolean shouldInitialize() {
        return roleService.findAll().size() <=0;
    }

}
