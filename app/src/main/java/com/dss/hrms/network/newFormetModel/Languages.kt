import com.google.gson.annotations.SerializedName

data class Languages (

	@SerializedName("name_of_language") val name_of_language : String?,
	@SerializedName("name_of_language_bn") val name_of_language_bn : String?,
	@SerializedName("name_of_institute") val name_of_institute : String?,
	@SerializedName("name_of_institute_bn") val name_of_institute_bn : String?,
	@SerializedName("expertise_level") val expertise_level : String?
){
	fun g_name_of_language(): String {
		if (name_of_language.equals(null))
			return ""
		return name_of_language!!
	}fun g_name_of_language_bn(): String {
		if (name_of_language_bn.equals(null))
			return ""
		return name_of_language_bn!!
	}fun g_name_of_institute(): String {
		if (name_of_institute.equals(null))
			return ""
		return name_of_institute!!
	}fun g_name_of_institute_bn(): String {
		if (name_of_institute_bn.equals(null))
			return ""
		return name_of_institute_bn!!
	}fun g_expertise_level(): String {
		if (expertise_level.equals(null))
			return ""
		return expertise_level!!
	}
}