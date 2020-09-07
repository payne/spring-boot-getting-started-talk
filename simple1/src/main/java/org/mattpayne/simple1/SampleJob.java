package org.mattpayne.simple1;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class SampleJob implements Job {

   // @Autowired
   // private SampleJobService jobService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //jobService.executeSampleJob();
        System.out.println("Sample Job " + new java.util.Date());
    }
}

