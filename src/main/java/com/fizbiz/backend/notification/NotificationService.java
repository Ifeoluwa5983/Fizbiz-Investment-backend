package com.fizbiz.backend.notification;

import com.fizbiz.backend.exception.FizbizException;

public interface NotificationService {

    void sendNotification(String fromAddress, String senderName, String toAddress, String subject, String redirectLink, String content) throws FizbizException;
}
