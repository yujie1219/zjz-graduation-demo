package com.zjz.graduationdemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSummary {
    @Id
    @Column(columnDefinition = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private Long requestNum;

    private Long successNum;

    private Long failNum;

    private Long invalidNum;
}
