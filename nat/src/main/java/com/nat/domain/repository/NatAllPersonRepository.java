package com.nat.domain.repository;

import com.nat.domain.NatAllPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ：com.lbw
 * @date ：Created in 2021/8/19 14:01
 * @description：TODO
 */
public interface NatAllPersonRepository extends JpaRepository<NatAllPerson, Long> {

    List<NatAllPerson> findAllByRemark(String remark);


}
