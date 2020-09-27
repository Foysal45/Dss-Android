import com.google.gson.annotations.SerializedName

data class Quotas (

	@SerializedName("description") val description : String?,
	@SerializedName("description_bn") val description_bn : String?,
	@SerializedName("quota_information") val quota_information : Quota_information,
	@SerializedName("quota_information_details") val quota_information_details : Quota_information_details
)
{
	fun g_description(): String {
		if (description.equals(null))
			return ""
		return description!!
	}

	fun g_description_bn(): String {
		if (description.equals(null))
			return ""
		return description!!
	}
}