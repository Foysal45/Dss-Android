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
)