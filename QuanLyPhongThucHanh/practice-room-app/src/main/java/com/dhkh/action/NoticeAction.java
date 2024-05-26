package com.dhkh.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.dhkh.dao.NoticeDAO;
import com.dhkh.dao.UserDAO;
import com.dhkh.model.Notice;
import com.dhkh.model.User;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class NoticeAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	private List<Notice> notices = new ArrayList<>();
	private static final long serialVersionUID = 1L;
	private Notice notice;
	private int noticeId;
	private boolean stt;
	private String data;
	
	private HttpServletRequest request;
	private HttpServletResponse response;

	public String execute() {
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		NoticeDAO noticeDAO = new NoticeDAO();
		List<Notice> notices = noticeDAO.getNoticeByUser(userId);

		Gson gson = new Gson();
		String jsonNotices = gson.toJson(notices);
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonNotices);
			this.setStt(true);
			this.setData(jsonNotices.toString());
		} catch (IOException e) {
			e.printStackTrace();
			this.setStt(false);
		}

		return SUCCESS;
	}
	
	public String showAllNotice() {
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		NoticeDAO noticeDAO = new NoticeDAO();
		setNotices(noticeDAO.getNoticeByUser(userId));
		return SUCCESS;
	}
	
	public static void addPasswordNotification(String password, User loggedInUser) {
		String title = "Thông báo mật khẩu";
		String content = "Mật khẩu mặc định của bạn là " + password;
		String type = "Mật khẩu";
		
		NoticeDAO noticeDAO = new NoticeDAO();
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setUserId(loggedInUser.getUserId());
		notice.setType(type);
		
		noticeDAO.addNotice(notice);
	}

	public static void addNotification(Map<String, Map<String, Set<String>>> bookedSeats, User loggedInUser, String type) {
		if (loggedInUser != null) {
			UserDAO userDAO = new UserDAO();
			String username = userDAO.getUserById(loggedInUser.getUserId()).getUsername();
			String title = "";
			if (type == "Đặt ghế") {
				title = username + " đặt ghế thành công";
			} else if (type == "Hủy ghế") {
				title = username + " hủy ghế thành công";
			}
			
			StringBuilder contentBuilder = new StringBuilder();
			for (Map.Entry<String, Map<String, Set<String>>> entry : bookedSeats.entrySet()) {
				String seatName = entry.getKey();
				Map<String, Set<String>> dateAndPeriodsMap = entry.getValue();

				StringBuilder seatStringBuilder = new StringBuilder();
				if (type == "Đặt ghế") {
					seatStringBuilder.append("Ghế: ").append(seatName);
				} else if (type == "Hủy ghế") {
					seatStringBuilder.append("Hủy ghế: ").append(seatName);
				}
				
				for (Map.Entry<String, Set<String>> dateAndPeriodEntry : dateAndPeriodsMap.entrySet()) {
					String date = dateAndPeriodEntry.getKey();
					Set<String> period = dateAndPeriodEntry.getValue();
					seatStringBuilder.append(", Ngày sử dụng: ").append(date).append(" Buổi: ").append(period);
				}

				if (seatStringBuilder.length() > 0 && seatStringBuilder.charAt(seatStringBuilder.length() - 1) == ',') {
					seatStringBuilder.setLength(seatStringBuilder.length() - 2);
				}
				seatStringBuilder.append("\n");
				contentBuilder.append(seatStringBuilder);
			}
			String content = contentBuilder.toString();

			NoticeDAO noticeDAO = new NoticeDAO();
			Notice notice = new Notice();
			notice.setTitle(title);
			notice.setContent(content);
			notice.setUserId(loggedInUser.getUserId());
			notice.setType(type);
			
			noticeDAO.addNotice(notice);
		}
	}
	
	public String detailNotice() {
		try {
			NoticeDAO noticeDAO = new NoticeDAO();
			Notice notice = noticeDAO.getNoticeById(noticeId);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			java.sql.Timestamp createDate = notice.getCreateDate();
			String createDateStr = dateFormat.format(createDate);
			notice.setCreateDateStr(createDateStr);
			
			this.setNotice(notice);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Có lỗi xảy ra, vui lòng thử lại");
			return ERROR;
		}
	}

	public List<Notice> getNotices() {
		return notices;
	}

	public void setNotices(List<Notice> notices) {
		this.notices = notices;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
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
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
}
