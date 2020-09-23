import com.google.gson.annotations.SerializedName

data class Languages (

	@SerializedName("name_of_language") val name_of_language : String?,
	@SerializedName("name_of_language_bn") val name_of_language_bn : String?,
	@SerializedName("name_of_institute") val name_of_institute : String?,
	@SerializedName("name_of_institute_bn") val name_of_institute_bn : String?,
	@SerializedName("expertise_level") val expertise_level : String?
)