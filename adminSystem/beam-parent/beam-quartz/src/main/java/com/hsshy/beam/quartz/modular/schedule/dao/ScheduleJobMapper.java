package com.hsshy.beam.quartz.modular.schedule.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsshy.beam.quartz.modular.schedule.entity.ScheduleJobEntity;

import java.util.Map;

/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.2.0 2016-11-28
 */
public interface ScheduleJobMapper extends BaseMapper<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
