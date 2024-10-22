package br.com.teste.cargamaster.app.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobExecutionView {

    private BigInteger jobExecutionId;
    private BigInteger version;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private String status;
    private String exitCode;
    private String exitMessage;
    private Date lastUpdated;
}
