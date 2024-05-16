package com.neathorium.thorium.core.wait.interfaces;

import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskCommonData;
import com.neathorium.thorium.core.wait.records.tasks.common.WaitTaskStateData;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public interface IWaitTask<DependencyType, ReturnType, ConditionType> {
    ScheduledExecutorService SCHEDULER();
    WaitTaskCommonData<DependencyType, ReturnType, ConditionType> COMMON_DATA();
    AtomicReference<WaitTaskStateData<DependencyType, ConditionType>> STATE_DATA();
}
