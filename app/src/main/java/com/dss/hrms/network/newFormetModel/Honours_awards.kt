import com.google.gson.annotations.SerializedName



data class Honours_awards (

	@SerializedName("award_title") val award_title : String?,
	@SerializedName("award_title_bn") val award_title_bn : String?,
	@SerializedName("award_details") val award_details : String?,
	@SerializedName("award_details_bn") val award_details_bn : String?,
	@SerializedName("award_date") val award_date : String?
){
	fun g_award_title(): String {
		if (award_title.equals(null))
			return ""
		return award_title!!
	}
	fun g_award_title_bn(): String {
		if (award_title_bn.equals(null))
			return ""
		return award_title_bn!!
	}
	fun g_award_details(): String {
		if (award_details.equals(null))
			return ""
		return award_details!!
	}
	fun g_award_details_bn(): String {
		if (award_details_bn.equals(null))
			return ""
		return award_details_bn!!
	}
	fun g_award_date(): String {
		if (award_date.equals(null))
			return ""
		return award_date!!
	}
}