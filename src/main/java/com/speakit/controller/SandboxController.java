package com.speakit.controller;

import com.speakit.service.email.EmailSender;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/sandbox/")
public class SandboxController {
	@Resource
	private EmailSender emailSender;

	@GetMapping(path = "email")
	public void sendEmail() throws Exception {
//		https://www.google.com/settings/security/lesssecureapps
		emailSender.send("dejiskra@gmail.com", "Test Subject", "This is the email body");
	}
}
