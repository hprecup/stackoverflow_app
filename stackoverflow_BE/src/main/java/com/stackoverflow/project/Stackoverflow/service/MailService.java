package com.stackoverflow.project.Stackoverflow.service;

import com.stackoverflow.project.Stackoverflow.model.MailDetail;

public interface MailService {
    void sendSimpleMail(MailDetail mailDetail);
}
