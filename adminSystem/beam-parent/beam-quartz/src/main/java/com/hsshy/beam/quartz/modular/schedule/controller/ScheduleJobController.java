package com.hsshy.beam.quartz.modular.schedule.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsshy.beam.common.utils.R;
import com.hsshy.beam.common.utils.ToolUtil;
import com.hsshy.beam.quartz.common.ScheduleJob;
import com.hsshy.beam.quartz.modular.schedule.entity.ScheduleJobEntity;
import com.hsshy.beam.quartz.modular.schedule.service.IScheduleJobService;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.2.0 2016-11-28
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {

	@Autowired
	private IScheduleJobService scheduleJobService;
	
	/**
	 * 定时任务列表
	 */
	@RequestMapping("/page/list")
	public R list(ScheduleJobEntity scheduleJobEntity){

		QueryWrapper qw = new QueryWrapper<Map>();
		if(ToolUtil.isNotEmpty(scheduleJobEntity.getStatus())){
			qw.eq("status",scheduleJobEntity.getStatus());
		}
		if(ToolUtil.isNotEmpty(scheduleJobEntity.getBeanName())){
			qw.like("bean_name",scheduleJobEntity.getBeanName());
		}
		IPage<ScheduleJob> page = scheduleJobService.page(new Page(scheduleJobEntity.getCurrentPage(),scheduleJobEntity.getPageSize()),qw);
		return R.ok(page);
	}

	/**
	 * 定时任务信息
	 */
	@RequestMapping("/info/{jobId}")
	public R info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.getById(jobId);
		
		return R.ok(schedule);
	}
	
	/**
	 * 保存定时任务
	 */
	@RequestMapping("/save")
	public R save(@RequestBody ScheduleJobEntity scheduleJob){
		if(CronExpression.isValidExpression(scheduleJob.getCronExpression())){
			scheduleJobService.saveScheduleJob(scheduleJob);
			return R.ok();

		}
		else {
			return R.fail("cron表达式有误");
		}

	}
	
	/**
	 * 修改定时任务
	 */
	@RequestMapping("/update")
	public R update(@RequestBody ScheduleJobEntity scheduleJob){
		if(CronExpression.isValidExpression(scheduleJob.getCronExpression())){
			scheduleJobService.update(scheduleJob);

			return R.ok();
		}
		else {
			return R.fail("cron表达式有误");
		}

	}
	
	/**
	 * 删除定时任务
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] jobIds){
		scheduleJobService.deleteBatch(jobIds);
		
		return R.ok();
	}
	
	/**
	 * 立即执行任务
	 */
	@RequestMapping("/run")
	public R run(@RequestBody Long[] jobIds){
		scheduleJobService.run(jobIds);
		
		return R.ok();
	}
	
	/**
	 * 暂停定时任务
	 */
	@RequestMapping("/pause")
	public R pause(@RequestBody Long[] jobIds){
		scheduleJobService.pause(jobIds);
		
		return R.ok();
	}
	
	/**
	 * 恢复定时任务
	 */
	@RequestMapping("/resume")
	public R resume(@RequestBody Long[] jobIds){
		scheduleJobService.resume(jobIds);
		return R.ok();
	}

}
