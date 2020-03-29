package com.zjz.graduationdemo.dao;

import com.zjz.graduationdemo.pojo.RequestSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RSDao extends JpaRepository<RequestSummary, Date> {
    public Optional<List<RequestSummary>> findByCreateTimeBetween(Date start, Date end);
}
