package com.dhkh.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.dhkh.dao.RoomDAO;
import com.dhkh.dao.ScheduleDAO;
import com.dhkh.dao.UserDAO;
import com.dhkh.model.Schedule;
import com.dhkh.model.User;
import com.dhkh.util.NumberUtil;
import com.dhkh.util.StringUtil;
import com.google.gson.Gson;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class ScheduleAction extends BaseAction implements ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;
	private List<Schedule> schedules;
	private int scheduleId;
	private List<User> users;
	private String qrCodeBase64;
	private String studentCode;
	
	private String msg;
	private boolean stt;
	private String data;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public String execute() {
		try {
			String usingDateStr = request.getParameter("usingDateStr");
		    String period = request.getParameter("period");
		    
		    if (!StringUtil.isNullOrEmpty(usingDateStr) && !StringUtil.isNullOrEmpty(period)) {
		    	java.util.Date javaUsingDate = new SimpleDateFormat("dd/MM/yyyy").parse(usingDateStr);
		        java.sql.Date sqlUsingdate = new java.sql.Date(javaUsingDate.getTime());
		        
		        ScheduleDAO scheduleDAO = new ScheduleDAO();
				schedules = scheduleDAO.getAllSchedules(sqlUsingdate, period);
				
				Gson gson = new Gson();
				String jsonSchedules = gson.toJson(schedules);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonSchedules);
				
				this.setData(jsonSchedules.toString());
		        
		        RoomDAO roomDAO = new RoomDAO();
		        if (roomDAO.isClosedSchedule(sqlUsingdate, period)) {
		        	this.setStt(false);
		        	this.setMsg("Ngày đã chọn là ngày đóng cửa. Vui lòng chọn ngày khác.");
		        	
		        	return SUCCESS;
		        }
				
				this.setStt(true);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			this.setStt(false);
		}
		return SUCCESS;
	}
	
	public String getUserAccount() {
	    try {
	        UserDAO userDAO = new UserDAO();
	        setUsers(userDAO.getAllUserAccount());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return SUCCESS;
	}
	
	public String ShowQR() {
		try {
			int scheduleId = this.getScheduleId();
			
			if (!NumberUtil.isNullOrEmpty(scheduleId)) {
				// Generate QR code
				String qrCodeData = "https://fowl-giving-mantis.ngrok-free.app/dhkh/confirmStudent?scheduleId=" + scheduleId;
				int width = 200;
				int height = 200;
				ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.H;
				
				byte[] qrCodeBytes = QRCodeGenerator.generateQRCode(qrCodeData, width, height, errorCorrectionLevel);
				
				// Convert QR code byte array to base64 string
				String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeBytes);
				
				// Set QR code base64 string as src attribute of <img> tag
				setQrCodeBase64(qrCodeBase64);
				
				this.setStt(true);
				this.setMsg(qrCodeBase64);
			} else {
				this.setStt(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setStt(false);
		}
		
		return SUCCESS;
	}
	
	public String bookSeats() {
		try {
			// Lấy dữ liệu từ yêu cầu AJAX
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.request.getInputStream()));
			StringBuilder jsonData = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonData.append(line);
			}
			
			// Chuyển đổi dữ liệu JSON thành mảng Schedules
			Gson gson = new Gson();
			Schedule[] schedules = gson.fromJson(jsonData.toString(), Schedule[].class);
			
			if (schedules.length > 0) {
				ScheduleDAO scheduleDAO = new ScheduleDAO();
				for (Schedule schedule : schedules) {
					java.util.Date javaUsingDate = new SimpleDateFormat("dd/MM/yyyy").parse(schedule.getUsingDateStr());
			        java.sql.Date sqlUsingdate = new java.sql.Date(javaUsingDate.getTime());
					java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
					schedule.setUsingDate(sqlUsingdate);
					
					if (sqlUsingdate.toLocalDate().equals(currentDate.toLocalDate())) {
						java.util.Date currentDateTime = new java.util.Date();
						Calendar calendar = Calendar.getInstance();
					    calendar.setTime(currentDateTime);
					    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
					    
						if (!(currentHour > 0 && currentHour < 11 && schedule.getPeriod().equals("Sáng"))
								&& !(currentHour < 17 && schedule.getPeriod().equals("Chiều"))) {
							this.setMsg("Vui lòng đặt ghế sau hiện tại một ngày.");
							this.setStt(false);
							return SUCCESS;
						}
					}
					else if (sqlUsingdate.before(currentDate)) {
						this.setMsg("Vui lòng đặt ghế sau hiện tại một ngày.");
						this.setStt(false);
						return SUCCESS;
					}
					
					if (scheduleDAO.isScheduleConflict(schedule)) {
						this.setMsg("Ghế đã được đăng ký.");
						this.setStt(false);
						return SUCCESS;
					}
					scheduleDAO.newSeatSchedule(schedule);
				}
				this.setMsg("Đặt ghế thành công.");
				this.setStt(true);
			} else {
				this.setMsg("Vui lòng chọn ghế trước khi xác nhận.");
				this.setStt(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String deleteSeatSchedule() {
		try {
			ScheduleDAO scheduleDAO = new ScheduleDAO();
			int scheduleId = this.getScheduleId();
			
			Schedule schedule =  scheduleDAO.getScheduleById(scheduleId);
			boolean deleted = scheduleDAO.deleteSeatSchedule(scheduleId);
			if (deleted) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				java.sql.Date sqlUsingDate = schedule.getUsingDate();
				java.util.Date javaUsingDate = new java.util.Date(sqlUsingDate.getTime());
				String usingDateStr = dateFormat.format(javaUsingDate);
				schedule.setUsingDateStr(usingDateStr);
				
//				String recipientEmail = schedule.getEmail();
//				String subject = "Xóa lịch đặt ghế";
//				String body = "Lịch sử dụng ghế " + schedule.getSeatName() 
//						+ " buổi " + schedule.getPeriod()
//						+ " ngày " + schedule.getUsingDateStr()
//						+ " đã được hủy.";
				
				this.setMsg("Xóa lịch đặt ghế thành công");
				this.setStt(true);
				
				//MailSender.sendMail(recipientEmail, subject, body);
				//MailSender.sendMail("20t1080072@husc.edu.vn", subject, body);
			} else {
				this.setMsg("Xóa lịch đặt ghế không thành công");
				this.setStt(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("Đã xảy ra lỗi khi xóa lịch đặt ghế.");
			this.setStt(false);
		}	
		return SUCCESS;
	}
	
	public String getBookedSeats() {
		try {
			String usingDateStr = request.getParameter("usingDateStr");
		    String period = request.getParameter("period");
		    int userId = Integer.parseInt(request.getParameter("userId"));
		    
		    if (!StringUtil.isNullOrEmpty(usingDateStr) && !StringUtil.isNullOrEmpty(period)) {
		    	java.util.Date javaUsingDate = new SimpleDateFormat("dd/MM/yyyy").parse(usingDateStr);
		        java.sql.Date sqlUsingdate = new java.sql.Date(javaUsingDate.getTime());
		        
		        ScheduleDAO scheduleDAO = new ScheduleDAO();
		        if (isAdmin() || isSubadmin()) {
		        	schedules = scheduleDAO.getAllSchedules(sqlUsingdate, period);
		        } else if (isUser()) {
		        	schedules = scheduleDAO.getAllUserSchedules(sqlUsingdate, period, userId);
		        }
				
				Gson gson = new Gson();
				String jsonSchedules = gson.toJson(schedules);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonSchedules);
				
				this.setData(jsonSchedules.toString());
		        
		        RoomDAO roomDAO = new RoomDAO();
		        if (roomDAO.isClosedSchedule(sqlUsingdate, period)) {
		        	this.setStt(false);
		        	this.setMsg("Ngày đã chọn là ngày đóng cửa. Vui lòng chọn ngày khác.");
		        	
		        	return SUCCESS;
		        }
				
				this.setStt(true);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			this.setStt(false);
		}
		return SUCCESS;
	}
	
	public String confirmStudent() {
		try {
			ScheduleDAO scheduleDAO = new ScheduleDAO();
			int scheduleId = this.getScheduleId();
			Schedule schedule = scheduleDAO.getScheduleById(scheduleId);
			int userId = schedule.getUserId();
			
	        java.sql.Date sqlUsingdate = new java.sql.Date(schedule.getUsingDate().getTime());
			java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
	        if (!sqlUsingdate.toLocalDate().equals(currentDate.toLocalDate())) {
	        	return ERROR;
	        }
			
	        // Thời gian hiện tại
	        LocalTime currentTime = LocalTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
	        int currentHour = currentTime.getHour();
	        int currentMinute = currentTime.getMinute();
	        String formattedTime = String.format("%02d:%02d", currentHour, currentMinute);

	        // Xác định Period dựa trên thời gian hiện tại
	        String period;
	        if (isBetween("07:30", "11:00", formattedTime)) {
	            period = "Sáng";
	        } else if (isBetween("14:00", "17:30", formattedTime)) {
	            period = "Chiều";
	        } else {
	            period = null;
	        }
			
	        if (period != null) {
				boolean confirmed = scheduleDAO.confirmStudent(userId, period);
				if (confirmed) {
					//addActionMessage("Xác nhận sinh viên thành công");
					return SUCCESS;
				} else {
					//addActionError("Không tìm thấy sinh viên trong danh sách đăng ký!");
					return ERROR;
				}
	        } else {
	        	//addActionError("Không thể xác nhận vào thời điểm hiện tại!");
	        	return ERROR;
	        }
		} catch (Exception e) {
			e.printStackTrace();
			//addActionError("Đã xảy ra lỗi khi xác nhận sinh viên!");
			return ERROR;
		}
	}
	
	public static boolean isBetween(String startTime, String endTime, String currentTime) {
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    try {
	        Date start = sdf.parse(startTime);
	        Date end = sdf.parse(endTime);
	        Date current = sdf.parse(currentTime);
	        return current.after(start) && current.before(end);
	    } catch (Exception e) {
	        return false;
	    }
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isStt() {
		return stt;
	}

	public void setStt(boolean stt) {
		this.stt = stt;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getQrCodeBase64() {
		return qrCodeBase64;
	}

	public void setQrCodeBase64(String qrCodeBase64) {
		this.qrCodeBase64 = qrCodeBase64;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
}