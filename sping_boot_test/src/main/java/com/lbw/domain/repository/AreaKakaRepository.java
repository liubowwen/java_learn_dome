package com.lbw.domain.repository;

import com.lbw.domain.AreaKaka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author ：com.lbw
 * @date ：Created in 2021/12/28 9:55
 * @description：TODO
 */
public interface AreaKakaRepository extends JpaRepository<AreaKaka, Long>, JpaSpecificationExecutor<AreaKaka> {
    List<AreaKaka> findAllByCodeLikeAndIsGrid(String code, Integer isGrid);

    AreaKaka findFirstById(Long id);



}
