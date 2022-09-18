package com.lbw.domain.repository;

import com.lbw.domain.AllNatPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author zbl
 * @description
 * @date 2019/10/17 14:14
 */

public interface AllNatPersonRepository extends JpaRepository<AllNatPerson, Long>, JpaSpecificationExecutor<AllNatPerson> {
}
