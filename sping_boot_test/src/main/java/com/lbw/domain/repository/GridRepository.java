package com.lbw.domain.repository;

import com.lbw.domain.Grid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ：com.lbw
 * @date ：Created in 2021/12/28 14:50
 * @description：TODO
 */
public interface GridRepository extends JpaRepository<Grid, Long>, JpaSpecificationExecutor<Grid> {
}
