package com.dss.hrms.model

data class CourseModel(
    val course_category: CourseCategory?,
    val course_category_id: Int?,
    val course_description: String?,
    val course_image_path: String?,
    val course_method_id: List<String?>?,
    val course_name: String?,
    val course_name_bn: String?,
    val course_objective: String?,
    val courses_evaluation_methods: List<CoursesEvaluationMethod?>?,
    val created_at: String?,
    val deleted_at: Any?,
    val id: Int?,
    val modules: List<Module?>?,
    val status: Int?,
    val time_in_hour: Int?,
    val total_number: Int?,
    val updated_at: String?
) {
    data class CourseCategory(
        val course_category_name: String?,
        val course_category_name_bn: String?,
        val created_at: String?,
        val id: Int?,
        val status: Int?,
        val updated_at: String?
    )

    data class CoursesEvaluationMethod(
        val course_evaluation_method: CourseEvaluationMethod?,
        val course_id: Int?,
        val created_at: Any?,
        val deleted_at: Any?,
        val evaluation_method_id: Int?,
        val id: Int?,
        val marks: Int?,
        val status: Int?,
        val updated_at: Any?
    ) {
        data class CourseEvaluationMethod(
            val created_at: String?,
            val evaluation_method_name: String?,
            val id: Int?,
            val marks: Int?,
            val updated_at: String?
        )
    }

    data class Module(
        val course_id: Int?,
        val course_module_id: Int?,
        val created_at: Any?,
        val deleted_at: Any?,
        val id: Int?,
        val module: Module?,
        val status: Int?,
        val updated_at: Any?
    ) {
        data class Module(
            val created_at: String?,
            val deleted_at: Any?,
            val id: Int?,
            val marks: Int?,
            val module_name: String?,
            val module_name_bn: String?,
            val objective: String?,
            val status: Int?,
            val time_in_hour: Int?,
            val updated_at: String?,
            val user_id: Int?
        )
    }
}