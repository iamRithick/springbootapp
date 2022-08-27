package com.accenture.springbootapp.client;

import com.accenture.springbootapp.bean.DepartmentBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DepartmentConsumer {
    @Value("${DepartmentServiceConsumer.serviceURL}")
    private String deptURL;
    @Value("${DepartmentServiceConsumer.apiURLForByName}")
    private String apiURLForById;

    private final Logger log = LoggerFactory.getLogger(DepartmentConsumer.class);
    private final RestTemplate restTemplate;

    @Autowired
    public DepartmentConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * METHOD DESCRIPTION:<br/>
     * This method hits the Department Service API and gets the Department bean
     * corresponding to the given Department Id. <br/>
     *
     * @param id
     * @return Department bean
     */
    public DepartmentBean getDeptById(int id){
        DepartmentBean departmentBean = null;
        try {
            log.info("Hitting Department Service API...");
            String url = deptURL + apiURLForById + id;
            departmentBean = restTemplate.getForObject(url, DepartmentBean.class);
            log.info("Returning to Student Service.");
        }
        catch (Exception e){
            throw e;
        }
        return departmentBean;
    }

}

