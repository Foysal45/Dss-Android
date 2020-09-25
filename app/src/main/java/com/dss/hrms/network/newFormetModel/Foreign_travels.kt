import com.google.gson.annotations.SerializedName




data class Foreign_travels (

	@SerializedName("purpose") val purpose : String?,
	@SerializedName("purpose_bn") val purpose_bn : String?,
	@SerializedName("from_date") val from_date : String?,
	@SerializedName("to_date") val to_date : String?,
	@SerializedName("country") val country : Country
){
	fun g_purpose(): String {
		if (purpose.equals(null))
			return ""
		return purpose!!
	}fun g_purpose_bn(): String {
		if (purpose_bn.equals(null))
			return ""
		return purpose_bn!!
	}fun g_from_date(): String {
		if (from_date.equals(null))
			return ""
		return from_date!!
	}fun g_to_date(): String {
		if (to_date.equals(null))
			return ""
		return to_date!!
	}
}