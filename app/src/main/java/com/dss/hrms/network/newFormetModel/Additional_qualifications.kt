import com.google.gson.annotations.SerializedName


data class Additional_qualifications (

	@SerializedName("qualification_name") val qualification_name : String?,
	@SerializedName("qualification_name_bn") val qualification_name_bn : String?,
	@SerializedName("qualification_details") val qualification_details : String?,
	@SerializedName("qualification_details_bn") val qualification_details_bn : String?
){
	fun g_qualification_name(): String {
		if (qualification_name.equals(null))
			return ""
		return qualification_name!!
	}
	fun g_qualification_name_bn(): String {
		if (qualification_name_bn.equals(null))
			return ""
		return qualification_name_bn!!
	}
	fun g_qualification_details(): String {
		if (qualification_details.equals(null))
			return ""
		return qualification_details!!
	}
	fun g_qualification_details_bn(): String {
		if (qualification_details_bn.equals(null))
			return ""
		return qualification_details_bn!!
	}
}