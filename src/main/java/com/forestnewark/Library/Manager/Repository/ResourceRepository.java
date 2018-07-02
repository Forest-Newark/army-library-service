package com.forestnewark.Library.Manager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forestnewark.Library.Manager.bean.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

}
