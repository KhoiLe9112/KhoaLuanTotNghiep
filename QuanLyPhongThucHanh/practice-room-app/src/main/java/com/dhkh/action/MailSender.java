package com.dhkh.action;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.dhkh.dao.ScheduleDAO;
import com.dhkh.model.Schedule;

public class MailSender {
    public static void sendMail(String recipientEmail, String subject, String body) {
        // Cấu hình thông tin tài khoản email
        String senderEmail = "";
        String senderPassword = "";

        // Cấu hình properties cho mail session
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Tạo mail session với thông tin đăng nhập
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

        try {
            // Tạo message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            // Gửi email
            Transport.send(message);

            System.out.println("Email đã được gửi thành công!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void sendCancelationEmail(Date startDate, Date endDate, String startPeriod, String endPeriod, String reason) {
        try {
            ScheduleDAO scheduleDAO = new ScheduleDAO();
            List<Schedule> schedules = scheduleDAO.getDeletedSchedules(startDate, endDate, startPeriod, endPeriod);
            
            // Nhóm các lịch đặt theo email của người dùng
            Map<String, List<Schedule>> scheduleMap = new HashMap<>();
            for (Schedule schedule : schedules) {
                // Cập nhật kiểu dữ liệu ngày
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                java.sql.Date sqlUsingDate = schedule.getUsingDate();
                java.util.Date javaUsingDate = new java.util.Date(sqlUsingDate.getTime());
                String usingDateStr = dateFormat.format(javaUsingDate);
                schedule.setUsingDateStr(usingDateStr);
                
                String email = schedule.getEmail();
                if (!scheduleMap.containsKey(email)) {
                    scheduleMap.put(email, new ArrayList<>());
                }
                scheduleMap.get(email).add(schedule);
            }
            
            // Gửi mail thông báo cho mỗi người dùng trong Map
            for (Map.Entry<String, List<Schedule>> entry : scheduleMap.entrySet()) {
                String recipientEmail = entry.getKey();
                List<Schedule> userSchedules = entry.getValue();

                StringBuilder bodyBuilder = new StringBuilder();
                bodyBuilder.append("Danh sách các ghế bạn đã đặt từ ").append(startPeriod).append(" ngày ").append(startDate)
                		.append(" đến ").append(endPeriod).append(" ngày ").append(endDate)
                		.append(" đã được hủy:\n");
                for (Schedule userSchedule : userSchedules) {
                    bodyBuilder.append("- Ghế: ").append(userSchedule.getSeatName())
                            .append(", buổi: ").append(userSchedule.getPeriod())
                            .append(", ngày: ").append(userSchedule.getUsingDateStr())
                            .append("\n");
                }
                bodyBuilder.append("Lý do: ").append(reason);
                String subject = "Hệ thống LabTracker thông báo: Lịch đặt ghế của bạn đã bị hủy";
                String body = bodyBuilder.toString();
                MailSender.sendMail(recipientEmail, subject, body);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}