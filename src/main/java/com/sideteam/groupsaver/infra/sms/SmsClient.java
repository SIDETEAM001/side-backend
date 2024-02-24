package com.sideteam.groupsaver.infra.sms;

import com.sideteam.groupsaver.infra.sms.dto.SmsSendResult;

public interface SmsClient {
    SmsSendResult sendTextMessage(String phoneNumber, String message);
}
