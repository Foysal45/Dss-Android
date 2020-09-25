import com.google.gson.annotations.SerializedName




data class Office (

	@SerializedName("office_name") val office_name : String,
	@SerializedName("office_name_bn") val office_name_bn : String
)
{	fun g_office_name(): String {
	if (office_name.equals(null))
		return ""
	return office_name!!
}
	fun g_office_name_bn(): String {
		if (office_name_bn.equals(null))
			return ""
		return office_name_bn!!
	}}