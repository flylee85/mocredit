package cn.mocredit.gateway.wangpos.bo;

public class JsonData {
    private String termId;
    private String storId;
    private String timestamp;
    private String jData;

    public String getjData() {
        return jData;
    }

    public void setjData(String jData) {
        this.jData = jData;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStorId() {
        return storId;
    }

    public void setStorId(String storId) {
        this.storId = storId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }
}
