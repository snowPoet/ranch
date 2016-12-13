package org.lpw.ranch.audit;

import org.springframework.stereotype.Service;

/**
 * @author lpw
 */
@Service(TestAuditModel.NAME + ".service")
public class TestAuditServiceImpl implements TestAuditService {
    private String[] passIds;
    private String[] refuseIds;
    private String auditRemark;

    @Override
    public String[] getPassIds() {
        return passIds;
    }

    @Override
    public String[] getRefuseIds() {
        return refuseIds;
    }

    @Override
    public String getAuditRemark() {
        return auditRemark;
    }

    @Override
    public void pass(String[] ids, String auditRemark) {
        passIds = ids;
        this.auditRemark = auditRemark;
    }

    @Override
    public void refuse(String[] ids, String auditRemark) {
        refuseIds = ids;
        this.auditRemark = auditRemark;
    }
}
