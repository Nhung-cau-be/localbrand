package com.localbrand.service;

import java.util.List;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.NotificationDto;


public interface INotificationService {
	List<NotificationDto> getAllNotIsRead(Boolean isRead);
	
	boolean setAllNotificationIsRead();	
	
	List<NotificationDto> getAll();
	
	BaseSearchDto<List<NotificationDto>> findAll(BaseSearchDto<List<NotificationDto>> searchDto);
	

}
