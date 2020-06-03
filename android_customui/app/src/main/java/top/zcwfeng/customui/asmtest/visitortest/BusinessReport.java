package top.zcwfeng.customui.asmtest.visitortest;

import java.util.ArrayList;
import java.util.List;
// 员工业务报表类
class BusinessReport {
    private List<Staff> mStaffs = new ArrayList<>();


    public BusinessReport() {
        this.mStaffs.add(new Manager("经理-A"));
        this.mStaffs.add(new Engineer("Engineer-A"));
        this.mStaffs.add(new Engineer("Engineer-B"));
        this.mStaffs.add(new Engineer("Engineer-C"));
        this.mStaffs.add(new Manager("经理-B"));
        this.mStaffs.add(new Engineer("Engineer-D"));

    }


    /**
     * 为访问者展示报表
     * @param visitor 公司高层，如CEO、CTO
     */
    public void showReport(Visitor visitor) {
        for(Staff staff:mStaffs) {
            staff.accept(visitor);
        }
    }


}
