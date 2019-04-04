package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableStage;
import com.workflowstreamer.dao.mapper.StageMapper;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Set;

public interface StagesDAO {
    @Mapper(StageMapper.class)
    @SqlQuery("SELECT * FROM stages")
    Set<ImmutableStage> getStages();
}