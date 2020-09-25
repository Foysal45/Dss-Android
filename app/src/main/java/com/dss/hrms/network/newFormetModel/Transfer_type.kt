import com.google.gson.annotations.SerializedName



data class Transfer_type (
	@SerializedName("name") val name : String?,
	@SerializedName("name_bn") val name_bn : String?
){
	fun g_name(): String {
		if (name.equals(null))
			return ""
		return name!!
	}
	fun g_name_bn(): String {
		if (name_bn.equals(null))
			return ""
		return name_bn!!
	}
}
