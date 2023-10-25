package com.localbrand.dal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.localbrand.dal.entity.CustomerType;

@Repository
public interface ICustomerTypeRepository extends JpaRepository<CustomerType, String> {
	int countByName(String name);
	
	@Query("SELECT COUNT(c) FROM CustomerType c WHERE c.standardPoint = :standardPoint")
	int countByStandardPoint(Integer standardPoint);
	
	@Query("SELECT count(c.id) FROM CustomerType c WHERE c.name = :name AND c.id != :customerTypeId")
	int countByNameIgnore(@Param("name") String name, @Param("customerTypeId") String customerTypeId);
	
	@Query("SELECT count(c.id) FROM CustomerType c WHERE c.standardPoint = :standardPoint AND c.id != :customerTypeId")
	int countByStandardPointIgnore(@Param("standardPoint") Integer standardPoint, @Param("customerTypeId") String customerTypeId);
	
	@Query("SELECT c FROM CustomerType c WHERE c.standardPoint = :standardPoint")
	CustomerType findByStandardPoint(@Param("standardPoint") Integer standardPoint);
	
	@Query("SELECT c FROM CustomerType c WHERE c.standardPoint < :standardPoint")
	List<CustomerType> findByStandardPointDelete(Integer standardPoint);
}
