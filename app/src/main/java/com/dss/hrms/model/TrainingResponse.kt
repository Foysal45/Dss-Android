package com.dss.hrms.model

class TrainingResponse {
    data class ContentCategory(
        var status: String?,
        var code: Int?,
        var data: List<Category>
    )

    data class Category(
        val id: Int,
        val category_name: String?,
        val category_name_bn: String?
    )

    data class ContentsContent(
        var status: String?,
        var code: Int?,
        var data: List<Content>
    )

    data class Content(
        val id: Int,
        val content_title: String?,
        val content_title_bn: String?,
        val category_id: Int?,
        val content_body: String?,
        val attachment_path: String?,
        val picture_path: String?,
        val slug: String?,
        val is_published: Int?,
        val category: Category?
    )

    data class ContentsFaq(var status: String?, var code: Int?, var data: List<Faq>?)
    data class FaqData(val data: List<Faq>)
    data class Faq(val id: Int?, val faq_question: String?, val faq_answer: String)
    data class ResourcePersonResponse(
        var status: String?,
        var message: String?,
        var code: Int?,
        var data: List<ResourcePerson>
    )

    data class ResourcePerson(
        val id: Int?,
        val person_name: String?,
        val short_name: String?,
        val designation_id: Int?,
        val first_email: String?,
        val first_mobile: String?,
        val honorarium_head_id: Int?,
        val field_of_expertise_id: Int?,
        val tin_no: String?,
        val nid_no: String?,
        val optional_email: String?,
        val optional_mobile: String?,
        val office_address: String?,
        val mailing_address: String?,
        val resource_person_image_path: String?,
        val cv_upload_path: String?,
        val youtube_url: String?,
        val is_youtube_public: Int?,
        val facebook_url: String?,
        val is_facebook_public: Int?,
        val linkedin_url: String?,
        val is_linkedin_public: Int?,
        val instagram_url: String?,
        val is_instagram_public: Int?,
        val status: Int?,
        val designation: Desingnation?
    )

    data class Desingnation(
        val id: Int?,
        val name: String?,
        val name_bn: String?,
        val priority_order: Int?,
        val status: Int?
    )




    data class ResourcePersonModulesResponse(
        var status: String?,
        var message: String?,
        var code: Int?,
        var data: TrainingModulesResponse
    )

    data class TrainingModulesResponse(

        var data: List<TrainingModules>
    )

    data class ResourcePersonCourseResponse(
        var status: String?,
        var message: String?,
        var code: Int?,
        var data: TrainingCourseResponse
    )
    data class TrainingCourseResponse(

        var data: List<CourseModel>
    )


    data class TrainingModules(

        val id: Int,
        val module_name: String,
        val module_name_bn: String,
        val user_id: Int,
        val time_in_hour: Int,
        val marks: Int,
        val objective: String,
        val status: Int,
        val deleted_at: String,
        val created_at: String,
        val updated_at: String
    )


}