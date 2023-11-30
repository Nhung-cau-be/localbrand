package com.localbrand.service.impl;

import com.localbrand.dal.entity.Account;
import com.localbrand.dal.entity.Category;
import com.localbrand.dal.entity.CustomerMessage;
import com.localbrand.dal.repository.IAccountRepository;
import com.localbrand.dal.repository.ICustomerMessageRepository;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.AccountDto;
import com.localbrand.dtos.response.CustomerMessageDto;
import com.localbrand.mappers.IAccountDtoMapper;
import com.localbrand.mappers.ICategoryDtoMapper;
import com.localbrand.mappers.ICustomerMessageDtoMapper;
import com.localbrand.service.IAccountService;
import com.localbrand.service.ICustomerMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerMessageServiceImpl implements ICustomerMessageService {
	private static final Logger logger = LoggerFactory.getLogger(ProductAttributeValueServiceImpl.class);
	@Autowired
	private ICustomerMessageRepository customerMessageRepository;

	@Override
	public List<CustomerMessageDto> getAll() {
		return ICustomerMessageDtoMapper.INSTANCE.toCustomerMessageDtos(customerMessageRepository.findAll());
	}

	@Override
	public BaseSearchDto<List<CustomerMessageDto>> findAll(BaseSearchDto<List<CustomerMessageDto>> searchDto) {
		if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
			searchDto.setResult(this.getAll());
			return searchDto;
		}

		if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
			searchDto.setSortBy("createdDate");
		}
		Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

		Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

		Page<CustomerMessage> page = customerMessageRepository.findAll(pageable);
		searchDto.setTotalRecords(page.getTotalElements());
		searchDto.setResult(ICustomerMessageDtoMapper.INSTANCE.toCustomerMessageDtos(page.getContent()));

		return searchDto;
	}

	@Override
	public CustomerMessageDto insert(CustomerMessageDto customerMessageDto){
		try {
			customerMessageDto.setId(UUID.randomUUID().toString());
			CustomerMessage customerMessage = ICustomerMessageDtoMapper.INSTANCE.toCustomerMessage(customerMessageDto);
			customerMessageRepository.save(customerMessage);
			
			return customerMessageDto;
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}