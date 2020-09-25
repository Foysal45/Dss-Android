import com.google.gson.annotations.SerializedName


data class Childs (

	@SerializedName("name_of_children") val name_of_children : String?,
	@SerializedName("name_of_children_bn") val name_of_children_bn : String?,
	@SerializedName("date_of_birth") val date_of_birth : String?,
	@SerializedName("gender") val gender : Gender
)
{
	fun g_name_of_children(): String {
		if (name_of_children.equals(null))
			return ""
		return name_of_children!!
	}
	fun g_name_of_children_bn(): String {
		if (name_of_children_bn.equals(null))
			return ""
		return name_of_children_bn!!
	}
	fun g_date_of_birth(): String {
		if (date_of_birth.equals(null))
			return ""
		return date_of_birth!!
	}
}
