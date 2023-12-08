package com.localbrand.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.localbrand.service.INotificationService;
import com.localbrand.dal.entity.Notification;
import com.localbrand.dal.repository.INotificationRepository;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.NotificationDto;
import com.localbrand.mappers.INotificationDtoMapper;

@Service
public class NotificationServiceImpl implements INotificationService {
	@Autowired
	private INotificationRepository notificationRepository;
	
	
	@Override
    public List<NotificationDto> getAll() {
        return INotificationDtoMapper.INSTANCE.toNotificationDtos(notificationRepository.findAll());
    }
	
	@Override
	public List<NotificationDto> getAllNotIsRead(Boolean isRead) {
		List<NotificationDto> notificationDtos;
		if(isRead == null) {
			List<Notification> notification = notificationRepository.findAll();
			notificationDtos = INotificationDtoMapper.INSTANCE.toNotificationDtos(notification);
		}
		else if(isRead == false ) {
			List<Notification> notification = notificationRepository.getByNotIsRead(isRead);
			notificationDtos = INotificationDtoMapper.INSTANCE.toNotificationDtos(notification);
		}
		else
		{
			notificationDtos = null;
		}
		return notificationDtos;
	}
	@Override
	public BaseSearchDto<List<NotificationDto>> findAll(BaseSearchDto<List<NotificationDto>> searchDto) {
		if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("id");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<Notification> page = notificationRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(INotificationDtoMapper.INSTANCE.toNotificationDtos(page.getContent()));

        return searchDto;
	}
	@Override
	public boolean setAllNotificationIsRead() {
		try {
		notificationRepository.setAllNotificationIsRead();
		return true;
		} catch (Exception e) {
		System.out.println(e.getMessage());
		return false;
	}
	}
}