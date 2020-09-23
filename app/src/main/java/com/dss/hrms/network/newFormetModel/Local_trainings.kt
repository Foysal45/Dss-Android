import com.google.gson.annotations.SerializedName



data class Local_trainings (

	@SerializedName("course_title") val course_title : String?,
	@SerializedName("course_title_bn") val course_title_bn : String?,
	@SerializedName("name_of_institute") val name_of_institute : String?,
	@SerializedName("name_of_institute_bn") val name_of_institute_bn : String?,
	@SerializedName("location") val location : String?,
	@SerializedName("location_bn") val location_bn : String?,
	@SerializedName("from_date") val from_date : String?,
	@SerializedName("to_date") val to_date : String?,
	@SerializedName("certificate") val certificate : String?
)