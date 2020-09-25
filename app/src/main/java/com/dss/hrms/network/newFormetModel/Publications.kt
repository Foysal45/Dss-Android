import com.google.gson.annotations.SerializedName




data class Publications (

	@SerializedName("publication_type") val publication_type : Publication_type,
	@SerializedName("publication_name") val publication_name : String?,
	@SerializedName("publication_name_bn") val publication_name_bn : String?,
	@SerializedName("publication_details") val publication_details : String?,
	@SerializedName("publication_details_bn") val publication_details_bn : String?
)
{
	fun g_publication_name(): String {
		if (publication_name.equals(null))
			return ""
		return publication_name!!
	}
	fun g_publication_name_bn(): String {
		if (publication_name_bn.equals(null))
			return ""
		return publication_name_bn!!
	}
	fun g_publication_details(): String {
		if (publication_details.equals(null))
			return ""
		return publication_details!!
	}
	fun g_publication_details_bn(): String {
		if (publication_details_bn.equals(null))
			return ""
		return publication_details_bn!!
	}

}