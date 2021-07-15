package com.dss.hrms.view.training.model

import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.TrainingResponse

class TrainingDashBoard {


    data class TrainingDashBoardResponse(
        val status: String?,
        val code: Int?,
        val data: Dashboard?
    )

    data class Dashboard(
           val course_shedules:List<BudgetAndSchedule.CourseSchedule>,
           val batch_shedules:List<BudgetAndSchedule.BatchSchedule>,
         //  val course_sessions:List<CourseSessions>,
          // val course_modules:List<CourseModules>,
          // val course:List<BudgetAndSchedule.Course>,
           val faqs:List<TrainingResponse.Faq>
    )
  data  class CourseSessions(
      val id: Int?,
      val session_name: String?,
      val session_name_bn: String?,
      val course_method_id: Int?,
      val evaluation_method_id: Int?,
      val objective: String?,
      val outcomes: String?,
      val session_materials: String?,
      val session_details: String?,
      val marks: Int?,
      val status: Int?
  )
    data  class CourseModules(
      val id: Int?,
      val module_name: String?,
      val module_name_bn: String?,
      val user_id: Int?,
      val time_in_hour: Int?,
      val marks: Int?,
      val objective: String?,
      val status: Int?
  )

    data class BatchSchedule(
        val id: Int?,
        val course_schedule_id: Int?,
        val total_seat: String?,
        val batch_name: String?,
        val batch_name_bn: String?,
        val start_date: String?,
        val end_date: String?,
        val reg_start_date: String,
        val reg_end_date: String?,
        val co_coordinator_is_external: Int?,
        val coordinator_is_external: Int?,
        val external_coordinator: SpinnerDataModel?,
        val external_co_coordinator: SpinnerDataModel?,
        val course_coordinator: Int?,
        val course_co_coordinator: Int?,
        val co_coordinator: BudgetAndSchedule.CommonClass?,
        val coordinator: BudgetAndSchedule.CommonClass?,
        val staff1: BudgetAndSchedule.CommonClass?,
        val staff2: BudgetAndSchedule.CommonClass?,
        val staff3: BudgetAndSchedule.CommonClass?,
        val status: Int,
        val course_schedule: BudgetAndSchedule.CourseScheduleBatch
    )

}