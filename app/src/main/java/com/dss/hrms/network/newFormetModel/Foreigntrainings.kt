import com.google.gson.annotations.SerializedName




data class Foreigntrainings (

	@SerializedName("course_title") val course_title : String?,
	@SerializedName("course_title_bn") val course_title_bn : String?,
	@SerializedName("name_of_institute") val name_of_institute : String?,
	@SerializedName("name_of_institute_bn") val name_of_institute_bn : String?,
	@SerializedName("from_date") val from_date : String?,
	@SerializedName("to_date") val to_date : String?,
	@SerializedName("certificate") val certificate : String?,
	@SerializedName("country") val country : Country
){
	fun g_course_title(): String {
		if (course_title.equals(null))
			return ""
		return course_title!!
	}fun g_course_title_bn(): String {
		if (course_title_bn.equals(null))
			return ""
		return course_title_bn!!
	}fun g_name_of_institute(): String {
		if (name_of_institute.equals(null))
			return ""
		return name_of_institute!!
	}fun g_name_of_institute_bn(): String {
		if (name_of_institute_bn.equals(null))
			return ""
		return name_of_institute_bn!!
	}fun g_from_date(): String {
		if (from_date.equals(null))
			return ""
		return from_date!!
	}fun g_to_date(): String {
		if (to_date.equals(null))
			return ""
		return to_date!!
	}fun g_certificate(): String {
		if (certificate.equals(null))
			return ""
		return certificate!!
	}
}