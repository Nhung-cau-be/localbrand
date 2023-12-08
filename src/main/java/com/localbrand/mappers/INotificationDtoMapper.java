package com.localbrand.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.localbrand.dal.entity.Notification;
import com.localbrand.dtos.response.NotificationDto;


@Mapper
public interface INotificationDtoMapper {
	INotificationDtoMapper INSTANCE = Mappers.getMapper(INotificationDtoMapper.class);
	NotificationDto toNotificationDto (Notification notification);
	
	Notification toNotification (NotificationDto notificationDto);
	
	List<NotificationDto> toNotificationDtos(List<Notification> notifications);
}
