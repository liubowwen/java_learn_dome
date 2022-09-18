package com.nat.service;

import com.nat.domain.repository.NatAllPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：lbw
 * @date ：Created in 2022/3/16 20:19
 * @description：TODO
 */
@Service
public class NatAllPersonService {
    private NatAllPersonRepository natAllPersonRepository;

    @Autowired
    public void setNatAllPersonRepository(NatAllPersonRepository natAllPersonRepository) {
        this.natAllPersonRepository = natAllPersonRepository;
    }

//    /**
//     * 获取指定轮核酸 省平台
//     * @return
//     */
//    private List<NatAllPerson> findNatAllPersonByRemark(String remark){
//        List<NatAllPerson> list = natAllPersonRepository.findAllByRemark(remark);
//
//    }
}
