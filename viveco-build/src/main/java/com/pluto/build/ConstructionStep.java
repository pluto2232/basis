package com.pluto.build;

import java.util.List;

/**
 * 施工工序类
 */
public class ConstructionStep {
    private Long id;
    private String name;  // 工序名称
    private List<Long> prevSteps;  // 前置工序ID
    private boolean canSkip;  // 是否允许跳过
    private int estimatedDays;  // 预计工期

    public ConstructionStep(Long id, String name, List<Long> prevSteps, boolean canSkip, int estimatedDays) {
        this.id = id;
        this.name = name;
        this.prevSteps = prevSteps;
        this.canSkip = canSkip;
        this.estimatedDays = estimatedDays;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Long> getPrevSteps() { return prevSteps; }
    public boolean isCanSkip() { return canSkip; }
    public int getEstimatedDays() { return estimatedDays; }

}
