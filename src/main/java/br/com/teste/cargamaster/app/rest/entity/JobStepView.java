package br.com.teste.cargamaster.app.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobStepView {

    private BigInteger stepExecutionId;
    private BigInteger version;
    private String stepName;
    private BigInteger jobExecutionId;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private String status;
    private BigInteger commitCount;
    private BigInteger readCount;
    private BigInteger filterCount;
    private BigInteger writeCount;
    private BigInteger readSkipCount;
    private BigInteger writeSkipCount;
    private BigInteger processSkipCount;
    private BigInteger rollbackCount;
    private String exitCode;
    private String exitMessage;
    private Date lastUpdated;
}
