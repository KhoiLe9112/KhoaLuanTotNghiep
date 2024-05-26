package com.dhkh.action;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dhkh.dao.RoomDAO;
import com.dhkh.dao.ScheduleDAO;
import com.dhkh.model.Room;
import com.dhkh.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class RoomAction extends ActionSupport implements ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private List<Room> rooms;
	private Room room;
	private int roomId;
	
	private HttpServletRequest request;
	
	public String execute() {
		RoomDAO roomDAO = new RoomDAO();
		room = roomDAO.getRoom();
		rooms = roomDAO.getClosingSchedule();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		if (room == null) {
			room = new Room();
			room.setRoomName("Lab - Doanh nghiệp");
			room.setStatus("Đang hoạt động");
		}
		
		if (room.getStartDate() != null) {
			java.sql.Date sqlStartDate = room.getStartDate();
			java.util.Date javaStartDate = new java.util.Date(sqlStartDate.getTime());
			String startDateStr = dateFormat.format(javaStartDate);
			room.setStartDateStr(startDateStr);
		}
		
		if (room.getEndDate() != null) {
			java.sql.Date sqlEndDate = room.getEndDate();
			java.util.Date javaEndDate = new java.util.Date(sqlEndDate.getTime());
			String endDateStr = dateFormat.format(javaEndDate);
			room.setEndDateStr(endDateStr);
		}
		
		HttpSession session = request.getSession();
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            addActionError(errorMessage);
            session.removeAttribute("errorMessage");
        }
		
		return SUCCESS;
	}
	
	public String updateRoom() {
		try {
	        RoomDAO roomDAO = new RoomDAO();
	        Room roomInput = getRoom();
	        
	        if (roomInput.getStatus().equals("Đang hoạt động")) {
	        	roomInput.setStartDateStr("");
	        	roomInput.setEndDateStr("");
	        }
	        
	        // Chuyển đổi ngày
	        if (!StringUtil.isNullOrEmpty(roomInput.getStartDateStr())) {
	        	java.util.Date javaStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(roomInput.getStartDateStr());
		        java.sql.Date sqlStartDate = new java.sql.Date(javaStartDate.getTime());
		        roomInput.setStartDate(sqlStartDate);
	        }
	        
	        if (!StringUtil.isNullOrEmpty(roomInput.getEndDateStr())) {
	        	java.util.Date javaEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(roomInput.getEndDateStr());
		        java.sql.Date sqlEndDate = new java.sql.Date(javaEndDate.getTime());
		        roomInput.setEndDate(sqlEndDate);
	        }
	        
	        roomDAO.updateRoom(roomInput);
	        return SUCCESS;
	    } catch (Exception e) {
	        e.printStackTrace();
	        HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Có lỗi xảy ra vui lòng thử lại.");
	        return ERROR;
	    }
	}
	
	public String newRoomSchedule() {
		try {
	        RoomDAO roomDAO = new RoomDAO();
	        Room roomInput = getRoom();
	        
	        // Chuyển đổi ngày
	        if (!StringUtil.isNullOrEmpty(roomInput.getStartDateStr())) {
	        	java.util.Date javaStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(roomInput.getStartDateStr());
		        java.sql.Date sqlStartDate = new java.sql.Date(javaStartDate.getTime());
		        roomInput.setStartDate(sqlStartDate);
	        }
	        
	        if (!StringUtil.isNullOrEmpty(roomInput.getEndDateStr())) {
	        	java.util.Date javaEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(roomInput.getEndDateStr());
		        java.sql.Date sqlEndDate = new java.sql.Date(javaEndDate.getTime());
		        roomInput.setEndDate(sqlEndDate);
	        }
	        
	        if (roomDAO.isExistClosedSchedule(roomInput.getStartDate(), roomInput.getEndDate())) {
	        	HttpSession session = request.getSession();
	            session.setAttribute("errorMessage", "Lịch đóng của đã tồn tại.");
	            return ERROR;
	        }
	        
	        // Gửi mail thông báo xóa lịch đặt ghế
	        MailSender.sendCancelationEmail(roomInput.getStartDate(), roomInput.getEndDate(), roomInput.getStartPeriod(), roomInput.getEndPeriod(), roomInput.getReason());
	        
	        // Xóa tất cả lịch đã đặt trước đó vào ngày đóng cửa
	        ScheduleDAO scheduleDAO = new ScheduleDAO();
	        scheduleDAO.deleteScheduleByClosed(roomInput.getStartDate(), roomInput.getEndDate(), roomInput.getStartPeriod(), roomInput.getEndPeriod());
	        
	        // Thêm lịch đóng cửa
			roomDAO.newRoomSchedule(roomInput);
	        return SUCCESS;
	    } catch (Exception e) {
	        e.printStackTrace();
	        HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Có lỗi xảy ra vui lòng thử lại.");
            return ERROR;
	    }
	}
	
	public String deleteRoomSchedule() {
		try {
			RoomDAO roomDAO = new RoomDAO();
			roomId = this.getRoomId();
			
			roomDAO.deleteRoomSchedule(roomId);
			return SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Có lỗi xảy ra vui lòng thử lại.");
	        return ERROR;
		}
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}