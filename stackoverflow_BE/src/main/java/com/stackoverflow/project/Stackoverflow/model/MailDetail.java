package com.stackoverflow.project.Stackoverflow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailDetail {
    private String recipient;
    private String subject;
    private String msgBody;
}
