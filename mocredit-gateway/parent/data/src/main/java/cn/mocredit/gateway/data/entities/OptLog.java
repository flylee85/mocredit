package cn.mocredit.gateway.data.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_opt_log")
public class OptLog extends Id implements java.io.Serializable {
    private String opt_code;
    private String operator;
    private Date opt_time;
    private String opt_info;

    public String getOpt_code() {
        return opt_code;
    }

    public void setOpt_code(String opt_code) {
        this.opt_code = opt_code;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOpt_time() {
        return opt_time;
    }

    public void setOpt_time(Date opt_time) {
        this.opt_time = opt_time;
    }

    public String getOpt_info() {
        return opt_info;
    }

    public void setOpt_info(String opt_info) {
        this.opt_info = opt_info;
    }
}
