package com.example.Clocking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }
    
    
    @PostMapping("/Check-In")
    public ResponseEntity<String> checkin(@RequestBody User user){
    	
    	Authentication checking = SecurityContextHolder.getContext().getAuthentication();
    	if(checking.isAuthenticated())
    	{
            String systemipaddress = "";
    		 try
    	        {
    	            URL url_name = new URL("http://bot.whatismyipaddress.com");
    	 
    	            BufferedReader sc =
    	            new BufferedReader(new InputStreamReader(url_name.openStream()));
    	 
    	            // reads system IPAddress
    	            systemipaddress = sc.readLine().trim();
    	        }
    		 catch (Exception e)
    	        {
    	            systemipaddress = "Cannot Execute Properly";
    	        }
            return new ResponseEntity<>("Done Check In at" + systemipaddress, HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<>("Login First", HttpStatus.BAD_REQUEST);
    }
}
