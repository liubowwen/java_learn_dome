package com.lbw.domain.repository;


import com.lbw.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**`
 * @author zbl
 * @description
 * @date 2019/10/17 14:27
 */
public interface AreaRepository extends JpaRepository<Area, String>, JpaSpecificationExecutor<Area> {
    Area findByCode(String code);


}
