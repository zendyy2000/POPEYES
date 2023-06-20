package com.lct.service;


import javax.mail.MessagingException;

import com.lct.beans.MailInformation;


public interface MailService {
	void send(MailInformation mail) throws MessagingException;
	void send(String to, String subject, String body) throws MessagingException;
	void queue(MailInformation mail);
	void queue(String to, String subject, String body);
}